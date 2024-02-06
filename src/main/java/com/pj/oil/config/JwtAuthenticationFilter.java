package com.pj.oil.config;

import com.pj.oil.member.Member;
import com.pj.oil.member.MemberRepository;
import com.pj.oil.token.AccessTokenRepository;
import com.pj.oil.token.RefreshTokenRepository;
import com.pj.oil.token.TokenService;
import com.pj.oil.util.CookieUtil;
import com.pj.oil.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter { // 요청당 한 번씩 실행되는 필터

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AccessTokenRepository accessTokenRepository;
    private final TokenService tokenService;
    private final CookieUtil cookieUtil;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, // 들어오는 요청
            @NonNull HttpServletResponse response, // 나가는 응답
            @NonNull FilterChain filterChain // 필터 체인
    ) throws ServletException, IOException {
        LOGGER.info("[doFilterInternal]");
        String requestURI = request.getRequestURI();
        Optional<Cookie> accessCookie = cookieUtil.getCookie(request, "access_token");
        Optional<Cookie> refreshCookie = cookieUtil.getCookie(request, "refresh_token");

        // 1. 어떠한 쿠키도 가지고 있지 않음
        if (accessCookie.isEmpty() && refreshCookie.isEmpty()) {
            // 1.1 로그인 정보 없음
            //
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                // 1.2 인증 만료
                response.sendRedirect("/login");
            }
            filterChain.doFilter(request, response);
            return;
        }

        // /login 요청인 경우
        if (requestURI.equals("/login")) {
            filterChain.doFilter(request, response);
            return;
        }
        Boolean isTokenValid;
        boolean isAccessToken;
        // 2. access 토큰이 존재하면
        if (accessCookie.isPresent()) {
            String accessToken = accessCookie.get().getValue();
            String memberEmail = jwtUtil.extractUsername(accessToken);
            UserDetails memberDetail = userDetailsService.loadUserByUsername(memberEmail);
            try {
                if (jwtUtil.isTokenValid(accessToken, memberDetail)) {
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        setAuthentication(memberDetail, request);
                    }
                } else if (!jwtUtil.isTokenValid(accessToken, memberDetail)) {
                    handleExpiredAccessToken(request, response);
                }
            } catch (JwtException e) {
                handleJwtException(response, e);
            }
        } else if (refreshCookie.isPresent()) {
            String refreshToken = refreshCookie.get().getValue();
            String memberEmail = jwtUtil.extractUsername(refreshToken);
            UserDetails memberDetail = userDetailsService.loadUserByUsername(memberEmail);
            try {
                if (jwtUtil.isTokenValid(refreshToken, memberDetail)) {
                    renewAccessToken(response, refreshToken);
                } else {
                    redirectToLogin(response);
                }
            } catch (JwtException e) {
                handleJwtException(response, e);
            }
        } else {
            redirectToLogin(response);
        }

        filterChain.doFilter(request, response);
    }

    private void handleJwtException(HttpServletResponse response, JwtException e) throws IOException {
        redirectToLogin(response);
    }

    private void handleExpiredAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Optional<Cookie> refreshCookie = cookieUtil.getCookie(request, "refresh_token");
        if (refreshCookie.isPresent()) {
            String refreshToken = refreshCookie.get().getValue();
            renewAccessToken(response, refreshToken);
        } else {
            redirectToLogin(response);
        }
    }

    private void renewAccessToken(HttpServletResponse response, String refreshToken) {
        String username = jwtUtil.extractUsername(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        String newAccessToken = jwtUtil.generateToken(userDetails);
        Cookie newAccessCookie = cookieUtil.createTokenCookie("access_token", newAccessToken, jwtUtil.getJwtExpirationSecond());
        Member member = memberRepository.findByEmail(username).get();
        tokenService.revokeAllMemberAccessTokens(member);
        tokenService.saveMemberAccessToken(member, newAccessToken);
        response.addCookie(newAccessCookie);
    }

    private void redirectToLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect("/login");
    }

    private void setAuthentication(UserDetails memberDetail, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                memberDetail, null, memberDetail.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}