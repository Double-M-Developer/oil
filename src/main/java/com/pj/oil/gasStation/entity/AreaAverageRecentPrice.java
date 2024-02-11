package com.pj.oil.gasStation.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

/**
 * 일 평균가격 확정 수치이며, 전일부터 7일간의 전국 일일 지역별 평균가격
 */
@Schema(description = "일 평균가격 확정 수치이며, 전일부터 7일간의 전국 일일 지역별 평균가격")
@Getter @ToString
@Entity
public class AreaAverageRecentPrice {

        @Schema(description = "일 평균가격 id")
        @Column(name = "area_average_recent_price_id")
        @Id @GeneratedValue
        private Long id;
        @Schema(description = "기준일자")
        private String baseDate;
        @Schema(description = "주유소 지역 - 구분 미입력시 전국, 시도코드(2자리): 해당시도 기준, 시군코드(4자리): 해당시군 기준)")
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "areaCode")
        private Area area;
//        @Schema(description = "제품구분")
//        @ManyToOne(fetch = FetchType.LAZY)
//        @JoinColumn(name = "productCode")
//        private Product product;
        @Schema(description = "평균가격")
        private String averagePrice;

}
