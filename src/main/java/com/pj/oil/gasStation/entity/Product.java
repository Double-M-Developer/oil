package com.pj.oil.gasStation.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "db에 저장된 지역 정보")
@Getter
@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @Schema(description = "제품코드")
    @Enumerated(EnumType.STRING)
    private ProductCode productCode;

    @OneToMany(mappedBy = "product")
    private List<AreaAverageRecentPrice> areaAverageRecentPrices;
    @OneToMany(mappedBy = "product")
    private List<AverageAllPrice> averageAllPrices;
    @OneToMany(mappedBy = "product")
    private List<AverageSidoPrice> averageSidoPrices;
    @OneToMany(mappedBy = "product")
    private List<LowTop20Price> lowTop20Prices;

}
