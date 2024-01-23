package com.pj.oil.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    NORMAL("NORMAL", "일반"),
    DORMANCY("DORMANCY", "휴면"),
    DELETE("DELETE", "삭제");

    private final String key;
    private final String title;
}
