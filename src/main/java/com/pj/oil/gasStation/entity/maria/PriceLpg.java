package com.pj.oil.gasStation.entity.maria;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class PriceLpg {

    @Schema(description = "Lpg")
    @Column(name = "price_lpg_id")
    @Id @GeneratedValue
    private Long id;

    @Schema(description = "주유쇼 id")
    @Column(name = "uni_id")
    private String uniId;

    @Schema(description = "주유쇼 id")
    @Column(name = "lpg")
    private int lpg;

}
