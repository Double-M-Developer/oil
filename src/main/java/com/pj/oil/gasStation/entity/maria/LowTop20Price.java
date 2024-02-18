package com.pj.oil.gasStation.entity.maria;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pj.oil.gasStation.entity.redis.LowTop20PriceRedis;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

/**
 * 전국 또는 지역별 최저가 주유소 TOP20
 */
@Schema(description = "전국 또는 지역별 최저가 주유소 TOP20")
@Getter @Setter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(indexes = {
        @Index(name = "low_top_price",columnList = "price_current"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class LowTop20Price extends GasStationBase {

    @Schema(description = "전국 또는 지역별 최저가 주유소 TOP20 id")
    @Column(name = "low_top_20_price_id")
    @Id @GeneratedValue
    private Long id;
    @Schema(description = "주유소 코드")
    @Column(name = "uni_id")
    @JsonProperty("UNI_ID")
    private String uniId;
    @Schema(description = "판매가격")
    @Column(name = "price_current")
    @JsonProperty("PRICE")
    private int priceCurrent;
    @Schema(description = "상표(SKE:SK에너지, GSC:GS칼텍스, HDO:현대오일뱅크, SOL:S-OIL, RTE:자영알뜰, RTX:고속도로 알뜰, NHO:농협알뜰, ETC:자가상표, E1G: E1, SKG:SK가스, RTO: 기타 )")
    @Column(name = "poll_div_code")
    @JsonProperty("POLL_DIV_CD")
    private String pollDivCode;
    @Schema(description = "상호(중복X)")
    @Column(name = "os_name")
    @JsonProperty("OS_NM")
    private String osName;
    @Schema(description = "지번주소")
    @Column(name = "van_address")
    @JsonProperty("VAN_ADR")
    private String vanAddress;
    @Schema(description = "도로명주소")
    @Column(name = "new_address")
    @JsonProperty("NEW_ADR")
    private String newAddress;
    @Schema(description = "GIS X좌표(KATEC)")
    @Column(name = "gis_x_coor")
    @JsonProperty("GIS_X_COOR")
    private Double gisXCoor;
    @Schema(description = "GIS Y좌표(KATEC)")
    @Column(name = "gis_y_coor")
    @JsonProperty("GIS_Y_COOR")
    private Double gisYCoor;
    /**
     * 반환되는 값이 없으므로, 호출시 사용한 매개변수를 사용하여 값을 지정해줘야 함
     */
    @Schema(description = "제품 구분 코드")
    @Column(name = "product_code")
    private String productCode;
    /**
     * 반환되는 값이 없으므로, 호출시 사용한 매개변수를 사용하여 값을 지정해줘야 함
     */
    @Schema(description = "주유소 지역 코드")
    @Column(name = "area_code")
    private String areaCode;

    @Builder
    public LowTop20Price(Long id, String uniId, int priceCurrent, String pollDivCode, String osName, String vanAddress, String newAddress, Double gisXCoor, Double gisYCoor, String productCode, String areaCode) {
        this.id = id;
        this.uniId = uniId;
        this.priceCurrent = priceCurrent;
        this.pollDivCode = pollDivCode;
        this.osName = osName;
        this.vanAddress = vanAddress;
        this.newAddress = newAddress;
        this.gisXCoor = gisXCoor;
        this.gisYCoor = gisYCoor;
        this.productCode = productCode;
        this.areaCode = areaCode;
    }

    public static LowTop20Price transferRedisToEntity(LowTop20PriceRedis redis) {
        return LowTop20Price.builder()
                .uniId(redis.getUniId())
                .priceCurrent(redis.getPriceCurrent())
                .pollDivCode(redis.getPollDivCode())
                .osName(redis.getOsName())
                .vanAddress(redis.getVanAddress())
                .newAddress(redis.getNewAddress())
                .gisXCoor(redis.getGisXCoor())
                .gisYCoor(redis.getGisYCoor())
                .productCode(redis.getProductCode())
                .areaCode(redis.getAreaCode())
                .build();
    }

}
