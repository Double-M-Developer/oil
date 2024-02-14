package com.pj.oil.gasStation.entity.maria;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Schema(description = "lpg 가격")
@Getter @Setter @ToString
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceLpg extends GasStationBase {

    @Schema(description = "lpg 가격 id")
    @Column(name = "price_lpg_id")
    @Id @GeneratedValue
    private Long id;

    @Schema(description = "주유소 id")
    @Column(name = "uni_id")
    private String uniId;

    @Schema(description = "lpg 가격")
    @Column(name = "lpg")
    private int lpg;
}
