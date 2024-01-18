package com.pj.oil.dto;

import com.pj.oil.entity.Area;
import com.pj.oil.entity.PollDivCode;
import com.pj.oil.entity.ProductCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@RequiredArgsConstructor
@Setter
@AllArgsConstructor
@ToString
public class GasStationPriceDto {
    @Schema(description = "주유소 코드")
    private String uniId;
    @Schema(description = "주유소 지역 - 구분 미입력시 전국, 시도코드(2자리):해당시도 기준, 시군코드(4자리):해당시군 기준)")
    private Area area;
    @Schema(description = "상표(SKE:SK에너지, GSC:GS칼텍스, HDO:현대오일뱅크, SOL:S-OIL, RTE:자영알뜰, RTX:고속도로 알뜰, NHO:농협알뜰, ETC:자가상표, E1G: E1, SKG:SK가스 )")
    private PollDivCode pollDivCd;
    @Schema(description = "상호(중복X)")
    private String osNm;
    @Schema(description = "지번주소")
    private String vanAdr;
    @Schema(description = "도로명주소")
    private String newAdr;
    @Schema(description = "GIS X좌표(KATEC)")
    private Double gisXCoor;
    @Schema(description = "GIS Y좌표(KATEC)")
    private Double gisYCoor;
    @Schema(description = "주유소 가격 id")
    private Long price_id;
    @Schema(description = "주유소 제품 - 구분(휘발유:B027, 경유:D047, 고급휘발유: B034, 실내등유:C004, 자동차부탄: K015)")
    private ProductCode prodcd;
    @Schema(description = "판매가격")
    private Integer price;
    @Schema(description = "컬럼 정보가 갱신된 시간")
    private String updatedAt;

}
