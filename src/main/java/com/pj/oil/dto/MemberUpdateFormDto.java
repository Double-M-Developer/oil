package com.pj.oil.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class MemberUpdateFormDto {
    private String password;
    private String nickname;

    @Builder
    public MemberUpdateFormDto(String password, String nickname) {
        this.password = password;
        this.nickname = nickname;
    }
}