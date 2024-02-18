package com.pj.oil.gasStation.entity.redis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pj.oil.gasStation.entity.maria.AverageRecentPrice;
import com.pj.oil.gasStation.entity.maria.GasStationBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

/**
 * 전날부터 최대 7일 전국 일일 평균가격
 */
@Schema(description = "전날부터 최대 7일 전국 일일 평균가격")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@RedisHash(value = "averageRecentPriceRedis", timeToLive = 1 * 24 * 60 * 60 * 1000) // 1일
public class AverageRecentPriceRedis extends GasStationBase {

    @Id
    private Long averageRecentPriceId;
    @Indexed
    @Schema(description = "기준일자")
    @JsonProperty("DATE")
    private String date;
    @Indexed
    @Schema(description = "제품구분 코드")
    @JsonProperty("PRODCD")
    private String productCode;
    @Schema(description = "평균가격")
    @JsonProperty("PRICE")
    private double priceAverage;

    public static AverageRecentPriceRedis transferDataToRedis(AverageRecentPrice dbEntity) {
        return new AverageRecentPriceRedis(
                dbEntity.getId(),
                dbEntity.getDate(),
                dbEntity.getProductCode(),
                dbEntity.getPriceAverage()
        );
    }
}
