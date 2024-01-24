package com.pj.oil.token;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "refreshToken", timeToLive = 7 * 24 * 60 * 60 * 1000) // 7일
public class RefreshToken implements Serializable {

    @Id
    private String token;
    @Indexed
    private String email;

    @Builder
    public RefreshToken(String token, String email) {
        this.token = token;
        this.email = email;
    }
}
