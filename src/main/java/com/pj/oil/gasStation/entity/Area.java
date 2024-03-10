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
public class Area {

    @Schema(description = "주유소 지역코드")
    @Id @Column(name = "area_code")
    private String AreaCode;

    @Schema(description = "주유소 지역명")
    @Column(name = "area_name")
    private String AreaName;

    @OneToMany(mappedBy = "area")
    private List<AreaAverageRecentPrice> areaAverageRecentPriceList;

    @OneToMany(mappedBy = "area")
    private List<LowTop20Price> lowTop20PricesList;
}
