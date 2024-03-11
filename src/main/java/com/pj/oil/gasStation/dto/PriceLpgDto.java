package com.pj.oil.gasStation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "lpg 가격")
@Getter @Setter @ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceLpgDto extends GasStationDtoBase {

    @Schema(description = "주유소 id")
    private String uniId;

    @Schema(description = "lpg 가격")
    private int lpg;
}
