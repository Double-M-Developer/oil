package com.pj.oil.token;

import com.pj.oil.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Token {

    @Id @GeneratedValue
    private Long id;
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    @Setter
    private boolean expired; // db 만료 관리 (로그아웃)
    @Setter
    private boolean revoked; // db 취소 관리 (로그아웃)

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Token(Long id, String token, TokenType tokenType, boolean expired, boolean revoked, Member member) {
        this.id = id;
        this.token = token;
        this.tokenType = tokenType;
        this.expired = expired;
        this.revoked = revoked;
        this.member = member;
    }
}
