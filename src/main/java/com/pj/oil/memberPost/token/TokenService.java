package com.pj.oil.memberPost.token;

import com.pj.oil.memberPost.member.Member;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * DB에 저장된 AccessToken 의 권한을 취소시킴
     *
     * @param member
     */
    public void revokeAllMemberAccessTokens(Member member) {
        LOGGER.info("[revokeAllMemberAccessTokens]");
        var validMemberToken = accessTokenRepository.findAllValidTokensByMember(member.getId());
        if (validMemberToken.isEmpty()) return;
        validMemberToken.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
        });
        accessTokenRepository.saveAll(validMemberToken);
    }

    /**
     * DB에 토큰(JWT) 저장
     *
     * @param member      - DB 에 있는 사용자 자료형(TABLE)
     * @param accessToken - 생성된(저장할) 토큰
     */
    public void saveMemberAccessToken(Member member, String accessToken) {
        LOGGER.info("[saveMemberAccessToken]");
        var token = Token.builder()
                .member(member)
                .token(accessToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        accessTokenRepository.save(token);
    }

    public void saveMemberRefreshToken(Member member, String refreshToken) {
        LOGGER.info("[saveMemberRefreshToken]");
        var token = RefreshToken.builder()
                .email(member.getEmail())
                .token(refreshToken)
                .build();
        refreshTokenRepository.save(token);
    }
}
