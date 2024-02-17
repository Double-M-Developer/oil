package com.pj.oil.gasStation;

import com.pj.oil.gasStation.entity.maria.AreaAverageRecentPrice;
import com.pj.oil.gasStation.entity.maria.AverageAllPrice;
import com.pj.oil.gasStation.entity.maria.AverageRecentPrice;
import com.pj.oil.gasStation.entity.maria.LowTop20Price;
import com.pj.oil.gasStation.repository.jpa.*;
import com.pj.oil.util.CoordinateUtil;
import com.pj.oil.util.DateUtil;
import org.locationtech.proj4j.ProjCoordinate;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.geom.Area;
import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class GasStationService {

    private final AreaAverageRecentPriceRepository areaAverageRecentPriceRepository;
    private final AverageRecentPriceRepository averageRecentPriceRepository;
    private final AverageAllPriceRepository averageAllPriceRepository;

    private final GasStationRepository gasStationRepository;

    private final PriceLpgRepository priceLpgRepository;

    private final PriceOilRepository priceOilRepository;

    private final LowTop20PriceRepository lowTop20PriceRepository;

    private final CoordinateUtil coordinateUtil;
    private final DateUtil dateUtil;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    // 일 평균가격 확정 수치이며, 전일부터 7일간의 전국 일일 지역별 평균가격
    public List<AreaAverageRecentPrice> findAreaAverageRecentPriceByBaseDate(String baseDate) {
        List<AreaAverageRecentPrice> entity = areaAverageRecentPriceRepository.findByBaseDate(baseDate);
        if (entity.isEmpty()) {
            LOGGER.info("[findAreaAverageRecentPrice] AreaAverageRecentPrice data dose not exist");
        }
        LOGGER.info("[findAreaAverageRecentPrice] AreaAverageRecentPrice data dose exist, AreaAverageRecentPrice size: {}", entity.size());
        return entity;
    }

    // 현재 오피넷에 게시되고 있는 전국 주유소 평균 가격
    public List<AverageAllPrice> findAverageAllPriceByTradeDate(String tradeDate) {
        List<AverageAllPrice> entity = averageAllPriceRepository.findByTradeDate(tradeDate);
        if (entity.isEmpty()) {
            LOGGER.info("[findAverageAllPriceByTradeDate] AverageAllPrice data dose not exist");
        }
        LOGGER.info("[findAverageAllPriceByTradeDate] AverageAllPrice data dose exist, AverageAllPrice entity size: {}", entity.size());
        return entity;
    }


    // 전국 또는 지역별 최저가 주유소 TOP20
    public List<LowTop20Price> findLowTop20PriceByAreaCodeAndProductCode(String areaCode, String productCode) {
        LOGGER.info("[findLowTop20PriceByAreaCode] request area: {} product: {}", areaCode, productCode);

        List<LowTop20Price> entity = lowTop20PriceRepository.findLowTop20PriceByAreaCodeAndProductCode(areaCode, productCode);

        if (entity.isEmpty()) {
            LOGGER.info("[findLowTop20PriceByAreaCode] LowTop20Price data dose not exist");
        } else {
            entity.forEach(station -> {
                // 각 주유소의 좌표를 KATEC에서 WGS84로 변환
                ProjCoordinate transformedCoords = coordinateUtil.convertKATECToWGS84(station.getGisXCoor(), station.getGisYCoor());
                station.setGisXCoor(transformedCoords.x);
                station.setGisYCoor(transformedCoords.y);
            });
            LOGGER.info("[findLowTop20PriceByAreaCode] LowTop20Price data dose exist, LowTop20Price size: {}", entity.size());
        }
        return entity;
    }


    public void findRank() {
    }


    public Map<String, Object> avgDay() {
        LOGGER.info("[avgDay]");
        Map<String, Object> response = new HashMap<>();
        List<String> labels = IntStream.rangeClosed(0, 6)
                .mapToObj(i -> LocalDate.now().minusDays(i).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .sorted()
                .collect(Collectors.toList());

        // 연료 유형별 평균 가격을 저장할 리스트
        List<Double> avgPricesOil1 = new ArrayList<>(Collections.nCopies(labels.size(), null));
        List<Double> avgPricesOil2 = new ArrayList<>(Collections.nCopies(labels.size(), null));
        List<Double> avgPricesOil3 = new ArrayList<>(Collections.nCopies(labels.size(), null));
        List<Double> avgPricesOil4 = new ArrayList<>(Collections.nCopies(labels.size(), null));

        // 데이터베이스에서 모든 AverageAllPrice 객체를 불러옴
        List<AverageAllPrice> allPrices = averageAllPriceRepository.findAll();
        LOGGER.info("[avgDay] data: {}", allPrices);
        // allPrices 리스트를 순회하면서 각 연료 유형별 평균 가격을 해당 날짜에 맞게 삽입
        allPrices.forEach(item -> {
            String formattedDate = LocalDate.parse(item.getTradeDate(), DateTimeFormatter.ofPattern("yyyyMMdd"))
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int index = labels.indexOf(formattedDate);
            if (index != -1) { // 해당 날짜가 labels 리스트에 존재하는 경우

                Double priceAverage = item.getPriceAverage();
                if (priceAverage == null) {
                    priceAverage = 0.0; // 기본값 설정
                }
                switch (item.getProductCode()) {
                    case "B034":
                        avgPricesOil1.set(index, priceAverage);
                        break;
                    case "B027":
                        avgPricesOil2.set(index, priceAverage);
                        break;
                    case "D047":
                        avgPricesOil3.set(index, priceAverage);
                        break;
                    case "K015":
                        avgPricesOil4.set(index, priceAverage);
                        break;
                }
            }
        });

        // 각 연료 유형별 평균 가격 데이터 구조 생성
        List<Map<String, List<Double>>> prices = new ArrayList<>();
        prices.add(Collections.singletonMap("averagePrice", avgPricesOil1));
        prices.add(Collections.singletonMap("averagePrice", avgPricesOil2));
        prices.add(Collections.singletonMap("averagePrice", avgPricesOil3));
        prices.add(Collections.singletonMap("averagePrice", avgPricesOil4));

        // 최종 응답 구조 생성
        response.put("labels", labels);
        response.put("prices", prices);
        LOGGER.info("[avgDay] response: {}", response);
        return response;
    }


    public Map<String, List<String>> getTopBrandsByFuelType() {
        LOGGER.info("[getTopBrandsByFuelType]");
        Map<String, List<String>> rankData = new HashMap<>();
        List<String> data1 = gasStationRepository.findTopBrandsByPreGasolinePrice(PageRequest.of(0, 3));
        rankData.put("고급 휘발유", gasStationRepository.findTopBrandsByPreGasolinePrice(PageRequest.of(0, 3)));
        rankData.put("휘발유", gasStationRepository.findTopBrandsByGasolinePrice(PageRequest.of(0, 3)));
        rankData.put("경유", gasStationRepository.findTopBrandsByDieselPrice(PageRequest.of(0, 3)));
        rankData.put("LPG", gasStationRepository.findTopBrandsByLpgPrice(PageRequest.of(0, 3)));
        LOGGER.info("[getTopBrandsByFuelType] rankData: {}", rankData);
        return rankData;
    }


    public Map<String, Object> findAverageRecentPrice7Day() {
        LOGGER.info("[findAverageRecentPrice7Day]");
        Map<String, Object> response = new HashMap<>();
        List<String> labels = IntStream.rangeClosed(0, 6)
                .mapToObj(i -> LocalDate.now().minusDays(i).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .sorted()
                .collect(Collectors.toList());

        // 연료 유형별 평균 가격을 저장할 리스트
        List<Double> avgPricesOil1 = new ArrayList<>(Collections.nCopies(labels.size(), null));
        List<Double> avgPricesOil2 = new ArrayList<>(Collections.nCopies(labels.size(), null));
        List<Double> avgPricesOil3 = new ArrayList<>(Collections.nCopies(labels.size(), null));
        List<Double> avgPricesOil4 = new ArrayList<>(Collections.nCopies(labels.size(), null));

        String eightDaysBeforeDateString = dateUtil.getEightDaysBeforeDateString();
        String yesterdayDateString = dateUtil.getYesterdayDateString();
        // 데이터베이스에서 지난 7일간의 AverageRecentPrice 객체를 불러옴
        List<AverageRecentPrice> allPrices =  averageRecentPriceRepository.findByLastSevenDays(eightDaysBeforeDateString, yesterdayDateString);

        LOGGER.info("[findAverageRecentPrice7Day] data: {}", allPrices);
        // allPrices 리스트를 순회하면서 각 연료 유형별 평균 가격을 해당 날짜에 맞게 삽입
        allPrices.forEach(item -> {
            String formattedDate = LocalDate.parse(item.getDate(), DateTimeFormatter.ofPattern("yyyyMMdd"))
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int index = labels.indexOf(formattedDate);
            if (index != -1) { // 해당 날짜가 labels 리스트에 존재하는 경우

                Double priceAverage = item.getPriceAverage();
                if (priceAverage == null) {
                    priceAverage = 0.0; // 기본값 설정
                }
                switch (item.getProductCode()) {
                    case "B034":
                        avgPricesOil1.set(index, priceAverage);
                        break;
                    case "B027":
                        avgPricesOil2.set(index, priceAverage);
                        break;
                    case "D047":
                        avgPricesOil3.set(index, priceAverage);
                        break;
                    case "K015":
                        avgPricesOil4.set(index, priceAverage);
                        break;
                }
            }
        });

        // 각 연료 유형별 평균 가격 데이터 구조 생성
        List<Map<String, List<Double>>> prices = new ArrayList<>();
        prices.add(Collections.singletonMap("averagePrice", avgPricesOil1));
        prices.add(Collections.singletonMap("averagePrice", avgPricesOil2));
        prices.add(Collections.singletonMap("averagePrice", avgPricesOil3));
        prices.add(Collections.singletonMap("averagePrice", avgPricesOil4));

        // 최종 응답 구조 생성
        response.put("labels", labels);
        response.put("prices", prices);
        LOGGER.info("[findAverageRecentPrice7Day] response: {}", response);
        return response;
    }
}
