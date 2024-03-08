package com.pj.oil.cache.gasStation.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pj.oil.gasStation.entity.GasStationBase;
import com.pj.oil.gasStation.entity.LowTop20Price;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

/**
 * 전국 또는 지역별 최저가 주유소 TOP20
 */
@Schema(description = "전국 또는 지역별 최저가 주유소 TOP20")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@RedisHash(value = "lowTop20Price", timeToLive = 1 * 24 * 60 * 60 * 1000) // 1일
public class LowTop20PriceRedis extends GasStationBase {

    @Id
    @Schema(description = "주유소 코드")
    @JsonProperty("UNI_ID")
    private String uniId;
    @Schema(description = "판매가격")
    @JsonProperty("PRICE")
    private int priceCurrent;
    @Schema(description = "상표(SKE:SK에너지, GSC:GS칼텍스, HDO:현대오일뱅크, SOL:S-OIL, RTE:자영알뜰, RTX:고속도로 알뜰, NHO:농협알뜰, ETC:자가상표, E1G: E1, SKG:SK가스, RTO: 기타 )")
    @JsonProperty("POLL_DIV_CD")
    private String pollDivCode;
    @Schema(description = "상호(중복X)")
    @JsonProperty("OS_NM")
    private String osName;
    @Schema(description = "지번주소")
    @JsonProperty("VAN_ADR")
    private String vanAddress;
    @Schema(description = "도로명주소")
    @JsonProperty("NEW_ADR")
    private String newAddress;
    @Schema(description = "GIS X좌표(KATEC)")
    @JsonProperty("GIS_X_COOR")
    private Double gisXCoor;
    @Schema(description = "GIS Y좌표(KATEC)")
    @JsonProperty("GIS_Y_COOR")
    private Double gisYCoor;
    @Indexed
    @Schema(description = "제품 구분 코드")
    private String productCode;
    @Indexed
    @Schema(description = "주유소 지역 코드")
    private String areaCode;

    public static LowTop20PriceRedis transferDataToRedis(LowTop20Price dbEntity) {
        return new LowTop20PriceRedis(
                dbEntity.getUniId(),
                dbEntity.getPriceCurrent(),
                dbEntity.getPollDivCode(),
                dbEntity.getOsName(),
                dbEntity.getVanAddress(),
                dbEntity.getNewAddress(),
                dbEntity.getGisXCoor(),
                dbEntity.getGisYCoor(),
                dbEntity.getProductCode(),
                dbEntity.getAreaCode()
        );
    }
}
