package com.pj.oil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.Arrays;

@SpringBootApplication
@SpringBootApplication(exclude = RedisReactiveAutoConfiguration.class)
@EnableAsync // 비동기
@EnableRedisRepositories(enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP)
public class OilApplication {

	public static void main(String[] args) {
		SpringApplication.run(OilApplication.class, args);
	}

}
