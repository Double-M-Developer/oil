package com.pj.oil.gasStation.entity.redis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pj.oil.gasStation.entity.maria.AreaAverageRecentPrice;
import com.pj.oil.gasStation.entity.maria.GasStationBase;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.annotation.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

/**
 * 일 평균가격 확정 수치이며, 전일부터 7일간의 전국 일일 지역별 평균가격
 */
@Schema(description = "일 평균가격 확정 수치이며, 전일부터 7일간의 전국 일일 지역별 평균가격")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@RedisHash(value = "areaAverageRecentPriceRedis", timeToLive = 1 * 24 * 60 * 60 * 1000) // 1일
public class AreaAverageRecentPriceRedis extends GasStationBase {

    @Id
    private Long areaAverageRecentPriceId;
    @Indexed
    @Schema(description = "기준일자")
    @JsonProperty("DATE")
    private String baseDate;
    @Indexed
    @Schema(description = "주유소 지역 코드")
    @JsonProperty("AREA_CD")
    private String areaCode;
    @Indexed
    @Schema(description = "제품구분 코드")
    @JsonProperty("PRODCD")
    private String productCode;
    @Schema(description = "평균가격")
    @JsonProperty("PRICE")
    private double priceAverage;

    public static AreaAverageRecentPriceRedis transferDataToRedis(AreaAverageRecentPrice dbEntity) {
        return new AreaAverageRecentPriceRedis(
                dbEntity.getId(),
                dbEntity.getBaseDate(),
                dbEntity.getAreaCode(),
                dbEntity.getProductCode(),
                dbEntity.getPriceAverage()
        );
    }
}
