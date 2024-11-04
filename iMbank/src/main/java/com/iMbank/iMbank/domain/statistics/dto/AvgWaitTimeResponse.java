package com.iMbank.iMbank.domain.statistics.dto;

import java.util.Map;

public record AvgWaitTimeResponse(
        Map<String, Double> myAvg,
        Map<String, Double> otherAvg
) {
}
