package com.iMbank.iMbank.domain.member.controller;

import com.iMbank.iMbank.domain.counsel.dto.response.ButtonInfoResponse;
import com.iMbank.iMbank.domain.department.dto.request.CreateButtonRequest;
import com.iMbank.iMbank.domain.department.dto.request.ModifyButtonRequest;
import com.iMbank.iMbank.domain.member.dto.MemberInfo;
import com.iMbank.iMbank.domain.member.dto.MemberLoginRequest;
import com.iMbank.iMbank.domain.member.dto.MemberLoginResponse;
import com.iMbank.iMbank.domain.member.dto.MemberSignupRequest;
import com.iMbank.iMbank.domain.member.service.MemberService;
import com.iMbank.iMbank.global.common.dto.Message;
import com.iMbank.iMbank.global.component.jwt.security.MemberLoginActive;
import com.iMbank.iMbank.global.component.jwt.service.JwtTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/signup")
    public ResponseEntity<Message<Void>> signupMember(@Valid @RequestBody MemberSignupRequest signupRequest) {
        memberService.signupMember(signupRequest);
        return ResponseEntity.ok().body(Message.success());
    }

    @PostMapping("/login")
    public ResponseEntity<Message<MemberLoginResponse>> loginMember(@RequestBody MemberLoginRequest loginRequest,
                                                                    HttpServletResponse response) {
        MemberLoginResponse loginResponse = memberService.loginMember(loginRequest);
        // JWT 토큰을 쿠키에 저장
        Cookie accessTokenCookie = new Cookie("accessToken", loginResponse.tokenInfo().accessToken());
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(25200); // 4200분(25200초)
        response.addCookie(accessTokenCookie);
        return ResponseEntity.ok().body(Message.success(loginResponse));
    }

    @PostMapping("/logout")
    @PreAuthorize("hasAuthority('HEADQUARTER') or hasAuthority('BRANCH')")
    public ResponseEntity<Message<Void>> logoutMember(@AuthenticationPrincipal MemberLoginActive loginActive,
                                                      HttpServletResponse response) {
        memberService.logoutMember(loginActive.email());;
        // 쿠키 삭제
        Cookie accessTokenCookie = new Cookie("accessToken", null);
        accessTokenCookie.setMaxAge(0);
        accessTokenCookie.setPath("/");
        response.addCookie(accessTokenCookie);
        return ResponseEntity.ok().body(Message.success());
    }

    @GetMapping("/get")
    @PreAuthorize("hasAuthority('HEADQUARTER') or hasAuthority('BRANCH')")
    public ResponseEntity<Message<MemberInfo>> getMember(@AuthenticationPrincipal MemberLoginActive loginActive) {
        MemberInfo info = memberService.getMember(loginActive.id());
        return ResponseEntity.ok().body(Message.success(info));
    }


    @DeleteMapping("")
    @PreAuthorize("hasAuthority('HEADQUARTER') or hasAuthority('BRANCH')")
    public ResponseEntity<Message<Void>> deleteMember(@AuthenticationPrincipal MemberLoginActive loginActive) {
        memberService.deleteMember(loginActive.id());
        return ResponseEntity.ok().body(Message.success());
    }


    @PostMapping("/reissue/accessToken/{memberEmail}")
    public ResponseEntity<Message<String>> reissueAccessToken(@PathVariable String memberEmail) {
        String reissueAccessToken = jwtTokenService.reissueAccessToken(memberEmail);
        return ResponseEntity.ok().body(Message.success(reissueAccessToken));
    }

    // 버튼 추가
    @PostMapping("/button")
    @PreAuthorize("hasAuthority('BRANCH')")
    public ResponseEntity<Message<Void>> addButton(@RequestBody CreateButtonRequest createButtonRequest) {
        memberService.addButton(createButtonRequest);
        return ResponseEntity.ok().body(Message.success());
    }


    // 버튼 수정
    @PatchMapping("/button")
    @PreAuthorize("hasAuthority('BRANCH')")
    public ResponseEntity<Message<Void>> modifyButton(@RequestBody ModifyButtonRequest modifyButtonRequest) {
        memberService.modifyButton(modifyButtonRequest);
        return ResponseEntity.ok().body(Message.success());
    }

    // 버튼 삭제
    @DeleteMapping("/button/{deptNm}/{workDvcdNm}")
    @PreAuthorize("hasAuthority('BRANCH')")
    public ResponseEntity<Message<Void>> deleteButton(@AuthenticationPrincipal MemberLoginActive loginActive,
                                                        @PathVariable String deptNm, @PathVariable String workDvcdNm) {
        CreateButtonRequest createButtonRequest = new CreateButtonRequest(deptNm, workDvcdNm);
        memberService.deleteButton(createButtonRequest);
        String reissueAccessToken = jwtTokenService.reissueAccessToken(loginActive.email());
        return ResponseEntity.ok().body(Message.success());
    }

    // 버튼 리스트 가져오기
    @GetMapping("/button/{deptNm}")
    public ResponseEntity<Message<List<ButtonInfoResponse>>> getButtonInfo(@PathVariable String deptNm) {
        System.out.println("1111111111111111111111" + deptNm);
        List<ButtonInfoResponse> buttonInfoResponses = memberService.getButtonInfo(deptNm);
        System.out.println("2222222222222222222222" + buttonInfoResponses.size());
        return ResponseEntity.ok().body(Message.success(buttonInfoResponses));
    }
}
