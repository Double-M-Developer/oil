package com.pj.oil.gasStation;

import com.pj.oil.gasStation.entity.AreaAverageRecentPrice;
import com.pj.oil.gasStation.entity.AverageAllPrice;
import com.pj.oil.gasStation.entity.AverageSidoPrice;
import com.pj.oil.gasStation.entity.LowTop20Price;
import com.pj.oil.gasStation.repository.AreaAverageRecentPriceRepository;
import com.pj.oil.gasStation.repository.AverageAllPriceRepository;
import com.pj.oil.gasStation.repository.AverageSidoPriceRepository;
import com.pj.oil.gasStation.repository.LowTop20PriceRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GasStationService {

//    private final AreaAverageRecentPriceRepository areaAverageRecentPriceRepository;
//    private final AverageAllPriceRepository averageAllPriceRepository;
//    private final AverageSidoPriceRepository averageSidoPriceRepository;
//    private final LowTop20PriceRepository lowTop20PriceRepository;
//
//    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
//
//    // 일 평균가격 확정 수치이며, 전일부터 7일간의 전국 일일 지역별 평균가격
//    public List<AreaAverageRecentPrice> findAreaAverageRecentPriceByBaseDate(String baseDate) {
//        List<AreaAverageRecentPrice> entity = areaAverageRecentPriceRepository.findByBaseDate(baseDate);
//        if (entity.isEmpty()) {
//            LOGGER.info("[findAreaAverageRecentPrice] AreaAverageRecentPrice data dose not existed");
//        }
//        LOGGER.info("[findAreaAverageRecentPrice] AreaAverageRecentPrice data dose existed, AreaAverageRecentPrice size: {}", entity.size());
//        return entity;
//    }
//    // 현재 오피넷에 게시되고 있는 전국 주유소 평균 가격
//    public Optional<AverageAllPrice> findAverageAllPriceByTradeDate(String tradeDate) {
//        Optional<AverageAllPrice> entity = averageAllPriceRepository.findByTradeDate(tradeDate);
//        if (entity.isEmpty()) {
//            LOGGER.info("[findAverageAllPriceByTradeDate] AverageAllPrice data dose not existed");
//        }
//        LOGGER.info("[findAverageAllPriceByTradeDate] AverageAllPrice data dose existed, AverageAllPrice entity: {}", entity);
//        return entity;
//    }
//
//    // 현재 오피넷에 게시되고 있는 시도별 주유소 평균 가격
//    public List<AverageSidoPrice> findAverageSidoPriceByAreaCode(String areaCode) {
//        List<AverageSidoPrice> entity = averageSidoPriceRepository.findByArea(areaCode);
//        if (entity.isEmpty()) {
//            LOGGER.info("[findAverageSidoPriceByAreaCode] AverageSidoPrice data dose not existed");
//        }
//        LOGGER.info("[findAverageSidoPriceByAreaCode] AverageSidoPrice data dose existed, AverageSidoPrice size: {}", entity.size());
//        return entity;
//    }
//    // 전국 또는 지역별 최저가 주유소 TOP20
//    public List<LowTop20Price> findLowTop20PriceByAreaCode(String areaCode) {
//        List<LowTop20Price> entity = lowTop20PriceRepository.findByAreaCode(areaCode);
//        if (entity.isEmpty()) {
//            LOGGER.info("[findLowTop20PriceByAreaCode] LowTop20Price data dose not existed");
//        }
//        LOGGER.info("[findLowTop20PriceByAreaCode] LowTop20Price data dose existed, LowTop20Price size: {}", entity.size());
//        return entity;
//    }
}