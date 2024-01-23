package com.pj.oil.gasStation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;


@Schema(description = "주유소 정보")
@Getter
@Entity @Table(name = "gas_station")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@ToString(exclude = {"vanAdr", "newAdr"})
public class GasStation {

    @Id
    @Column(name = "uni_id")
    @Schema(description = "주유소 코드")
    private String uniId;

    @Schema(description = "주유소 지역 - 구분 미입력시 전국, 시도코드(2자리): 해당시도 기준, 시군코드(4자리): 해당시군 기준)")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_cd")
    private Area area;

    @Schema(description = "상표(SKE:SK에너지, GSC:GS칼텍스, HDO:현대오일뱅크, SOL:S-OIL, RTE:자영알뜰, RTX:고속도로 알뜰, NHO:농협알뜰, ETC:자가상표, E1G: E1, SKG:SK가스 )")
    @Enumerated(EnumType.STRING)
    private PollDivCode pollDivCd;

    @Schema(description = "상호(중복X)")
    private String osNm;

    @Schema(description = "지번주소")
    private String vanAdr;

    @Schema(description = "도로명주소")
    private String newAdr;

    @Schema(description = "GIS X좌표(KATEC)")
    @Column(name = "gis_x_coor")
    private Double gisXCoor;

    @Schema(description = "GIS Y좌표(KATEC)")
    @Column(name = "gis_y_coor")
    private Double gisYCoor;

    @OneToMany(mappedBy = "gasStation")
    private List<GasStationPrice> prices; // 가격

    public void setArea(Area area) {
        this.area = area;
    }

}
