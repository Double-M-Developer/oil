package com.pj.oil.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Schema(description = "db에 저장된 사용자 정보")
@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Member {

    @Schema(description = "사용자 id")
    @Id @GeneratedValue
    private Long id;
    @Schema(description = "사용자 로그인 id")
    private String userId;
    @Schema(description = "사용자 로그인 pw")
    private String password;
    @Schema(description = "사용자 이름")
    private String username;
    @Schema(description = "사용자 닉네임")
    private String nickname;
    @Schema(description = "사용자 이메일")
    private String email;
    @Schema(description = "사용자 권한")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Schema(description = "사용자 가입일")
    private LocalDate issueDate;
    @Schema(description = "사용자 계정 상태 - 일반, 삭제, 휴면")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Builder
    protected Member(Long id, String userId, String password, String username, String nickname, String email, Role role, LocalDate issueDate, UserStatus userStatus) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.issueDate = issueDate;
        this.userStatus = userStatus;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
