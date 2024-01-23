package com.pj.oil.gasStationApi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Schema(description = "현재 오피넷에 게시되고 있는 시/군/구별 주유소 평균 가격")
@Getter @Setter @ToString
public class AverageSigunPriceDto extends ApiBaseDto {
    @JsonProperty("RESULT")
    private Result result;

    @Getter @Setter @ToString
    public static class Result {
        @JsonProperty("OIL")
        private List<Oil> oil;
    }
    @Getter @Setter @ToString
    public static class Oil{
        @Schema(description = "시군구 구분코드")
        @JsonProperty("SIGUNCD")
        private String areaCd;
        @Schema(description = "시군구명")
        @JsonProperty("SIGUNNM")
        private String areaNm;
        @Schema(description = "제품구분")
        @JsonProperty("PRODCD")
        private String prodcd;
        @Schema(description = "평균가격")
        @JsonProperty("PRICE")
        private String avgPrice;
        @Schema(description = "전일대비 등락값")
        @JsonProperty("DIFF")
        private String diff;
    }
}