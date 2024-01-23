package com.pj.oil.gasStation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Schema(description = "db에 저장된 지역 정보")
@Getter
@Entity @Table(name = "area")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Area {

    @Id
    @Schema(description = "지역코드")
    @Column(name = "area_cd")
    private String areaCd;
    @Schema(description = "지역이름")
    private String areaNm;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_cd")
    @Schema(description = "상위지역코드")
    private Area parentCd;

    @OneToMany(mappedBy = "parentCd", cascade = CascadeType.ALL)
    private List<Area> childCd;

    @OneToMany(mappedBy = "area")
    private List<GasStation> gasStations;

    @Override
    public String toString() {
        return "Area{" +
                "areaCd='" + areaCd + '\'' +
                ", areaNm='" + areaNm + '\'' +
                ", parentCd=" + (parentCd != null ? parentCd.getAreaCd() : null) +
                ", childCd=" + childCd +
                '}';
    }

}
