package com.iMbank.iMbank.domain.member.dto;

import com.iMbank.iMbank.global.component.jwt.dto.JwtTokenInfo;

public record MemberLoginResponse(JwtTokenInfo tokenInfo, MemberInfo memberInfo) {
}
