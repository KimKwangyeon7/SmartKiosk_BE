package com.iMbank.iMbank.domain.wicket.dto.request;

public record WicketMoveRequest(
        int floor,
        String from,
        String to,
        String counterName
) {
}
