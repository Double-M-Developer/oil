package com.pj.oil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = RedisReactiveAutoConfiguration.class)
@EnableAsync // 비동기
public class OilApplication {

	public static void main(String[] args) {
		SpringApplication.run(OilApplication.class, args);
	}

}
