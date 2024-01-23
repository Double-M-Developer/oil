package com.pj.oil.gasStationApi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Schema(description = "최근 1주의 주간 평균유가(전국/시도별)")
@Getter @Setter @ToString
public class AreaAverageLastWeekPriceDto extends ApiBaseDto {

    @JsonProperty("RESULT")
    private Result result;

    @Getter @Setter @ToString
    public static class Result {
        @JsonProperty("OIL")
        private List<Oil> oil;
    }
    @Getter @Setter @ToString
    public static class Oil {
        @Schema(description = "기준 주 (EX. 6월 3주 : 2016063)")
        @JsonPropertyOrder("WEEK")
        private String week;
        @Schema(description = "기준 주의 시작 일자")
        @JsonProperty("STA_DT")
        private String staDt;
        @Schema(description = "기준 주의 종료 일자")
        @JsonProperty("END_DT")
        private String endDt;
        @Schema(description = "지역코드")
        @JsonProperty("AREA_CD")
        private String areaCd;
        @Schema(description = "제품구분")
        @JsonProperty("PRODCD")
        private String prodcd;
        @Schema(description = "평균가격")
        @JsonProperty("PRICE")
        private String avgPrice;
    }
}