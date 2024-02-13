//package com.pj.oil.gasStation.entity.maria;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import io.swagger.v3.oas.annotations.media.Schema;
//import jakarta.persistence.*;
//import lombok.*;
//
///**
// * 현재 오피넷에 게시되고 있는 시도별 주유소 평균 가격
// */
//@Schema(description = "현재 오피넷에 게시되고 있는 시도별 주유소 평균 가격")
//@Getter @Setter @ToString
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Entity
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class AverageSidoPrice {
//
//    @Schema(description = "전국 시도별 평균가격 id")
//    @Column(name = "average_sido_price_id")
//    @Id @GeneratedValue
//    private Long id;
//    @Schema(description = "주유소 지역 코드")
//    @Column(name = "area_code")
//    @JsonProperty("AREA_CD")
//    private String areaCode;
//    @Schema(description = "제품구분 코드")
//    @Column(name = "product_code")
//    @JsonProperty("PRODCD")
//    private String productCode;
//    @Schema(description = "평균가격")
//    private String averagePrice;
//    @Schema(description = "전일대비 등락값")
//    @JsonProperty("price_change")
//    private String priceChange;
//
//}