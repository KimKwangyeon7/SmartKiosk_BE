package com.iMbank.iMbank.domain.counsel.repository;


import com.iMbank.iMbank.domain.department.entity.Department;

public interface CounselCustom {
    double getAvgCnt(Department deptId, String day);
    double getOtherAvgCnt(Department deptId, String day);
    long getPeriodCnt(Department deptId, String code, String year, int period);
}
