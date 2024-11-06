package com.iMbank.iMbank.global.component.jwt.security;

import com.iMbank.iMbank.domain.member.entity.enums.MemberRole;

public record MemberLoginActive(
        int id,
        String email,
        String name,
        String dept_nm,
        MemberRole role
) {
}
