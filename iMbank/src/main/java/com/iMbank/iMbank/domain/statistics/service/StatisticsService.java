package com.iMbank.iMbank.domain.statistics.service;

import com.iMbank.iMbank.domain.statistics.dto.AvgCsnlTimeResponse;
import com.iMbank.iMbank.domain.statistics.dto.AvgWaitTimeResponse;
import com.iMbank.iMbank.domain.statistics.dto.DailyCsnlCntResponse;

import java.util.Map;

public interface StatisticsService {


    AvgCsnlTimeResponse getAvgCsnlTime(String deptNm);
    DailyCsnlCntResponse getDailyCnt(String deptNm);
    Map<String, Map<Integer, Long>> getPeriodCnt(String deptNm, String year);
    Map<String, Long> getWorkPercentage(String deptNm);
    Map<Integer, Integer> getAvgCntByTime(String deptNm);
    Map<String, Long> getWicketPercentage(String deptNm);
    AvgWaitTimeResponse getAvgWaitTime(String deptNm);
}
