package com.pj.oil.gasStation;

import com.pj.oil.gasStation.entity.*;
import com.pj.oil.gasStation.repository.*;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class GasStationService {

    private final AreaAverageRecentPriceRepository areaAverageRecentPriceRepository;
    private final AverageAllPriceRepository averageAllPriceRepository;
    private final AverageSidoPriceRepository averageSidoPriceRepository;
    private final LowTop20PriceRepository lowTop20PriceRepository;

    private final AreaRepository areaRepository;

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
//    // 전국 또는 지역별 최저가 주유소 TOP20
//    public List<LowTop20Price> findLowTop20PriceByAreaCodeAndProductCode(String areaCode, String productCode) {
//
//        Optional<Area> area = areaRepository.findById(areaCode);
//        Optional<Product> product = productRepository.findById(productCode);
//
//        List<LowTop20Price> entity = lowTop20PriceRepository.findLowTop20PriceByAreaAndProduct(area, product);
//
//        if (entity.isEmpty()) {
//            LOGGER.info("[findLowTop20PriceByAreaCode] LowTop20Price data dose not existed");
//        }
//        LOGGER.info("[findLowTop20PriceByAreaCode] LowTop20Price data dose existed, LowTop20Price size: {}", entity.size());
//        return entity;
//    }


    public void findRank() {
    }


    public Map<String, Object> avgDay() {
        Map<String, Object> response = new HashMap<>();
        List<String> labels = IntStream.rangeClosed(0, 6)
                .mapToObj(i -> LocalDate.now().minusDays(i).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .sorted()
                .collect(Collectors.toList());

        // 연료 유형별 평균 가격을 저장할 리스트
        List<Double> avgPricesOil1 = new ArrayList<>(Collections.nCopies(labels.size(), null));
        List<Double> avgPricesOil2 = new ArrayList<>(Collections.nCopies(labels.size(), null));
        List<Double> avgPricesOil3 = new ArrayList<>(Collections.nCopies(labels.size(), null));

        // 데이터베이스에서 모든 AverageAllPrice 객체를 불러옴
        List<AverageAllPrice> allPrices = averageAllPriceRepository.findAll();

        // allPrices 리스트를 순회하면서 각 연료 유형별 평균 가격을 해당 날짜에 맞게 삽입
        allPrices.forEach(item -> {
            String formattedDate = LocalDate.parse(item.getTradeDate(), DateTimeFormatter.ofPattern("yyyyMMdd"))
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int index = labels.indexOf(formattedDate);
            if (index != -1) { // 해당 날짜가 labels 리스트에 존재하는 경우
                double averagePrice = item.getAveragePrice();
                switch (item.getProductCode()) {
                    case "oil1":
                        avgPricesOil1.set(index, averagePrice);
                        break;
                    case "oil2":
                        avgPricesOil2.set(index, averagePrice);
                        break;
                    case "oil3":
                        avgPricesOil3.set(index, averagePrice);
                        break;
                }
            }
        });

        // 각 연료 유형별 평균 가격 데이터 구조 생성
        List<Map<String, List<Double>>> prices = new ArrayList<>();
        prices.add(Collections.singletonMap("averagePrice", avgPricesOil1));
        prices.add(Collections.singletonMap("averagePrice", avgPricesOil2));
        prices.add(Collections.singletonMap("averagePrice", avgPricesOil3));

        // 최종 응답 구조 생성
        response.put("labels", labels);
        response.put("prices", prices);

        return response;
    }


    // 객체 저장
    private AverageAllPrice saveAveragePrice(String productCode, double averagePrice, String tradeDate) {
        AverageAllPrice averageAllPrice = new AverageAllPrice();

        return averageAllPrice; // 저장된 객체 반환
    }
}
