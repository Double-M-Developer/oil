package com.pj.oil.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AuthenticationRequest {
    private final String email;
    private final String password;

    @Builder
    public AuthenticationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
