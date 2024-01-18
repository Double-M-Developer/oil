package com.pj.oil.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Schema(description = "일 평균가격 확정 수치이며, 전일부터 7일간의 전국 일일 지역별 평균가격")
@Getter
@Setter
public class AreaAverageRecentPriceDTO {
    @JsonProperty("RESULT")
    private AreaDto.Result result;

    @Getter
    @Setter
    public static class Result {
        @JsonProperty("OIL")
        private List<AreaDto.Oil> oil;
    }

    @Getter
    @Setter
    public static class Oil {
        @Schema(description = "기준일자")
        @JsonProperty("DATE")
        private String date;
        @Schema(description = "지역코드")
        @JsonProperty("AREA_CD")
        private String areaCd;
        @Schema(description = "지역명")
        @JsonProperty("AREA_NM")
        private String areaNm;
        @Schema(description = "제품구분")
        @JsonProperty("PRODCD")
        private String prodcd;
        @Schema(description = "평균가격")
        @JsonProperty("PRICE")
        private String avgPrice;
    }
}
