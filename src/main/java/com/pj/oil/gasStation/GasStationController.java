package com.pj.oil.gasStation;

import com.pj.oil.gasStation.entity.maria.AreaAverageRecentPrice;
import com.pj.oil.gasStation.entity.maria.AverageAllPrice;
import com.pj.oil.gasStation.entity.maria.AverageSidoPrice;
import com.pj.oil.gasStation.entity.maria.LowTop20Price;
import lombok.RequiredArgsConstructor;
import com.pj.oil.gasStation.GasStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gas-station")
public class GasStationController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final GasStationService gasStationService;


    /**
     * 일 평균가격 확정 수치이며, 전일부터 7일간의 전국 일일 지역별 평균가격
     * @param baseDate
     * @return
     */
    @GetMapping("/area-average-recent-price")
    public List<AreaAverageRecentPrice> findAreaAverageRecentPriceByBaseDate(String baseDate) {
        LOGGER.info("[findAreaAverageRecentPriceByBaseDate]GET \"/api/v1/gas-station//area-average-recent-price\", parameters={}", baseDate);
        List<AreaAverageRecentPrice> entity = gasStationService.findAreaAverageRecentPriceByBaseDate(baseDate);
        if (entity.isEmpty()) {
            LOGGER.info("[findAreaAverageRecentPriceByBaseDate] AreaAverageRecentPrice data dose not existed");
        }
        LOGGER.info("[findAreaAverageRecentPriceByBaseDate] AreaAverageRecentPrice data dose existed, AreaAverageRecentPrice size: {}", entity.size());
        return entity;
    }

    /**
     * 현재 오피넷에 게시되고 있는 전국 주유소 평균 가격
     * @param tradeDate
     * @return
     */
    @GetMapping("/area-average-all-price")
    public List<AverageAllPrice> findAverageAllPriceByTradeDate(String tradeDate) {
        List<AverageAllPrice> entity = gasStationService.findAverageAllPriceByTradeDate(tradeDate);
        if (entity.isEmpty()) {
            LOGGER.info("[findAverageAllPriceByTradeDate] AverageAllPrice data dose not existed");
        }
        LOGGER.info("[findAverageAllPriceByTradeDate] AverageAllPrice data dose existed, AverageAllPrice entity size: {}", entity.size());
        return entity;
    }

    /**
     * 현재 오피넷에 게시되고 있는 시도별 주유소 평균 가격
     * @param areaCode
     * @return
     */
    public List<AverageSidoPrice> findAverageSidoPriceByAreaCode(String areaCode) {
        List<AverageSidoPrice> entity = gasStationService.findAverageSidoPriceByAreaCode(areaCode);
        if (entity.isEmpty()) {
            LOGGER.info("[findAverageSidoPriceByAreaCode] AverageSidoPrice data dose not existed");
        }
        LOGGER.info("[findAverageSidoPriceByAreaCode] AverageSidoPrice data dose existed, AverageSidoPrice size: {}", entity.size());
        return entity;
    }

    /**
     * 전국 또는 지역별 최저가 주유소 TOP20
     * @param areaCode
     * @return
     */
    public List<LowTop20Price> findLowTop20PriceByAreaCodeAndProductCode(String areaCode, String productCode) {
        List<LowTop20Price> entity = gasStationService.findLowTop20PriceByAreaCodeAndProductCode(areaCode, productCode);
        if (entity.isEmpty()) {
            LOGGER.info("[findLowTop20PriceByAreaCodeAndProductCode] LowTop20Price data dose not existed");
        }
        LOGGER.info("[findLowTop20PriceByAreaCodeAndProductCode] LowTop20Price data dose existed, LowTop20Price size: {}", entity.size());
        return entity;
    }

    @GetMapping("/avg")
    public void avgDay(){
        gasStationService.avgDay();
    }

    public void sendGasStationRank(){
        gasStationService.findRank();
    }


}
