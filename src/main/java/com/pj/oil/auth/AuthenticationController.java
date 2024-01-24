package com.pj.oil.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody SignupRequest request) {
        LOGGER.info("[register]POST \"/api/v1/auth/signup\", parameters={}", request.toString());
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @GetMapping("/email")
    public ResponseEntity<Boolean> isEmailUnique(@RequestParam String email) {
        LOGGER.info("[isEmailUnique]GET \"/api/v1/auth/email\", parameters={}", email);
        return ResponseEntity.ok(authenticationService.isEmailUnique(email));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        LOGGER.info("[login]POST \"/api/v1/auth/login\", parameters={}", request.toString());
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request, // refresh token, Authorization Header 정보를 가져오거나 읽을 수 있음
            HttpServletResponse response // 사용자에게 응답을 다시 주입하거나 다시 보냄
    ) throws IOException {
        LOGGER.info("[refreshToken]POST \"/api/v1/auth/refresh-token\", parameters={}", request.toString());
        authenticationService.processAccessTokenRefresh(request, response);
    }

}

