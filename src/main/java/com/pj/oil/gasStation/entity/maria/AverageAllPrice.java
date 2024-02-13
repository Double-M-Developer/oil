package com.pj.oil.gasStation.entity.maria;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class AverageAllPrice extends GasStationBase {

    @Schema(description = "전국 주유소 평균가격 id")
    @Column(name = "average_all_price_id")
    @Id @GeneratedValue
    private Long id;
    @Schema(description = "해당일자")
    @Column(name = "trade_date")
    @JsonProperty("TRADE_DT")
    private String tradeDate;
    @Schema(description = "제품구분 코드")
    @Column(name = "product_code")
    @JsonProperty("PRODCD")
    private String productCode;
    @Schema(description = "평균가격")
    @Column(name = "average_price")
    @JsonProperty("PRICE")
    private String priceAverage;
    @Schema(description = "전일대비 등락값")
    @Column(name = "price_change")
    @JsonProperty("DIFF")
    private String priceChange;

    @Builder
    public AverageAllPrice(Long id, String tradeDate, String productCode, String priceAverage, String priceChange) {
        this.id = id;
        this.tradeDate = tradeDate;
        this.productCode = productCode;
        this.priceAverage = priceAverage;
        this.priceChange = priceChange;
    }
}
