package com.iMbank.iMbank.domain.statistics.dto;

import java.util.Map;

public record DailyCsnlCntResponse(
        Map<String, Double> myAvg,
        Map<String, Double> otherAvg
) {
}
