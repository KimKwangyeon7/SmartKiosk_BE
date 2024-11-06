package com.iMbank.iMbank.domain.counsel.repository;


import com.iMbank.iMbank.domain.department.entity.Department;
import com.iMbank.iMbank.domain.statistics.dto.PeriodCntInfo;
import com.iMbank.iMbank.domain.statistics.dto.YearCntInfo;

import java.time.Year;

public interface CounselCustom {
    double getAvgCnt(Department deptId, String day);
    double getOtherAvgCnt(Department deptId, String day);
    PeriodCntInfo getPeriodCnt(Department deptId, String code, String year, int period);
    YearCntInfo getCntByDeptAndYear(Department dept, String code, int year);
}
