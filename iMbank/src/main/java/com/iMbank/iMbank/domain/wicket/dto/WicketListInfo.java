package com.iMbank.iMbank.domain.wicket.dto;

import java.util.Map;

public record WicketListInfo(
        Map<Integer, Map<String, String>> wicketInfo
) {
}
