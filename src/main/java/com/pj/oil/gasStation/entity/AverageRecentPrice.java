package com.pj.oil.gasStation.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pj.oil.cache.gasStation.entity.AverageRecentPriceRedis;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

/**
 * 전날부터 최대 7일 전국 일일 평균가격
 */
@Schema(description = "전날부터 최대 7일 전국 일일 평균가격")
@Getter @Setter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(indexes = {
        @Index(name = "avg_recent_price_dt",columnList = "date"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class AverageRecentPrice extends GasStationBase {

    @Schema(description = "전국 주유소 평균가격 id")
    @Column(name = "average_recent_price_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "기준일자")
    @Column(name = "date")
    @JsonProperty("DATE")
    private String date;
    @Schema(description = "제품구분 코드")
    @Column(name = "product_code")
    @JsonProperty("PRODCD")
    private String productCode;
    @Schema(description = "평균가격")
    @Column(name = "price_average")
    @JsonProperty("PRICE")
    private double priceAverage;

    @Builder
    public AverageRecentPrice(Long id, String date, String productCode, double priceAverage) {
        this.id = id;
        this.date = date;
        this.productCode = productCode;
        this.priceAverage = priceAverage;
    }

    public static AverageRecentPrice transferRedisToEntity(AverageRecentPriceRedis redis) {
        return AverageRecentPrice.builder()
                .id(redis.getAverageRecentPriceId())
                .date(redis.getDate())
                .productCode(redis.getProductCode())
                .priceAverage(redis.getPriceAverage())
                .build();
    }
}
