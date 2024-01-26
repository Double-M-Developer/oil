package com.pj.oil.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(
        basePackages = "com.pj.oil.token",
        redisTemplateRef = "RefreshTokenRedisTemplate"
)
public class RefreshTokenRedisConfig {

    @Value("${spring.refresh-token.redis.host}")
    private String redisHost;
    @Value("${spring.refresh-token.redis.port}")
    private int redisPort;

    @Bean
    public LettuceConnectionFactory refreshTokenConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
        return new LettuceConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Object> refreshTokenRedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(refreshTokenConnectionFactory());
        return template;
    }
}
