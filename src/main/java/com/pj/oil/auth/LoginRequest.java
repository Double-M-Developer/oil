package com.pj.oil.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class LoginRequest {
    @Email
    @NotEmpty(message = "이메일은 필수항목입니다.")
    private String email;
    @Size(min = 1, max = 30, message = "비밀번호 길이는 최소 8글자 입니다.")
    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password;

}
