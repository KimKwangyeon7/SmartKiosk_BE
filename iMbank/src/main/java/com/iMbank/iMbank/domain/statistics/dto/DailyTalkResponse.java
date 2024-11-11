package com.iMbank.iMbank.domain.statistics.dto;

public record DailyTalkResponse(
        String day,
        int visited,
        int waitAvg,
        int busyTime,
        String closeTime
) {
}
