package com.iMbank.iMbank.global.component.jwt.service;


import com.iMbank.iMbank.domain.member.dto.MemberInfo;
import com.iMbank.iMbank.domain.member.dto.MemberLoginResponse;
import com.iMbank.iMbank.domain.member.entity.Member;
import com.iMbank.iMbank.domain.member.exception.MemberErrorCode;
import com.iMbank.iMbank.domain.member.exception.MemberException;
import com.iMbank.iMbank.domain.member.repository.MemberRepository;
import com.iMbank.iMbank.global.component.jwt.JwtTokenProvider;
import com.iMbank.iMbank.global.component.jwt.dto.JwtTokenInfo;
import com.iMbank.iMbank.global.component.jwt.repository.RefreshTokenRepository;
import com.iMbank.iMbank.global.exception.GlobalErrorCode;
import com.iMbank.iMbank.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    @Override
    public MemberLoginResponse issueAndSaveJwtToken(Member member) {
        String accessToken = jwtTokenProvider.issueAccessToken(member);
        String refreshToken = jwtTokenProvider.issueRefreshToken();

        log.info("== {} 회원에 대한 토큰 발급: {}", member.getEmail(), accessToken);

        try {
            refreshTokenRepository.save(member.getEmail(), refreshToken);
        } catch (Exception e) {
            throw new GlobalException(GlobalErrorCode.REDIS_CONNECTION_FAILURE);
        }

        JwtTokenInfo tokenInfo = new JwtTokenInfo(accessToken);

        MemberInfo memberInfo = new MemberInfo(
                member.getUser_id(),
                member.getEmail(),
                member.getName(),
                member.getDept_nm(),
                member.getRole()
        );

        return new MemberLoginResponse(tokenInfo, memberInfo);
    }

    @Override
    public String reissueAccessToken(String email) {
        String refreshToken = refreshTokenRepository.find(email)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.REDIS_NOT_TOKEN));

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));

        return jwtTokenProvider.issueAccessToken(member);
    }
}
