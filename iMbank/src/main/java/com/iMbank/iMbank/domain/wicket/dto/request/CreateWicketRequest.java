package com.iMbank.iMbank.domain.wicket.dto.request;

public record CreateWicketRequest(
        String deptNm,
        String wdDvcdNm,
        int wdNum,
        int wdFloor
) {
}
