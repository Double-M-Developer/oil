package com.pj.oil.gasStation;

import com.pj.oil.gasStation.entity.maria.*;
import com.pj.oil.gasStation.entity.redis.AreaAverageRecentPriceRedis;
import com.pj.oil.gasStation.entity.redis.AverageAllPriceRedis;
import com.pj.oil.gasStation.entity.redis.AverageRecentPriceRedis;
import com.pj.oil.gasStation.entity.redis.LowTop20PriceRedis;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gas-station")
public class GasStationController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final GasStationService gasStationService;

    private final MeterRegistry meterRegistry;


    private void recordMetric(String uri, String method, String areaCode, String productCode, long start) {
        List<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("method", method));
        if (areaCode != null) tags.add(Tag.of("areaCode", areaCode));
        if (productCode != null) tags.add(Tag.of("productCode", productCode));

        meterRegistry.timer(uri, tags)
                .record(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
    }

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
        long start = System.currentTimeMillis();
        List<AreaAverageRecentPriceRedis> response = gasStationService.findAreaAverageRecentPriceSevenDays(areaCode, productCode);
        recordMetric("/gas-station/area-average-recent-price", "GET", areaCode, productCode, start);
        return response;
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
        long start = System.currentTimeMillis();
        List<AverageRecentPriceRedis> response = gasStationService.findAverageRecentPriceSevenDays(productCode);
        recordMetric("/gas-station/average-recent-price", "GET", null, productCode, start);
        return response;
    }

    /**
     * 현재 오피넷에 게시되고 있는 전국 주유소 평균 가격
     *
     * @return
     */
    @GetMapping("/area-average-all-price")
    public List<AverageAllPriceRedis> findAverageAllPriceByTradeDate() {
        LOGGER.info("[findAverageAllPriceByTradeDate]GET \"/gas-station/area-average-all-price\"");
        long start = System.currentTimeMillis();
        List<AverageAllPriceRedis> response = gasStationService.findAverageAllPriceByTradeDate();
        recordMetric("/gas-station/area-average-all-price", "GET", null, null, start);
        return response;
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
        long start = System.currentTimeMillis();
        List<LowTop20PriceRedis> response = gasStationService.findLowTop20PriceByAreaCodeAndProductCode(areaCode, productCode);
        recordMetric("/gas-station/low-top20", "GET", areaCode, productCode, start);
        return response;
    }

}
