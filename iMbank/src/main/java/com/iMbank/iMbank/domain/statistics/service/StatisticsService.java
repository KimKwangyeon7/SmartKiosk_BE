package com.iMbank.iMbank.domain.statistics.service;

import com.iMbank.iMbank.domain.statistics.dto.*;

import java.util.Map;

public interface StatisticsService {


    AvgCsnlTimeResponse getAvgCsnlTime(String deptNm);
    DailyCsnlCntResponse getDailyCnt(String deptNm);
    PeriodCntResponse getPeriodCnt(String deptNm, String year);
    Map<String, Long> getWorkPercentage(String deptNm);
    Map<Integer, Integer> getAvgCntByTime(String deptNm, int month);
    Map<String, Long> getWicketPercentage(String deptNm);
    AvgWaitTimeResponse getAvgWaitTime(String deptNm);
    YearCntResponse getYearCnt(String deptNm);
}
