package com.pj.oil.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String userId;
    private String password;
    private String username;
    private String nickname;
    private String email;
    private Role role;
    private LocalDate issueDate;
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
