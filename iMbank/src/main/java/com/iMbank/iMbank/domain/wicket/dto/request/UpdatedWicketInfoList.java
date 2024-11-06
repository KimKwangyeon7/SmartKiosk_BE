package com.iMbank.iMbank.domain.wicket.dto.request;

import java.util.List;
import java.util.Map;

public record UpdatedWicketInfoList(
        List<Map<String, String>> counters,
        List<Map<String, String>> kiosks
) {
}
