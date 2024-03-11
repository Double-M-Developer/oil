package com.pj.oil.gasStation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pj.oil.cache.gasStation.entity.AverageAllPriceRedis;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

/**
 * 현재 오피넷에 게시되고 있는 전국 주유소 평균 가격
 */
@Schema(description = "현재 오피넷에 게시되고 있는 전국 주유소 평균 가격")
@Getter @Setter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AverageAllPriceDto extends GasStationDtoBase {
    @Schema(description = "전국 주유소 평균가격 id")
    private Long id;
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

    @Builder
    public AverageAllPriceDto(Long id, String tradeDate, String productCode, double priceAverage, double priceChange) {
        this.id = id;
        this.tradeDate = tradeDate;
        this.productCode = productCode;
        this.priceAverage = priceAverage;
        this.priceChange = priceChange;
    }

    public static AverageAllPriceDto transferRedisToEntity(AverageAllPriceRedis redis) {
        return AverageAllPriceDto.builder()
                .tradeDate(redis.getTradeDate())
                .productCode(redis.getProductCode())
                .priceAverage(redis.getPriceAverage())
                .priceChange(redis.getPriceChange())
                .build();
    }
}
