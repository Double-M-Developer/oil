package com.pj.oil.gasStation.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
@Entity
public class Product {
    @Schema(description = "주유소 제품코드")
    @Id @Column(name = "product_code")
    private String productCode;

    @Schema(description = "주유소 제품명")
    @Column(name = "product_name")
    private String ProductName;

    @OneToMany(mappedBy = "product")
    private List<AreaAverageRecentPrice> areaAverageRecentPriceList;

    @OneToMany(mappedBy = "product")
    private List<AverageAllPrice> averageAllPriceList;

    @OneToMany(mappedBy = "product")
    private List<AverageRecentPrice> averageRecentPriceList;

    @OneToMany(mappedBy = "product")
    private List<LowTop20Price> lowTop20PricesList;
}
