package com.iMbank.iMbank.domain.statistics.controller;

import com.iMbank.iMbank.domain.statistics.dto.AvgCsnlTimeResponse;
import com.iMbank.iMbank.domain.statistics.dto.AvgWaitTimeResponse;
import com.iMbank.iMbank.domain.statistics.dto.DailyCsnlCntResponse;
import com.iMbank.iMbank.domain.statistics.service.StatisticsService;
import com.iMbank.iMbank.global.common.dto.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    // 업무별 고객 1명당 걸리는 평균 상담 시간
    @PostMapping("/code")
    //@PreAuthorize("hasAuthority('HEADQUARTER')")
    public ResponseEntity<Message<AvgCsnlTimeResponse>> getAvgCsnlTime(@RequestParam String deptNm) {
        AvgCsnlTimeResponse avgCsnlTimeResponse = statisticsService.getAvgCsnlTime(deptNm);
        return ResponseEntity.ok().body(Message.success(avgCsnlTimeResponse));
    }

    // 업무별 고객 1명당 걸리는 평균 대기 시간
    @PostMapping("/wait")
    //@PreAuthorize("hasAuthority('HEADQUARTER')")
    public ResponseEntity<Message<AvgWaitTimeResponse>> getAvgWaitTime(@RequestParam String deptNm) {
        AvgWaitTimeResponse avgWaitTimeResponse = statisticsService.getAvgWaitTime(deptNm);
        return ResponseEntity.ok().body(Message.success(avgWaitTimeResponse));
    }

    // 요일별 평균 고객 수
    @PostMapping("/day")
    //@PreAuthorize("hasAuthority('HEADQUARTER')")
    public ResponseEntity<Message<DailyCsnlCntResponse>> getDailyCsnlTime(@RequestParam String deptNm) {
        DailyCsnlCntResponse dailyCsnlCntResponse = statisticsService.getDailyCnt(deptNm);
        return ResponseEntity.ok().body(Message.success(dailyCsnlCntResponse));
    }

    // 분기별 고객 수 변화 - 영업별 => 년도
    @PostMapping("/period")
    //@PreAuthorize("hasAuthority('HEADQUARTER')")
    public ResponseEntity<Message<Map<String, Map<Integer, Long>>>> getPeriodCnt(@RequestParam String deptNm, @RequestParam String year) {
        Map<String, Map<Integer, Long>> map = statisticsService.getPeriodCnt(deptNm, year);
        return ResponseEntity.ok().body(Message.success(map));
    }

    // 업무별 총 고객 수에서 차지하는 비율
    @PostMapping("/work")
    //@PreAuthorize("hasAuthority('HEADQUARTER')")
    public ResponseEntity<Message<Map<String, Long>>> getWorkPercentage(@RequestParam String deptNm) {
        Map<String, Long> map = statisticsService.getWorkPercentage(deptNm);
        return ResponseEntity.ok().body(Message.success(map));
    }

    // 시간대별 고객 평균
    @PostMapping("/time")
    //@PreAuthorize("hasAuthority('HEADQUARTER')")
    public ResponseEntity<Message<Map<Integer, Integer>>> getAvgCntByTime(@RequestParam String deptNm) {
        Map<Integer, Integer> map = statisticsService.getAvgCntByTime(deptNm);
        return ResponseEntity.ok().body(Message.success(map));
    }

    // 창구 중 업무 비율
    @PostMapping("/wicket")
    //@PreAuthorize("hasAuthority('HEADQUARTER')")
    public ResponseEntity<Message<Map<String, Long>>> getWicketPercentage(@RequestParam String deptNm) {
        Map<String, Long> map = statisticsService.getWicketPercentage(deptNm);
        return ResponseEntity.ok().body(Message.success(map));
    }

}
