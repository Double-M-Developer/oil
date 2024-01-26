package com.pj.oil.gasStationApi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "일 평균가격 확정 수치이며, 전일부터 7일간의 전국 일일 지역별 평균가격")
@Getter @Setter @ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AreaAverageRecentPriceDto extends ApiBaseDto {

    @Schema(description = "기준일자")
    @JsonProperty("DATE")
    private String baseDate;
    @Schema(description = "지역코드")
    @JsonProperty("AREA_CD")
    private String areaCode;
    @Schema(description = "제품구분")
    @JsonProperty("PRODCD")
    private String productCode;
    @Schema(description = "평균가격")
    @JsonProperty("PRICE")
    private String averagePrice;

}
