package com.pj.oil.gasStation.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 현재 오피넷에 게시되고 있는 전국 주유소 평균 가격
 */
@Schema(description = "현재 오피넷에 게시되고 있는 전국 주유소 평균 가격")
@Getter
@Setter
@ToString
@Entity
public class AverageAllPrice {

    @Schema(description = "전국 주유소 평균가격 id")
    @Column(name = "average_all_price_id")
    @Id @GeneratedValue
    private Long id;
    @Schema(description = "해당일자")
    @Column(name = "trade_date")
    private String trade_date;
    @Schema(description = "제품구분")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productCode")
    private Product product;
    @Schema(description = "평균가격")
    @Column(name = "average_price")
    private String averagePrice;
    @Schema(description = "전일대비 등락값")
    @JsonProperty("price_change")
    private String priceChange;

}
