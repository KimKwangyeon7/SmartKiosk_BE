package com.iMbank.iMbank.domain.counsel.dto.request;

public record StartCounselRequest(
        String dept_nm,
        int wd_num,
        int floor,
        int wd_id
) {
}
