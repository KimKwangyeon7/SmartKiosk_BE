package com.iMbank.iMbank.domain.member.service;

import com.iMbank.iMbank.domain.counsel.dto.response.ButtonInfoResponse;
import com.iMbank.iMbank.domain.counsel.repository.CounselRepository;
import com.iMbank.iMbank.domain.department.dto.request.CreateButtonRequest;
import com.iMbank.iMbank.domain.department.dto.request.ModifyButtonRequest;
import com.iMbank.iMbank.domain.department.entity.Department;
import com.iMbank.iMbank.domain.department.entity.Work;
import com.iMbank.iMbank.domain.department.repository.DepartmentRepository;
import com.iMbank.iMbank.domain.department.repository.WorkRepository;
import com.iMbank.iMbank.domain.member.dto.*;
import com.iMbank.iMbank.domain.member.entity.Member;
import com.iMbank.iMbank.domain.member.exception.MemberErrorCode;
import com.iMbank.iMbank.domain.member.exception.MemberException;
import com.iMbank.iMbank.domain.member.repository.MemberRepository;
import com.iMbank.iMbank.domain.statistics.service.StatisticsService;
import com.iMbank.iMbank.global.component.jwt.repository.RefreshTokenRepository;
import com.iMbank.iMbank.global.component.jwt.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final WorkRepository workRepository;
    private final DepartmentRepository departmentRepository;
    private final StatisticsService statisticsService;
    private final CounselRepository counselRepository;

    @Override
    public void signupMember(MemberSignupRequest signupRequest) {
        if (memberRepository.existsByEmail(signupRequest.getEmail())) {
            throw new MemberException(MemberErrorCode.EXIST_MEMBER_EMAIL);
        }
        if (signupRequest.getPassword() == null) {
            throw new IllegalArgumentException("비밀번호가 null입니다.");
        }
        signupRequest.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        memberRepository.save(signupRequest.toEntity());
    }

    @Override
    public MemberLoginResponse loginMember(MemberLoginRequest loginRequest) {
        Member member = findMemberByEmail(loginRequest.email());

        String realPassword = member.getPassword();

        if (!passwordEncoder.matches(loginRequest.password(), realPassword)) {
            throw new MemberException(MemberErrorCode.NOT_MATCH_PASSWORD);
        }

        return jwtTokenService.issueAndSaveJwtToken(member);
    }

    @Override
    public void logoutMember(String email) {
        Optional<String> token = refreshTokenRepository.find(email);

        if (token.isEmpty()) {
            throw new MemberException(MemberErrorCode.ALREADY_MEMBER_LOGOUT);
        }
        // 리프레쉬 토큰 삭제
        refreshTokenRepository.delete(email);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberInfo getMember(int memberId) {
        Member member = findMemberById((long)memberId).orElse(null);

        return new MemberInfo(
                member.getUser_id(),
                member.getEmail(),
                member.getName(),
                member.getDept_nm(),
                member.getRole()
        );
    }

    @Override
    public void deleteMember(int memberId) {
        // JPA에 저장된 데이터 삭제
        memberRepository.deleteById((long)memberId);
    }

    @Override
    public void addButton(CreateButtonRequest createButtonRequest) {
        List<String> list = workRepository.findWorkDvcdByDeptNm(createButtonRequest.dept_nm());
        int max = Integer.MIN_VALUE;
        for (String str: list){
            int tmp = Integer.parseInt(str);
            max = Math.max(tmp, max);
        }
        max++;
        Work work;
        Department dept = departmentRepository.findByDeptNM(createButtonRequest.dept_nm()).orElse(null);

        if (max > 9){
            work = new Work(dept, createButtonRequest.dept_nm(), ""+max, createButtonRequest.work_dvcd_nm());
        } else {
            work = new Work(dept, createButtonRequest.dept_nm(), "0"+max, createButtonRequest.work_dvcd_nm());
        }
        workRepository.save(work);
    }

    @Override
    public void modifyButton(ModifyButtonRequest modifyButtonRequest) {
        Work work = workRepository.findWorkByWorkDvcdNm(modifyButtonRequest.dept_nm(), modifyButtonRequest.old_work_dvcd_nm());
        work.setWork_dvcd_nm(modifyButtonRequest.new_work_dvcd_nm());
        workRepository.save(work);
    }

    @Override
    public void deleteButton(CreateButtonRequest createButtonRequest) {
        workRepository.deleteByDeptNmAndWorkNm(createButtonRequest.dept_nm(), createButtonRequest.work_dvcd_nm());
    }

    @Override
    public List<ButtonInfoResponse> getButtonInfo(String deptNm) {
        List<Work> works = getButtonList(deptNm);
        List<ButtonInfoResponse> res = new ArrayList<>();
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        for (int i = 0; i < works.size(); i++) {
            int kioskId = works.get(i).getDepartment().getKiosks().get(0).getKiosk_id();
            int wait_people = counselRepository.getTotalWaitCnt(kioskId, works.get(i).getWork_dvcd(), "00", todayDate);
            // 대기 시간 구하기: 업무별 평균 대기 시간 * 대기 인원
            Map<String, Double> map = statisticsService.getAvgCsnlTime(departmentRepository.findDeptNameByDeptId(works.get(i).getDepartment().getDept_id())).myAvg();
            int wait_time = (int) (map.get(works.get(i).getWork_dvcd_nm()) * wait_people);
            Work w = works.get(i);
            res.add(new ButtonInfoResponse(w.getDepartment().getDept_id(), w.getDept_nm(), w.getWork_dvcd(), w.getWork_dvcd_nm(), wait_time, wait_people, w.getLeft_high(), w.getRight_low(), w.getColor()));
        }

        return res;
    }

    @Override
    public List<Work> getButtonList(String deptNm){
        return workRepository.findAllByDeptNm(deptNm);
    }

    @Override
    public void modifyButtonLocation(String deptNm, List<Map<String, String>> buttonLocInfoList) {
        for (Map<String, String> buttonLocInfo: buttonLocInfoList){
            Work work = workRepository.findWorkByWorkDvcdNm(deptNm, buttonLocInfo.get("work_dvcd_nm"));
            work.setRight_low(buttonLocInfo.get("right_low"));
            work.setLeft_high(buttonLocInfo.get("left_high"));
            workRepository.save(work);
        }
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
    }

    private Optional<Member> findMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
