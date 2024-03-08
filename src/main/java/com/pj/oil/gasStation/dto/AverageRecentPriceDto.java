package com.pj.oil.gasStation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pj.oil.cache.gasStation.entity.AverageRecentPriceRedis;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 전날부터 최대 7일 전국 일일 평균가격
 */
@Schema(description = "전날부터 최대 7일 전국 일일 평균가격")
@Getter @Setter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AverageRecentPriceDto extends GasStationDtoBase {

    @Schema(description = "기준일자")
    @JsonProperty("DATE")
    private String date;
    @Schema(description = "제품구분 코드")
    @JsonProperty("PRODCD")
    private String productCode;
    @Schema(description = "평균가격")
    @JsonProperty("PRICE")
    private double priceAverage;

    @Builder
    public AverageRecentPriceDto(String date, String productCode, double priceAverage) {
        this.date = date;
        this.productCode = productCode;
        this.priceAverage = priceAverage;
    }

    public static AverageRecentPriceDto transferRedisToEntity(AverageRecentPriceRedis redis) {
        return AverageRecentPriceDto.builder()
                .date(redis.getDate())
                .productCode(redis.getProductCode())
                .priceAverage(redis.getPriceAverage())
                .build();
    }
}
