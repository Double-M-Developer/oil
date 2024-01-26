package com.pj.oil.gasStation.entity.redis;

import com.pj.oil.gasStation.entity.maria.Area;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "area", timeToLive = 7 * 24 * 60 * 60 * 1000) // 7일
public class AreaRedis implements Serializable{

    @Id
    private String areaCode;
    private String areaName;

    @Builder
    public AreaRedis(String areaCode, String areaName) {
        this.areaCode = areaCode;
        this.areaName = areaName;
    }
}
