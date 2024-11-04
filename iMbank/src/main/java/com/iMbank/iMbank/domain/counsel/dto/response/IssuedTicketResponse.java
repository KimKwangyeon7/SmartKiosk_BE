package com.iMbank.iMbank.domain.counsel.dto.response;

public record IssuedTicketResponse(
        int count,
        int wait_time,
        int wait_people,
        String dept_nm,
        String user_dvcd
) {
}