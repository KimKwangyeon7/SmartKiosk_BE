package com.iMbank.iMbank.domain.statistics.dto;

import java.util.Map;

public record PeriodCntResponse(
        Map<String, Map<Integer, Long>> my,
        Map<String, Map<Integer, Long>> other
) {
}
