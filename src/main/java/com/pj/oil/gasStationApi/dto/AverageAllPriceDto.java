package com.pj.oil.gasStationApi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Schema(description = "현재 오피넷에 게시되고 있는 전국 주유소 평균 가격")
@Getter @Setter @ToString
public class AverageAllPriceDto extends ApiBaseDto {

    @JsonProperty("RESULT")
    private Result result;

    @Getter @Setter @ToString
    public static class Result {
        @JsonProperty("OIL")
        private List<Oil> oil;
    }

    @Getter @Setter @ToString
    @AllArgsConstructor
    public static class Oil {
        @Schema(description = "해당일자")
        @JsonProperty("TRADE_DT")
        private String tradeDt;
        @Schema(description = "제품구분코드")
        @JsonProperty("PRODCD")
        private String prodcd;
        @Schema(description = "제품명")
        @JsonProperty("PRODNM")
        private String prodNm;
        @Schema(description = "평균가격")
        @JsonProperty("PRICE")
        private String avgPrice;
        @Schema(description = "전일대비 등락값")
        @JsonProperty("DIFF")
        private String diff;
    }
}
