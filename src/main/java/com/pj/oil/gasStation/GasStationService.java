package com.pj.oil.gasStation;

import com.pj.oil.gasStation.entity.maria.*;
import com.pj.oil.gasStation.repository.jpa.*;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GasStationService {

    private final AreaAverageRecentPriceRepository areaAverageRecentPriceRepository;
    private final AverageAllPriceRepository averageAllPriceRepository;
    private final AverageSidoPriceRepository averageSidoPriceRepository;
    private final LowTop20PriceRepository lowTop20PriceRepository;

    private final AreaRepository areaRepository;
    private final ProductRepository productRepository;

    private final GasStationRepository gasStationRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    // 일 평균가격 확정 수치이며, 전일부터 7일간의 전국 일일 지역별 평균가격
    public List<AreaAverageRecentPrice> findAreaAverageRecentPriceByBaseDate(String baseDate) {
        List<AreaAverageRecentPrice> entity = areaAverageRecentPriceRepository.findByBaseDate(baseDate);
        if (entity.isEmpty()) {
            LOGGER.info("[findAreaAverageRecentPrice] AreaAverageRecentPrice data dose not existed");
        }
        LOGGER.info("[findAreaAverageRecentPrice] AreaAverageRecentPrice data dose existed, AreaAverageRecentPrice size: {}", entity.size());
        return entity;
    }
    // 현재 오피넷에 게시되고 있는 전국 주유소 평균 가격
    public List<AverageAllPrice> findAverageAllPriceByTradeDate(String tradeDate) {
        List<AverageAllPrice> entity = averageAllPriceRepository.findByTradeDate(tradeDate);
        if (entity.isEmpty()) {
            LOGGER.info("[findAverageAllPriceByTradeDate] AverageAllPrice data dose not existed");
        }
        LOGGER.info("[findAverageAllPriceByTradeDate] AverageAllPrice data dose existed, AverageAllPrice entity size: {}", entity.size());
        return entity;
    }

    // 현재 오피넷에 게시되고 있는 시도별 주유소 평균 가격
    public List<AverageSidoPrice> findAverageSidoPriceByAreaCode(String areaCode) {

        Optional<Area> area = areaRepository.findById(areaCode);

        List<AverageSidoPrice> entity = averageSidoPriceRepository.findByArea(area);
        if (entity.isEmpty()) {
            LOGGER.info("[findAverageSidoPriceByAreaCode] AverageSidoPrice data dose not existed");
        }
        LOGGER.info("[findAverageSidoPriceByAreaCode] AverageSidoPrice data dose existed, AverageSidoPrice size: {}", entity.size());
        return entity;
    }
    // 전국 또는 지역별 최저가 주유소 TOP20
    public List<LowTop20Price> findLowTop20PriceByAreaCodeAndProductCode(String areaCode, String productCode) {

        Optional<Area> area = areaRepository.findById(areaCode);
        Optional<Product> product = productRepository.findById(productCode);

        List<LowTop20Price> entity = lowTop20PriceRepository.findLowTop20PriceByAreaAndProduct(area, product);

        if (entity.isEmpty()) {
            LOGGER.info("[findLowTop20PriceByAreaCode] LowTop20Price data dose not existed");
        }
        LOGGER.info("[findLowTop20PriceByAreaCode] LowTop20Price data dose existed, LowTop20Price size: {}", entity.size());
        return entity;
    }


    public void findRank() {
    }

    public void avgDay(){
        System.out.println("여기까진 왔엉");
        List<GasStation> all = gasStationRepository.findAll();
    }
}
