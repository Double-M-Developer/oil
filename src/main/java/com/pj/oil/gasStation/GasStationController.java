package com.pj.oil.gasStation;

import com.pj.oil.gasStation.entity.maria.*;
import com.pj.oil.gasStation.entity.redis.AreaAverageRecentPriceRedis;
import com.pj.oil.gasStation.entity.redis.AverageAllPriceRedis;
import com.pj.oil.gasStation.entity.redis.AverageRecentPriceRedis;
import com.pj.oil.gasStation.entity.redis.LowTop20PriceRedis;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gas-station")
public class GasStationController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final GasStationService gasStationService;

    /**
     * 일 평균가격 확정 수치이며, 전일부터 7일간의 전국 일일 지역별 평균가격
     *
     * @return
     */
    @GetMapping("/area-average-recent-price")
    public List<AreaAverageRecentPriceRedis> findAreaAverageRecentPriceSevenDays(
            @RequestParam("areaCode") String areaCode,
            @RequestParam("productCode") String productCode
    ) {
        LOGGER.info("[findAreaAverageRecentPriceSevenDays]GET \"/gas-station/area-average-recent-price\", areaCode: {}, productCode: {}", areaCode, productCode);
        return gasStationService.findAreaAverageRecentPriceSevenDays(areaCode, productCode);
    }

    /**
     * 전날부터 최대 7일 전국 일일 평균가격
     *
     * @return
     */
    @GetMapping("/average-recent-price")
    public List<AverageRecentPriceRedis> findAverageRecentPriceSevenDays(
            @RequestParam("productCode") String productCode
    ) {
        LOGGER.info("[findAverageRecentPriceSevenDays]GET \"/gas-station/average-recent-price\", productCode: {}", productCode);
        return gasStationService.findAverageRecentPriceSevenDays(productCode);
    }

    /**
     * 현재 오피넷에 게시되고 있는 전국 주유소 평균 가격
     *
     * @return
     */
    @GetMapping("/area-average-all-price")
    public List<AverageAllPriceRedis> findAverageAllPriceByTradeDate() {
        LOGGER.info("[findAverageAllPriceByTradeDate]GET \"/gas-station/area-average-all-price\"");
        return gasStationService.findAverageAllPriceByTradeDate();
    }

    /**
     * 전국 또는 지역별 최저가 주유소 TOP20
     *
     * @param
     * @return
     */
    @GetMapping("/low-top20")
    public List<LowTop20PriceRedis> findLowTop20PriceByAreaCodeAndProductCode(
            @RequestParam("areaCode") String areaCode,
            @RequestParam("productCode") String productCode
    ) {
        LOGGER.info("[findLowTop20PriceByAreaCodeAndProductCode]GET \"/gas-station/low-top20\"");
        return gasStationService.findLowTop20PriceByAreaCodeAndProductCode(areaCode, productCode);
    }

}
