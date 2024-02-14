package com.pj.oil.gasStation.entity.maria;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceOil {

    @Schema(description = "고급 휘발유, 휘발유, 경유 id")
    @Column(name = "price_oil_id")
    @Id @GeneratedValue
    private Long id;

    @Schema(description = "주유쇼 id")
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
