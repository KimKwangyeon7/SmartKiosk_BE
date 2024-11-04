package com.iMbank.iMbank.global.component.jwt.service;

import com.iMbank.iMbank.domain.member.dto.MemberLoginResponse;
import com.iMbank.iMbank.domain.member.entity.Member;

public interface JwtTokenService {
    MemberLoginResponse issueAndSaveJwtToken(Member member);

    String reissueAccessToken(String email);
}
