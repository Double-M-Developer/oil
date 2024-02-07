package com.pj.oil.auth;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class SignupRequest {
    @Email
    @NotEmpty(message = "이메일은 필수항목입니다.")
    private String email;
    @Size(min = 2, max = 30, message = "사용자 닉네임 길이는 최소 2글자 입니다.")
    @NotEmpty(message = "사용자 닉네임은 필수항목입니다.")
    private String name;
    @Size(min = 1, max = 30, message = "비밀번호 길이는 최소 8글자 입니다.")
    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password;

}
