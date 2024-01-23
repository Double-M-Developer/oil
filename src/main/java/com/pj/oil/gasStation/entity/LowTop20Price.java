package com.pj.oil.gasStation.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pj.oil.gasStationApi.dto.ApiBaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 전국 또는 지역별 최저가 주유소 TOP20
 */
@Schema(description = "전국 또는 지역별 최저가 주유소 TOP20")
@Getter
@Setter
@ToString
@Entity
public class LowTop20Price extends ApiBaseDto {

    @Schema(description = "전국 또는 지역별 최저가 주유소 TOP20 id")
    @Column(name = "low_top_20_price_id")
    @Id @GeneratedValue
    private Long id;
    @Schema(description = "주유소 코드")
    private String uniId;
    @Schema(description = "판매가격")
    @Column(name = "price")
    private String price;
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
    /**
     * 반환되는 값이 없으므로, 호출시 사용한 매개변수를 사용하여 값을 지정해줘야 함
     */
    @Schema(description = "제품구분")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product")
    private Product product;
    /**
     * 반환되는 값이 없으므로, 호출시 사용한 매개변수를 사용하여 값을 지정해줘야 함
     */
    @Schema(description = "주유소 지역 - 구분 미입력시 전국, 시도코드(2자리): 해당시도 기준, 시군코드(4자리): 해당시군 기준)")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_code")
    private Area area;
}
