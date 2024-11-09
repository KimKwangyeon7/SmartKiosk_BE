package com.iMbank.iMbank.domain.department.dto.request;

public record CreateButtonRequest(
        String dept_nm,
        String work_dvcd_nm,
        String left_high,
        String right_low,
        int color
) {
}
