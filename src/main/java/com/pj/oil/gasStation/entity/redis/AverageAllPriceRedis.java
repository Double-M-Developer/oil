package com.pj.oil.gasStation.entity.redis;

import com.pj.oil.gasStation.entity.maria.AverageAllPrice;
import com.pj.oil.gasStation.entity.maria.GasStationBase;
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
    private String tradeDate;
    @Schema(description = "제품구분 코드")
    private String productCode;
    @Schema(description = "평균가격")
    private double priceAverage;
    @Schema(description = "전일대비 등락값")
    private double priceChange;
    public AverageAllPriceRedis transferDataToRedis(AverageAllPrice dbEntity) {
        return new AverageAllPriceRedis(
                dbEntity.getId(),
                dbEntity.getTradeDate(),
                dbEntity.getProductCode(),
                dbEntity.getPriceAverage(),
                dbEntity.getPriceChange()
        );
    }
}
