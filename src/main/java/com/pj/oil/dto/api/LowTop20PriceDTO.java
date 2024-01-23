package com.pj.oil.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Schema(description = "전국 또는 지역별 최저가 주유소 TOP20")
@Getter @Setter @ToString
public class LowTop20PriceDto extends ApiBaseDto {
    @JsonProperty("RESULT")
    private Result result;

    @Getter @Setter @ToString
    public static class Result {
        @JsonProperty("OIL")
        private List<Oil> oil;
    }
    @Getter @Setter @ToString
    public static class Oil{
        @Schema(description = "주유소코드")
        @JsonProperty("UNI_ID")
        private String uniId;
        @Schema(description = "판매가격")
        @JsonProperty("PRICE")
        private String price;
        @Schema(description = "상표(SKE:SK에너지, GSC:GS칼텍스, HDO:현대오일뱅크, SOL:S-OIL, RTE:자영알뜰, RTX:고속도로알뜰, NHO:농협알뜰, ETC:자가상표, E1G: E1, SKG:SK가스 )")
        @JsonProperty("POLL_DIV_CD")
        private String pollDivCd;
        @Schema(description = "상호")
        @JsonProperty("OS_NM")
        private String osNm;
        @Schema(description = "지번주소")
        @JsonProperty("VAN_ADR")
        private String vanAdr;
        @Schema(description = "도로명주소")
        @JsonProperty("NEW_ADR")
        private String newAdr;
        @Schema(description = "GIS X좌표(KATEC)")
        @JsonProperty("GIS_X_COOR")
        private String gisXCoor;
        @Schema(description = "GIS Y좌표(KATEC)")
        @JsonProperty("GIS_Y_COOR")
        private String gisyCoor;
        /**
         * 반환되는 값이 없으므로, 호출시 사용한 매개변수를 사용하여 값을 지정해줘야 함
         */
        @Schema(description = "제품구분코드")
        private String prodcd;
    }
}
