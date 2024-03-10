package com.pj.oil.gasStation.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pj.oil.cache.gasStation.entity.AverageAllPriceRedis;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

/**
 * 현재 오피넷에 게시되고 있는 전국 주유소 평균 가격
 */
@Schema(description = "현재 오피넷에 게시되고 있는 전국 주유소 평균 가격")
@Getter @Setter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(indexes = {
        @Index(name = "avg_all_price_td",columnList = "trade_date"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class AverageAllPrice extends GasStationBase {

    @Schema(description = "전국 주유소 평균가격 id")
    @Column(name = "average_all_price_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "해당일자")
    @Column(name = "trade_date")
    @JsonProperty("TRADE_DT")
    private String tradeDate;
    @Schema(description = "제품구분 코드")
    @ManyToOne
    @JoinColumn(name = "product_code")
    @JsonProperty("PRODCD")
    private Product productCode;
    @Schema(description = "평균가격")
    @Column(name = "price_average")
    @JsonProperty("PRICE")
    private double priceAverage;
    @Schema(description = "전일대비 등락값")
    @Column(name = "price_change")
    @JsonProperty("DIFF")
    private double priceChange;

    @Builder
    public AverageAllPrice(Long id, String tradeDate, Product productCode, double priceAverage, double priceChange) {
        this.id = id;
        this.tradeDate = tradeDate;
        this.productCode = productCode;
        this.priceAverage = priceAverage;
        this.priceChange = priceChange;
    }
}
