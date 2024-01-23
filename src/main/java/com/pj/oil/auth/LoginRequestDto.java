package com.pj.oil.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class LoginRequestDto {

    private String userId;
    private String password;

    @Builder
    public LoginRequestDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
