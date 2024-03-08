package com.pj.oil.cache.refreshToken;

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
