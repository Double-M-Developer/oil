package com.pj.oil.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

@Schema(description = "주유소 가격 정보")
@Getter
@Entity @Table(name = "gas_station_price")
@ToString(exclude = {"gasStation"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class GasStationPrice {

    @Id @Column(name = "price_id")
    @GeneratedValue
    @Schema(description = "주유소 가격 id")
    private Long id;
    @Schema(description = "주유소")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="uni_id")
    private GasStation gasStation;
    @Schema(description = "주유소 제품 - 구분(휘발유:B027, 경유:D047, 고급휘발유: B034, 실내등유:C004, 자동차부탄: K015)")
    @Enumerated(EnumType.STRING)
    private ProductCode prodcd;
    @Schema(description = "판매가격")
    private Integer price;
    @Schema(description = "컬럼 정보가 갱신된 시간")
    private String updatedAt;


}
