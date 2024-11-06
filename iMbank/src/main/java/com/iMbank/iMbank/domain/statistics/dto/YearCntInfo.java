package com.iMbank.iMbank.domain.statistics.dto;

import java.util.Map;

public record YearCntInfo(
        Map<String, Long> my,
        Map<String, Long> other
) {
}
