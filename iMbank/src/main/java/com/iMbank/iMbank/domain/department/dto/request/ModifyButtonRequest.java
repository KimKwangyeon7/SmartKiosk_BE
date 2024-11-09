package com.iMbank.iMbank.domain.department.dto.request;

public record ModifyButtonRequest(
        String dept_nm,
        String new_work_dvcd_nm,
        String old_work_dvcd_nm,
        int color
) {
}
