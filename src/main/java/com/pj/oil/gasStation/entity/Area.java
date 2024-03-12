package com.pj.oil.gasStation.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
public class Area {

    @Schema(description = "주유소 지역코드")
    @Id @Column(name = "area_code")
    private String AreaCode;

    @Schema(description = "주유소 지역명")
    @Column(name = "area_name")
    private String AreaName;
}
