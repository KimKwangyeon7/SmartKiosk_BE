package com.iMbank.iMbank.domain.counsel.dto.response;

public record ButtonInfoResponse(
        String dept_id,
        String dept_nm,
        String work_dvcd,
        String work_dvcd_nm,
        int wait_time,
        int wait_people
) {
}
