package com.pj.oil.gasStation.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
@Entity
public class GasStationOil {

    @Schema(description = "전국 주유소 id")
    @Id @Column(name = "uni_id")
    private String uniId;

    @Schema(description = "주유소 지역")
    @Column(name = "area")
    private String area;

    @Schema(description = "상호(중복X)")
    @Column(name = "os_name")
    private String osName;

    @Schema(description = "상표(SKE:SK에너지, GSC:GS칼텍스, HDO:현대오일뱅크, SOL:S-OIL, RTE:자영알뜰, RTX:고속도로 알뜰, NHO:농협알뜰, ETC:자가상표, E1G: E1, SKG:SK가스, RTO: 기타 )")
    @Column(name = "poll_div_name")
    private String pollDivName;

    @Schema(description = "도로명주소")
    @Column(name = "new_address")
    private String newAddress;

    @Schema(description = "업데이트 일자")
    @Column(name = "update_date")
    private String updateDate;

    @OneToMany(mappedBy = "gasStationOil")
    private List<PriceOil> priceOilList;
}




