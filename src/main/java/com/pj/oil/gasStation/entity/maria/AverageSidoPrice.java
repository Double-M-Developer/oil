package com.pj.oil.gasStation.entity.maria;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 현재 오피넷에 게시되고 있는 시도별 주유소 평균 가격
 */
@Schema(description = "현재 오피넷에 게시되고 있는 시도별 주유소 평균 가격")
@Getter
@Setter
@ToString
@Entity
public class AverageSidoPrice {

    @Schema(description = "전국 시도별 평균가격 id")
    @Column(name = "average_sido_price_id")
    @Id @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "areaCode")
    private Area area;
    @Schema(description = "제품구분")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productCode")
    private Product product;
    @Schema(description = "평균가격")
    private String averagePrice;
    @Schema(description = "전일대비 등락값")
    @JsonProperty("price_change")
    private String priceChange;

}