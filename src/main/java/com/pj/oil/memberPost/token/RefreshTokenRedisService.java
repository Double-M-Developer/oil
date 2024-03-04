package com.pj.oil.memberPost.token;

import com.pj.oil.memberPost.token.entity.redis.RefreshToken;
import com.pj.oil.memberPost.token.repository.redis.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenRedisService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByToken(String refreshToken) {
        return refreshTokenRepository.findById(refreshToken).orElse(null);
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteById(token);
    }

    public boolean isRefreshTokenPresent(String refreshToken) {
        return refreshTokenRepository.findById(refreshToken).isPresent();
    }

    public void save(RefreshToken token) {
        refreshTokenRepository.save(token);
    }
}
