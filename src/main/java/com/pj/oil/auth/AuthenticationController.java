package com.pj.oil.auth;

import com.pj.oil.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final AuthenticationService authenticationService;
    private final CookieUtil cookieUtil;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    @GetMapping("/signup")
    public String signupPage(Model model) {
        LOGGER.info("[signupPage]GET \"/signup\"");
        SignupRequest signupRequest = new SignupRequest();
        model.addAttribute("signupRequest", signupRequest);
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute SignupRequest signupRequest, BindingResult bindingResult, HttpServletResponse response) {
        LOGGER.info("[signup]POST \"/signup\", parameters={}", signupRequest.toString());
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시 에러 메시지와 함께 회원가입 폼으로 리다이렉트
            LOGGER.info("[signup] validate fail, redirect signup page");
            return "signup";
        }
        AuthenticationResponse authenticationToken;
        try {
            // 토큰을 유연하게 활용하기 위해 객체에 저장
            authenticationToken = authenticationService.signup(signupRequest);
        } catch (DataIntegrityViolationException e) {
            LOGGER.warn("[signup] warn: {}", String.valueOf(e));
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup";
        } catch (Exception e) {
            LOGGER.warn("[signup] warn: {}", String.valueOf(e));
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup";
        }
        // Refresh 토큰과 Access 토큰을 쿠키에 저장
        Cookie refreshCookie = cookieUtil.createCookie("refresh_token", authenticationToken.getRefreshToken(), (int) refreshExpiration);
        Cookie accessCookie = cookieUtil.createCookie("access_token", authenticationToken.getAccessToken(), (int) jwtExpiration);
        // HttpServletResponse 에 쿠키 추가
        response.addCookie(refreshCookie);
        response.addCookie(accessCookie);

        LOGGER.info("[signup] success, redirect login page");
        return "redirect:/login"; // 회원가입 성공 시 로그인 페이지로 리다이렉트
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        LOGGER.info("[loginPage]GET \"/login\"");
        LoginRequest loginRequest = new LoginRequest();
        model.addAttribute("loginRequest", loginRequest);
        return "login"; // 'src/main/resources/templates/login.html' 뷰를 반환
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginRequest loginRequest,
                        BindingResult bindingResult,
                        HttpServletResponse response) {
        LOGGER.info("[login]POST \"/login\", parameters={}", loginRequest.toString());
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시 에러 메시지와 함께 로그인 폼으로 리다이렉트
            LOGGER.info("bindingResult: {}", bindingResult);
            bindingResult.reject("BadCredentialsException", "로그인 아이디 및 비밀번호를 확인해주세요.");
            return "login";
        }
        AuthenticationResponse authenticationToken;
        try {
            // 토큰을 유연하게 활용하기 위해 객체에 저장
            authenticationToken = authenticationService.login(loginRequest);
        } catch (UsernameNotFoundException e) {
            LOGGER.warn("[login] warn: {}", String.valueOf(e));
            bindingResult.reject("loginFailed", "사용자를 찾을 수 없습니다.");
            return "login";
        } catch (Exception e) {
            LOGGER.warn("[signup] warn: {}", String.valueOf(e));
            bindingResult.reject("loginFailed", e.getMessage());
            return "login";
        }

        // Refresh 토큰과 Access 토큰을 쿠키에 저장
        Cookie refreshCookie = cookieUtil.createCookie("refresh_token", authenticationToken.getRefreshToken(), (int) refreshExpiration);
        Cookie accessCookie = cookieUtil.createCookie("access_token", authenticationToken.getAccessToken(), (int) jwtExpiration);

        // HttpServletResponse 에 쿠키 추가
        response.addCookie(refreshCookie);
        response.addCookie(accessCookie);

        LOGGER.info("[login] success, redirect home page");
        return "redirect:/"; // 성공 시 홈 페이지로 리다이렉트
    }

    @PostMapping("/refresh-token")
    public String refreshToken(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) throws IOException {
        LOGGER.info("[refreshToken]POST \"/api/v1/auth/refresh-token\", parameters={}", request.toString());
        authenticationService.processAccessTokenRefresh(request, response);
        // 필요한 경우, 모델 데이터 추가
        // model.addAttribute("key", "value");
        return "refreshTokenResult"; // Thymeleaf 템플릿 이름
    }
}
