package com.pj.oil.gasStation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "고급 휘발유, 휘발유, 경유 가격")
@Getter @Setter @ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceOilDto extends GasStationDtoBase {

    @Schema(description = "주유소 id")
    private String uniId;

    // 고급 휘발유
    @Schema(description = "고급 휘발유 가격")
    private int preGasoline;

    // 휘발유
    @Schema(description = "휘발유 가격")
    private int gasoline;

    // 경유
    @Schema(description = "경유 가격")
    private int diesel;
}
