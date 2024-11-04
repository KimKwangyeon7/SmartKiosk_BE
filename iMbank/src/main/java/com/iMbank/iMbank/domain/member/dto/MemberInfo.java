package com.iMbank.iMbank.domain.member.dto;


import com.iMbank.iMbank.domain.member.entity.enums.MemberRole;

public record MemberInfo(
        int user_id,
        String email,
        String name,
        String nickname,
        String profileImage,
        MemberRole role
) {
}
