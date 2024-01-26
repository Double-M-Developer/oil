package com.pj.oil.gasStation.entity.maria;

import com.pj.oil.gasStation.entity.redis.AreaRedis;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * db에 저장된 지역 정보
 */
@Schema(description = "db에 저장된 지역 정보")
@Getter
@Entity @Table(name = "area")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Area {

    @Id
    @Schema(description = "지역코드")
    private String areaCode;
    @Schema(description = "지역이름")
    private String areaName;

    @OneToMany(mappedBy = "area")
    private List<AreaAverageRecentPrice> areaAverageRecentPrices;
    @OneToMany(mappedBy = "area")
    private List<AverageSidoPrice> averageSidoPrices;
    @OneToMany(mappedBy = "area")
    private List<LowTop20Price> lowTop20Prices;

    @Override
    public String toString() {
        return "Area{" +
                "areaCode='" + areaCode + '\'' +
                ", areaNm='" + areaName + '\'' +
                '}';
    }

    public Area(String areaCode, String areaName) {
        this.areaCode = areaCode;
        this.areaName = areaName;
    }
}
