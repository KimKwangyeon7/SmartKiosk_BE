package com.iMbank.iMbank.domain.counsel.service;

import com.iMbank.iMbank.domain.counsel.dto.IssuedTicketInfo;
import com.iMbank.iMbank.domain.counsel.dto.request.StartCounselRequest;
import com.iMbank.iMbank.domain.counsel.dto.response.EndCounselResponse;
import com.iMbank.iMbank.domain.counsel.dto.response.IssuedTicketResponse;
import com.iMbank.iMbank.domain.counsel.dto.response.StartCounselResponse;
import com.iMbank.iMbank.domain.counsel.entity.Counsel;
import com.iMbank.iMbank.domain.counsel.entity.Ticket;
import com.iMbank.iMbank.domain.counsel.repository.CounselRepository;
import com.iMbank.iMbank.domain.counsel.repository.TicketRepository;
import com.iMbank.iMbank.domain.department.entity.Department;
import com.iMbank.iMbank.domain.department.entity.Work;
import com.iMbank.iMbank.domain.department.repository.DepartmentRepository;
import com.iMbank.iMbank.domain.department.repository.WorkRepository;
import com.iMbank.iMbank.domain.kiosk.entity.Kiosk;
import com.iMbank.iMbank.domain.kiosk.repository.KioskRepository;
import com.iMbank.iMbank.domain.member.entity.Member;
import com.iMbank.iMbank.domain.member.repository.MemberRepository;
import com.iMbank.iMbank.domain.statistics.service.StatisticsService;
import com.iMbank.iMbank.domain.wicket.entity.Wicket;
import com.iMbank.iMbank.domain.wicket.repository.WicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CounselServiceImpl implements CounselService{

    private final CounselRepository counselRepository;
    private final TicketRepository ticketRepository;
    private final KioskRepository kioskRepository;
    private final DepartmentRepository departmentRepository;
    private final WicketRepository wicketRepository;
    private final MemberRepository memberRepository;
    private final StatisticsService statisticsService;
    private final WorkRepository workRepository;

    private static final String DATE_FORMAT = "yyyyMMdd";



    @Transactional
    public IssuedTicketResponse issueTicket(IssuedTicketInfo ticketInfo) {
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        Department dpt = departmentRepository.findByDeptNM(ticketInfo.dept_nm()).orElse(null);

        Kiosk kiosk = kioskRepository.findByDepartment(dpt).orElse(null);
        List<Work> works = workRepository.findAllByDeptNm(ticketInfo.dept_nm());

        // 특정 업무 티켓을 찾거나 새로 생성
        String user_dvcd = workRepository.findWorkDvcdByWorkDvcdNm(ticketInfo.user_dvcd_nm(), dpt);
        Ticket updatedTicket = ticketRepository.findByKioskIdAndDateAndUser(kiosk, user_dvcd, todayDate);

        int cnt = 0;
        if (updatedTicket == null){
            int idx = 0;
            for (int i = 0; i < works.size(); i++) {
                if (works.get(i).getWork_dvcd().equals(user_dvcd)){
                    idx = 100 * (i+1);
                    break;
                }
            }
            ticketRepository.save(new Ticket(kiosk, user_dvcd, todayDate, idx));
            cnt = idx;
        } else {
            updatedTicket.setCount(updatedTicket.getCount()+1);
            ticketRepository.save(updatedTicket);
            cnt = updatedTicket.getCount();
        }

        // 상담 데이터 넣기
        Counsel counsel = new Counsel(dpt, user_dvcd, cnt, todayDate, kiosk.getKiosk_id(),
                                        "00", null, 0, 0, 0, null, null);
        counselRepository.save(counsel);

        // 대기 인원 구하기 => 업무, 상담코드, 날짜, 키오스크아이디
        int wait_people = counselRepository.getTotalWaitCnt(kiosk.getKiosk_id(), user_dvcd, "00", todayDate)-1;
        // 대기 시간 구하기: 업무별 평균 대기 시간 * 대기 인원
        int avgCsnlTime = 0;
        Map<String, Double> map = statisticsService.getAvgCsnlTime(ticketInfo.dept_nm()).myAvg();
        Double value = map.get(workRepository.findWorkByWorkDvcdNm(ticketInfo.dept_nm(),ticketInfo.user_dvcd_nm()).getWork_dvcd());
        if (value != null) {
            avgCsnlTime = value.intValue();
            // 이후 로직
        } else {
            avgCsnlTime = 5;
        }
        int wait_time = estimateWaitTime(ticketInfo.dept_nm(), ticketInfo.user_dvcd_nm(), wait_people, avgCsnlTime);
        // 해당 영업점 명 구하기
        String name = ticketInfo.dept_nm();
        // 반환값: 해당 날짜의 모든 업무의 티켓 수 + 1
        return new IssuedTicketResponse(cnt, wait_time, wait_people, name, user_dvcd);
    }

    @Override
    public StartCounselResponse startCounsel(StartCounselRequest startCounselRequest) {
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        // 우선 본인 창구의 dvcd = user_dvcd와 같으면서, csnl_cd는 00(대기중)인 애들을 발급 시간 순으로 오름차순
        Department dept = departmentRepository.findByDeptNM(startCounselRequest.dept_nm()).orElse(null);
        String wd_dvcd = wicketRepository.findWdDvcdByDeptIdAndWdNum(startCounselRequest.wd_id());
        List<Counsel> counsels = counselRepository.findFirstByDepartmentAndUserDvcdAndCsnlCdOrderByTicketStime(dept, wd_dvcd, "00", todayDate).orElse(null);
        if (counsels == null || counsels.size() == 0){
            return null;
        }
        Counsel counsel = counsels.get(0);
        // 해당 상담의 상태 변경 - 상담코드, 상담 시작 시간, 대기 시간
        int user_id = wicketRepository.findUserIdByDeptIdAndWdNum(dept, startCounselRequest.wd_num());
        Wicket wicket = wicketRepository.findByDeptIdAndWdNum(dept, startCounselRequest.wd_num());
        Member member = memberRepository.findById((long) user_id).orElse(null);

// startCounsel 메서드 호출로 상담 시작 설정
        counsel.startCounsel("01", wicket, member);

        counselRepository.save(counsel);

        //출력물에 필요한 내용 반환
        return new StartCounselResponse(counsel.getCounsel_id(), startCounselRequest.dept_nm(), wicket.getWd_num(), wicket.getWd_floor(), workRepository.findWorkDvcdNmByDepartmentAndWorkDvcd(startCounselRequest.dept_nm(), wicket.getWd_dvcd()), counsel.getTicket_count());
    }

    @Override
    public EndCounselResponse endCounsel(int counselId) {
        Counsel counsel = counselRepository.findById(counselId).orElse(null);
        if (counsel == null){
            return null;
        }
        counsel.endCounsel();

        counselRepository.save(counsel);
        return new EndCounselResponse(counsel.getDepartment().getDept_id(), wicketRepository.findByWd_id(counsel.getWd_id()).getWd_num());
    }

    public int estimateWaitTime(String deptNm, String serviceName, int waitingCount, int avgCsnlTime) {
        Department dept = departmentRepository.findByDeptNM(deptNm).orElse(null);
        Work work = workRepository.findWorkByWorkDvcdNm(deptNm, serviceName);
        int numCounters = (wicketRepository.getWicketCntByDept(dept, work.getWork_dvcd())).intValue(); // 업무별 창구 수

        int csnlCounter = counselRepository.getCnslCounterCnt(dept, work.getWork_dvcd());
        // 현재 상담 중인 고객 중 가장 오래된 상담의 남은 시간
        Integer oldestRemainingTime = getOldestRemainingServiceTime(dept.getDept_id(), work.getWork_dvcd());


        // 1. 대기 인원이 없고, 빈 창구가 있을 때
        if (waitingCount == 0){
            if(numCounters > 0 && (numCounters > csnlCounter)) {
                return 0;
            } else if (numCounters == csnlCounter) {
                if (oldestRemainingTime < avgCsnlTime) {
                    return avgCsnlTime - oldestRemainingTime;
                } else {
                    return 1; // 1분 미만으로 표시
                }
            }
        }
        // 대기 인원이 여러 명일 때
        List<LocalDateTime> times = counselRepository.findByTimeDesc(dept, work.getWork_dvcd());
        int totalWaitTime = 0;
        LocalDateTime currentTime = LocalDateTime.now();
        if (times == null || times.size() == 0){
            totalWaitTime = 0;
        }
        // 내림차순 정렬된 상담 시작 시간에서 대기 인원 수만큼의 대기 시간을 계산
        for (int i = 0; i < waitingCount && i < times.size(); i++) {
            LocalDateTime startTime = times.get(i);
            int waitTime = (int) Duration.between(startTime, currentTime).toMinutes();
            totalWaitTime += waitTime;
        }
        return totalWaitTime;
    }

    private Integer getOldestRemainingServiceTime(String deptId, String serviceCode) {
        LocalDateTime currentTime = LocalDateTime.now();
        Integer maxWaitTime = counselRepository.findMaxWaitTime(currentTime, deptId, serviceCode);
        return (maxWaitTime != null) ? maxWaitTime.intValue() : 0;
    }
}
