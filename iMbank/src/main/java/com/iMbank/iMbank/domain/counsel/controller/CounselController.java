package com.iMbank.iMbank.domain.counsel.controller;

import com.iMbank.iMbank.domain.counsel.dto.IssuedTicketInfo;
import com.iMbank.iMbank.domain.counsel.dto.request.StartCounselRequest;
import com.iMbank.iMbank.domain.counsel.dto.response.EndCounselResponse;
import com.iMbank.iMbank.domain.counsel.dto.response.IssuedTicketResponse;
import com.iMbank.iMbank.domain.counsel.dto.response.StartCounselResponse;
import com.iMbank.iMbank.domain.counsel.service.CounselService;
import com.iMbank.iMbank.global.common.dto.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/counsel")
public class CounselController {

    private final CounselService counselService;

    @PostMapping("/issue")
    public ResponseEntity<Message<IssuedTicketResponse>> issueTicket(@RequestBody IssuedTicketInfo issuedTicketInfo) {
        IssuedTicketResponse issuedTicketResponse = counselService.issueTicket(issuedTicketInfo);
        return ResponseEntity.ok().body(Message.success(issuedTicketResponse));
    }

    @PostMapping("/start")
    public ResponseEntity<Message<StartCounselResponse>> startCounsel(@RequestBody StartCounselRequest startCounselRequest) {
        StartCounselResponse startCounselResponse = counselService.startCounsel(startCounselRequest);
        if (startCounselResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Message.fail(null, "다음 고객이 없습니다"));
        }
        return ResponseEntity.ok().body(Message.success(startCounselResponse));
    }

    @PostMapping("/end")
    public ResponseEntity<Message<EndCounselResponse>> endCounsel(@RequestParam int counselId) {
        EndCounselResponse endCounselResponse = counselService.endCounsel(counselId);
        return ResponseEntity.ok().body(Message.success(endCounselResponse));
    }

//    @GetMapping("/get")
//    @PreAuthorize("hasAuthority('HEADQUARTER') or hasAuthority('BRANCH')")
//    public ResponseEntity<Message<MemberInfo>> getMember(@AuthenticationPrincipal MemberLoginActive loginActive) {
//        MemberInfo info = memberService.getMember(loginActive.id());
//        return ResponseEntity.ok().body(Message.success(info));
//    }
}
