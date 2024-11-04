package com.iMbank.iMbank.domain.member.service;

import com.iMbank.iMbank.domain.counsel.dto.response.ButtonInfoResponse;
import com.iMbank.iMbank.domain.department.dto.request.CreateButtonRequest;
import com.iMbank.iMbank.domain.department.dto.request.ModifyButtonRequest;
import com.iMbank.iMbank.domain.department.entity.Work;
import com.iMbank.iMbank.domain.member.dto.MemberInfo;
import com.iMbank.iMbank.domain.member.dto.MemberLoginRequest;
import com.iMbank.iMbank.domain.member.dto.MemberLoginResponse;
import com.iMbank.iMbank.domain.member.dto.MemberSignupRequest;

import java.util.List;

public interface MemberService {

    void signupMember(MemberSignupRequest signupRequest);

    MemberLoginResponse loginMember(MemberLoginRequest loginRequest);

    void logoutMember(String email);

    MemberInfo getMember(int memberId);

    void deleteMember(int memberId);

    void addButton(CreateButtonRequest createButtonRequest);

    void modifyButton(ModifyButtonRequest modifyButtonRequest);

    void deleteButton(CreateButtonRequest createButtonRequest);

    List<ButtonInfoResponse> getButtonInfo(String deptNm);

    List<Work> getButtonList(String deptNm);
}
