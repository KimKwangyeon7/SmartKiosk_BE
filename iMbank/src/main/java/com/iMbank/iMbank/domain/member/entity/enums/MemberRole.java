package com.iMbank.iMbank.domain.member.entity.enums;

public enum MemberRole {
    HEADQUARTER, BRANCH;

    public static MemberRole fromName(String roleName) {
        return MemberRole.valueOf(roleName.toUpperCase());
    }
}
