package com.pj.oil.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pj.oil.config.JwtService;
import com.pj.oil.member.Member;
import com.pj.oil.member.MemberRepository;
import com.pj.oil.member.Role;
import com.pj.oil.token.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RedisRefreshTokenService redisRefreshTokenService;
    /**
     * 사용자 등록 및 토큰(JWT) 발급
     *
     * @param request - 사용자 정보
     * @return AuthenticationResponse 사용자 정보로 만든 토큰(JWT)
     */
    public AuthenticationResponse signup(SignupRequest request) {
        var member = Member.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // 비밀번호 암호화
                .role(Role.USER)
                .build();
        var savedMember = memberRepository.save(member); // 회원 정보 저장
        var jwtToken = jwtService.generateToken(member); // access 토큰 생성
        var refreshToken = jwtService.generateRefreshToken(savedMember); // refresh 토큰 생성
        saveMemberAccessToken(savedMember, jwtToken);
        saveMemberRefreshToken(savedMember, refreshToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build(); // 생성된 토큰으로 AuthenticationResponse 반환
    }


    public Member authenticatedMember(LoginRequest request) {
        authenticationManager.authenticate( // 사용자 인증
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        return memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    /**
     * 사용자 인증 및 토큰(JWT) 발급
     *
     * @param request - 사용자 인증 데이터
     * @return 사용자 인증 정보로 만든 토큰(JWT)
     */
    public AuthenticationResponse login(LoginRequest request) {
        LOGGER.info("[login]");
        var member = authenticatedMember(request);
        var accessToken = jwtService.generateToken(member); // JWT 토큰 생성
        var refreshToken = jwtService.generateRefreshToken(member);
        LOGGER.info("[login] access-token: {}, refresh-token: {}", accessToken, refreshToken);
        saveMemberRefreshToken(member, refreshToken);
        LOGGER.info("[save-refresh-token]");
        revokeAllMemberAccessTokens(member);
        LOGGER.info("[revoke-access-token]");
        saveMemberAccessToken(member, accessToken);
        LOGGER.info("[save-access-token]");
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build(); // 생성된 토큰으로 AuthenticationResponse 반환
    }

    /**
     * DB에 저장된 AccessToken 의 권한을 취소시킴
     * @param member
     */
    private void revokeAllMemberAccessTokens(Member member) {
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
     * @param member   - DB 에 있는 사용자 자료형(TABLE)
     * @param accessToken - 생성된(저장할) 토큰
     */
    private void saveMemberAccessToken(Member member, String accessToken) {
        var token = Token.builder()
                .member(member)
                .token(accessToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        accessTokenRepository.save(token);
    }

    private void saveMemberRefreshToken(Member member, String refreshToken) {
        var token = RefreshToken.builder()
                .email(member.getEmail())
                .token(refreshToken)
                .build();
        refreshTokenRepository.save(token);
    }

    public void processAccessTokenRefresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendErrorResponse(response, SC_BAD_REQUEST, "Invalid Authorization header!");
            return;
        }
        final String refreshToken = authHeader.substring(7);
        final String memberId = jwtService.extractUsername(refreshToken);
        if (memberId != null) { // 존재하는 사용자
            var member = memberRepository.findByEmail(memberId)
                    .orElseThrow(() -> new UsernameNotFoundException("Member not found"));

            if (!redisRefreshTokenService.isRefreshTokenPresent(refreshToken)) { // 존재하는 refreshToken
                sendErrorResponse(response, SC_UNAUTHORIZED, "Invalid or expired refresh token!");
                return;
            }
            // 인증된 사용자이므로 일단 accessToken 재발급
            var accessToken = jwtService.generateToken(member);
            revokeAllMemberAccessTokens(member);
            saveMemberAccessToken(member, accessToken);
            // refreshToken 유효성 검사
            if (jwtService.isTokenValid(refreshToken, member)) { // refreshToken 유효성 검증 성공시 refreshToken 재발급 필요x
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                response.setStatus(SC_OK);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            } else { // refreshToken 유효성 검증 실패시 refreshToken 재발급
                var newRefreshToken = jwtService.generateRefreshToken(member); // refresh 토큰 생성
                saveMemberRefreshToken(member, newRefreshToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(newRefreshToken)
                        .build();
                response.setStatus(SC_OK);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private static void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.getWriter().write(message);
    }
}