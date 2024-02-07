package com.pj.oil.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class CookieUtil {

    // 쿠키 생성
    public Cookie createTokenCookie(String cookieName, String value, long tokenMaxAge) {
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) tokenMaxAge);
        cookie.setPath("/");
        return cookie;
    }

    public Cookie[] combineCookies(Optional<Cookie> accessCookie, Optional<Cookie> refreshCookie) {
        return Stream.of(accessCookie, refreshCookie)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toArray(Cookie[]::new);
    }

    // 쿠키 삭제 (존재 여부 확인 포함)
    public void removeCookie(HttpServletResponse response, String cookieName) {
        {
            Cookie cookie = new Cookie(cookieName, null);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }

    // 여러 쿠키 추가
    public void addCookies(Cookie[] cookies, HttpServletResponse response) {
        for (Cookie cookie : cookies) {
            response.addCookie(cookie);
        }
    }

    // 단일 쿠키 추가
    public void addCookie(Cookie cookie, HttpServletResponse response) {
        response.addCookie(cookie);
    }

    // 모든 쿠키 가져오기
    public Cookie[] getCookies(HttpServletRequest request) {
        return request.getCookies();
    }

    // 쿠키 배열에서 특정 쿠키 찾기
    public Optional<Cookie> getCookie(Cookie[] cookies, String cookieName) {
        return Optional.ofNullable(cookies)
                .flatMap(cs -> Arrays.stream(cs)
                        .filter(cookie -> cookie.getName().equals(cookieName))
                        .findFirst());
    }

    // 요청에서 특정 쿠키 찾기
    public Optional<Cookie> getCookie(HttpServletRequest request, String cookieName) {
        return Optional.ofNullable(request.getCookies())
                .flatMap(cookies -> Arrays.stream(cookies)
                        .filter(cookie -> cookie.getName().equals(cookieName))
                        .findFirst());
    }

    public String getCookieValue(Cookie cookie) {
        return cookie.getValue();
    }

}
