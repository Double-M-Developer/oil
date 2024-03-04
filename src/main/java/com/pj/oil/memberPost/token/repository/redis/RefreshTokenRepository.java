package com.pj.oil.memberPost.token.repository.redis;

import com.pj.oil.memberPost.token.entity.redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByEmailAndToken(String email, String token);
}
