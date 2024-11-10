package com.iMbank.iMbank.domain.wicket.dto.response;

import com.iMbank.iMbank.domain.kiosk.dto.KioskInfo;
import com.iMbank.iMbank.domain.wicket.dto.WicketListInfo;

import java.util.List;


public record MapLayoutResponse(
        WicketListInfo layouts,
        KioskInfo kiosks,
        int[] floors,
        List<String> detail,
        List<String> counsels
) {
}
