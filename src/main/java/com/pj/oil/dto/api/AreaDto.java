package com.pj.oil.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class AreaDto {

    @JsonProperty("RESULT")
    private Result result;

    @Getter @Setter
    public static class Result {
        @JsonProperty("OIL")
        private List<Oil> oil;
    }

    @Getter @Setter
    public static class Oil {
        @Schema(description = "지역코드")
        @JsonProperty("AREA_CD")
        private String areaCd;
        @Schema(description = "지역명")
        @JsonProperty("AREA_NM")
        private String areaNm;

    }
}
