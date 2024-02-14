package com.pj.oil.gasStation.entity.maria;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class GasStation {

    @Schema(description = "전국 주유소 id")
    @Column(name = "uni_id")
    @Id @GeneratedValue
    private String id;

    @Schema(description = "해당일자")
    @Column(name = "area_code")
    private String area;

    @Schema(description = "상호(중복X)")
    @Column(name = "os_name")
    @JsonProperty("OS_NM")
    private String osName;

    @Schema(description = "상표(SKE:SK에너지, GSC:GS칼텍스, HDO:현대오일뱅크, SOL:S-OIL, RTE:자영알뜰, RTX:고속도로 알뜰, NHO:농협알뜰, ETC:자가상표, E1G: E1, SKG:SK가스, RTO: 기타 )")
    @Column(name = "poll_div_code")
    @JsonProperty("POLL_DIV_CD")
    private String pollDivCode;

    @Schema(description = "도로명주소")
    @Column(name = "new_address")
    @JsonProperty("NEW_ADR")
    private String newAddress;

}




