package com.iMbank.iMbank.domain.kiosk.dto;

import java.util.Map;

public record KioskInfo(
         Map<Integer, String[]> kioskInfo
) {
}
