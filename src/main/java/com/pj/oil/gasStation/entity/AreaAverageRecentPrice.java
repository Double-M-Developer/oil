package com.pj.oil.gasStation.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pj.oil.cache.gasStation.entity.AreaAverageRecentPriceRedis;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

/**
 * 일 평균가격 확정 수치이며, 전일부터 7일간의 전국 일일 지역별 평균가격
 */
@Schema(description = "일 평균가격 확정 수치이며, 전일부터 7일간의 전국 일일 지역별 평균가격")
@Getter @Setter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(indexes = {
        @Index(name = "area_avg_rec_price_bd",columnList = "base_date"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class AreaAverageRecentPrice extends GasStationBase {

        @Schema(description = "일 평균가격 id")
        @Column(name = "area_average_recent_price_id")
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Schema(description = "기준일자")
        @Column(name = "base_date")
        @JsonProperty("DATE")
        private String baseDate;
        @Schema(description = "주유소 지역 코드")
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "area_code")
        @JsonProperty("AREA_CD")
        private Area areaCode;
        @Schema(description = "제품구분 코드")
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "product_code")
        @JsonProperty("PRODCD")
        private Product productCode;
        @Schema(description = "평균가격")
        @Column(name = "price_average")
        @JsonProperty("PRICE")
        private double priceAverage;

        @Builder
        public AreaAverageRecentPrice(Long id, String baseDate, Area areaCode, Product productCode, double priceAverage) {
                this.id = id;
                this.baseDate = baseDate;
                this.areaCode = areaCode;
                this.productCode = productCode;
                this.priceAverage = priceAverage;
        }
}
