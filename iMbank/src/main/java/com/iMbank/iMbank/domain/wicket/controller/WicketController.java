package com.iMbank.iMbank.domain.wicket.controller;

import com.iMbank.iMbank.domain.wicket.dto.request.UpdatedWicketInfoList;
import com.iMbank.iMbank.domain.wicket.dto.response.MapLayoutResponse;
import com.iMbank.iMbank.domain.wicket.service.WicketService;
import com.iMbank.iMbank.global.common.dto.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wicket")
public class WicketController {

    private final WicketService wicketService;

    @GetMapping("/{deptNm}")
    //@PreAuthorize("hasAuthority('BRANCH')")
    public ResponseEntity<Message<MapLayoutResponse>> getWicketListInfo(@PathVariable String deptNm) {
        MapLayoutResponse mapLayoutResponse = wicketService.getWicketListInfo(deptNm);
        return ResponseEntity.ok().body(Message.success(mapLayoutResponse));
    }


    // 업무별 고객 1명당 걸리는 평균 상담 시간
    @PatchMapping("")
    //@PreAuthorize("hasAuthority('BRANCH')")
    public ResponseEntity<Message<Void>> sendUpdatedWicketListInfo(@RequestBody UpdatedWicketInfoList updatedWicketInfoList) {
        System.out.println(updatedWicketInfoList);
        wicketService.sendUpdatedWicketListInfo(updatedWicketInfoList);
        return ResponseEntity.ok().body(Message.success());
    }


}
