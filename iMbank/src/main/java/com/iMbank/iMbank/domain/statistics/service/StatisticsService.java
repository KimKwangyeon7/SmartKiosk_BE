package com.iMbank.iMbank.domain.statistics.service;

import com.iMbank.iMbank.domain.statistics.dto.*;

import java.util.List;
import java.util.Map;

public interface StatisticsService {


    AvgCsnlTimeResponse getAvgCsnlTime(String deptNm);
    DailyCsnlCntResponse getDailyCnt(String deptNm);
    PeriodCntResponse getPeriodCnt(String deptNm, String year);
    Map<String, Long> getWorkPercentage(String deptNm);
    Map<Integer, Double> getAvgCntByTime(String deptNm);
    Map<String, Long> getWicketPercentage(String deptNm);
    AvgWaitTimeResponse getAvgWaitTime(String deptNm);
    YearCntResponse getYearCnt(String deptNm);
    Map<String, List<Long>> getAvgCntByMonth(String deptNm, String year, String Month);
}
