package com.pj.oil.gasStation.entity.redis;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "product", timeToLive = 7 * 24 * 60 * 60 * 1000) // 7일
public class ProductRedis implements Serializable {

    @Id
    private String productCode;
    private String productName; // 제품 이름 추가

    public ProductRedis(String productCode, String productName) {
        this.productCode = productCode;
        this.productName = productName;
    }
}