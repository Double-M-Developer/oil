package com.pj.oil.gasStation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pj.oil.cache.gasStation.entity.AreaAverageRecentPriceRedis;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

/**
 * 일 평균가격 확정 수치이며, 전일부터 7일간의 전국 일일 지역별 평균가격
 */
@Schema(description = "일 평균가격 확정 수치이며, 전일부터 7일간의 전국 일일 지역별 평균가격")
@Getter @Setter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AreaAverageRecentPriceDto extends GasStationDtoBase {

        @Schema(description = "일 평균가격 id")
        private Long id;
        @Schema(description = "기준일자")
        @JsonProperty("DATE")
        private String baseDate;
        @Schema(description = "주유소 지역 코드")
        @JsonProperty("AREA_CD")
        private String areaCode;
        @Schema(description = "제품구분 코드")
        @JsonProperty("PRODCD")
        private String productCode;
        @Schema(description = "평균가격")
        @JsonProperty("PRICE")
        private double priceAverage;

        @Builder
        public AreaAverageRecentPriceDto(Long id, String baseDate, String areaCode, String productCode, double priceAverage) {
                this.id = id;
                this.baseDate = baseDate;
                this.areaCode = areaCode;
                this.productCode = productCode;
                this.priceAverage = priceAverage;
        }

        public static AreaAverageRecentPriceDto transferRedisToEntity(AreaAverageRecentPriceRedis redis) {
                return AreaAverageRecentPriceDto.builder()
                        .baseDate(redis.getBaseDate())
                        .areaCode(redis.getAreaCode())
                        .productCode(redis.getProductCode())
                        .priceAverage(redis.getPriceAverage())
                        .build();
        }
}
