package com.pj.oil.gasStation.entity.maria;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Schema(description = "고급 휘발유, 휘발유, 경유 가격")
@Getter @Setter @ToString
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceOil extends GasStationBase {

    @Schema(description = "고급 휘발유, 휘발유, 경유 id")
    @Column(name = "price_oil_id")
    @Id @GeneratedValue
    private Long id;

    @Schema(description = "주유소 id")
    @Column(name = "uni_id")
    private String uniId;

    // 고급 휘발유
    @Schema(description = "고급 휘발유 가격")
    @Column(name = "pre_gasoline")
    private int preGasoline;

    // 휘발유
    @Schema(description = "휘발유 가격")
    @Column(name = "gasoline")
    private int gasoline;

    // 경유
    @Schema(description = "경유 가격")
    @Column(name = "diesel")
    private int diesel;
}
