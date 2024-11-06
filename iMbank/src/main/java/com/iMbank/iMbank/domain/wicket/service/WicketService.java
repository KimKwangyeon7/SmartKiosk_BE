package com.iMbank.iMbank.domain.wicket.service;


import com.iMbank.iMbank.domain.wicket.dto.request.UpdatedWicketInfoList;
import com.iMbank.iMbank.domain.wicket.dto.response.MapLayoutResponse;

public interface WicketService {

    MapLayoutResponse getWicketListInfo(String deptNm);

    void sendUpdatedWicketListInfo(UpdatedWicketInfoList updatedWicketInfoList);
}
