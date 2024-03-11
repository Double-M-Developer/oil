package com.pj.oil.cache.gasStation.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pj.oil.gasStation.dto.AverageAllPriceDto;
import com.pj.oil.gasStation.entity.AverageAllPrice;
import com.pj.oil.gasStation.entity.GasStationBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

/**
 * 현재 오피넷에 게시되고 있는 전국 주유소 평균 가격
 */
@Schema(description = "현재 오피넷에 게시되고 있는 전국 주유소 평균 가격")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@RedisHash(value = "lowTop20Price", timeToLive = 1 * 24 * 60 * 60 * 1000) // 1일
public class AverageAllPriceRedis extends GasStationBase {

    @Id
    private Long averageAllPriceId;
    @Indexed
    @Schema(description = "해당일자")
    @JsonProperty("TRADE_DT")
    private String tradeDate;
    @Schema(description = "제품구분 코드")
    @JsonProperty("PRODCD")
    private String productCode;
    @Schema(description = "평균가격")
    @JsonProperty("PRICE")
    private double priceAverage;
    @Schema(description = "전일대비 등락값")
    @JsonProperty("DIFF")
    private double priceChange;
    public static AverageAllPriceRedis transferDataToRedis(AverageAllPriceDto dbEntity) {
        return new AverageAllPriceRedis(
                dbEntity.getId(),
                dbEntity.getTradeDate(),
                dbEntity.getProductCode(),
                dbEntity.getPriceAverage(),
                dbEntity.getPriceChange()
        );
    }
}
