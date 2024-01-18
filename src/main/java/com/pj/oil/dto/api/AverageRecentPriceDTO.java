package com.pj.oil.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Schema(description = "일 평균가격 확정 수치이며, 전일부터 이전 7일간의 전국 일일 평균가격")
@Getter @Setter
public class AverageRecentPriceDTO {
    @JsonProperty("RESULT")
    private AreaDto.Result result;

    @Getter @Setter
    public static class Result {
        @JsonProperty("OIL")
        private List<AreaDto.Oil> oil;
    }

    @Getter @Setter
    public static class Oil {
        @Schema(description = "기준일자")
        @JsonProperty("DATE")
        private String date;
        @Schema(description = "제품구분")
        @JsonProperty("PRODCD")
        private String prodcd;
        @Schema(description = "평균가격")
        @JsonProperty("PRICE")
        private String avgPrice;
    }
}
