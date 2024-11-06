package com.iMbank.iMbank.domain.statistics.dto;

import java.util.Map;

public record YearCntResponse(
        Map<String, Map<String, Long>> my,
        Map<String, Map<String, Long>> other
) {
}
