package com.iMbank.iMbank.domain.wicket.controller;

import com.iMbank.iMbank.domain.wicket.dto.request.CreateWicketRequest;
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



    @PatchMapping("")
    //@PreAuthorize("hasAuthority('BRANCH')")
    public ResponseEntity<Message<Void>> sendUpdatedWicketListInfo(@RequestBody UpdatedWicketInfoList updatedWicketInfoList) {
        System.out.println(updatedWicketInfoList);
        wicketService.sendUpdatedWicketListInfo(updatedWicketInfoList);
        return ResponseEntity.ok().body(Message.success());
    }

    @PostMapping("")
    //@PreAuthorize("hasAuthority('BRANCH')")
    public ResponseEntity<Message<Integer>> createWicket(@RequestBody CreateWicketRequest createWicketRequest) {
        System.out.println(createWicketRequest);
        return ResponseEntity.ok().body(Message.success(wicketService.createWicket(createWicketRequest)));
    }

//    @DeleteMapping("/{wdId}")
//    //@PreAuthorize("hasAuthority('BRANCH')")
//    public ResponseEntity<Message<Void>> deleteWicket(@PathVariable int wdId) {
//        wicketService.deleteWicket(wdId);
//        return ResponseEntity.ok().body(Message.success(null));
//    }


}
