package com.iMbank.iMbank.domain.wicket.controller;

import com.iMbank.iMbank.domain.wicket.dto.request.CreateWicketRequest;
import com.iMbank.iMbank.domain.wicket.dto.request.KioskMoveRequest;
import com.iMbank.iMbank.domain.wicket.dto.request.UpdatedWicketInfoList;
import com.iMbank.iMbank.domain.wicket.dto.request.WicketMoveRequest;
import com.iMbank.iMbank.domain.wicket.dto.response.MapLayoutResponse;
import com.iMbank.iMbank.domain.wicket.service.WicketService;
import com.iMbank.iMbank.global.common.dto.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @DeleteMapping("/{wdId}")
    //@PreAuthorize("hasAuthority('BRANCH')")
    public ResponseEntity<Message<Void>> deleteWicket(@PathVariable int wdId) {
        System.out.println(wdId);
        wicketService.deleteWicket(wdId);
        return ResponseEntity.ok().body(Message.success(null));
    }

    @PatchMapping("/info")
    //@PreAuthorize("hasAuthority('BRANCH')")
    public ResponseEntity<Message<Void>> updateWicket(@RequestParam String code) {
        System.out.println(code);
        wicketService.updateWicket(code);
        return ResponseEntity.ok().body(Message.success());
    }

    @PatchMapping("/move")
    //@PreAuthorize("hasAuthority('BRANCH')")
    public ResponseEntity<Message<Void>> moveWicket(@RequestBody WicketMoveRequest wicketMoveRequest) {
        wicketService.moveWicket(wicketMoveRequest);
        return ResponseEntity.ok().body(Message.success());
    }

    @PatchMapping("/kiosk/move")
    //@PreAuthorize("hasAuthority('BRANCH')")
    public ResponseEntity<Message<Void>> moveKiosk(@RequestBody KioskMoveRequest kioskMoveRequest) {
        wicketService.moveKiosk(kioskMoveRequest);
        return ResponseEntity.ok().body(Message.success());
    }

    @DeleteMapping("/floor/{floor}")
    //@PreAuthorize("hasAuthority('BRANCH')")
    public ResponseEntity<Message<Void>> moveWicket(@PathVariable int floor) {
        wicketService.deleteFloor(floor);
        return ResponseEntity.ok().body(Message.success());
    }


}
