package com.iMbank.iMbank.domain.counsel.dto.response;

public record StartCounselResponse(
        int counsel_id,
        String dept_nm,
        int wd_num,
        int wd_floor,
        String wd_dvcd_nm,
        int cnt
) {
}
