package com.iMbank.iMbank.domain.wicket.service;


import com.iMbank.iMbank.domain.wicket.dto.request.CreateWicketRequest;
import com.iMbank.iMbank.domain.wicket.dto.request.KioskMoveRequest;
import com.iMbank.iMbank.domain.wicket.dto.request.UpdatedWicketInfoList;
import com.iMbank.iMbank.domain.wicket.dto.request.WicketMoveRequest;
import com.iMbank.iMbank.domain.wicket.dto.response.MapLayoutResponse;

public interface WicketService {

    MapLayoutResponse getWicketListInfo(String deptNm);

    void sendUpdatedWicketListInfo(UpdatedWicketInfoList updatedWicketInfoList);

    int createWicket(CreateWicketRequest createWicketRequest);

    void deleteWicket(int wdId);

    void updateWicket(String code);

    void moveWicket(WicketMoveRequest wicketMoveRequest);

    void deleteFloor(int floor);

    void moveKiosk(KioskMoveRequest kioskMoveRequest);
}
