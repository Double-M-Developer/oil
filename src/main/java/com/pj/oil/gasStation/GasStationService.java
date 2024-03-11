package com.pj.oil.gasStation;

import com.pj.oil.gasStation.dto.AreaAverageRecentPriceDto;
import com.pj.oil.gasStation.dto.AverageAllPriceDto;
import com.pj.oil.gasStation.dto.AverageRecentPriceDto;
import com.pj.oil.gasStation.dto.LowTop20PriceDto;
import com.pj.oil.gasStation.entity.*;
import com.pj.oil.cache.gasStation.entity.AreaAverageRecentPriceRedis;
import com.pj.oil.cache.gasStation.entity.AverageAllPriceRedis;
import com.pj.oil.cache.gasStation.entity.AverageRecentPriceRedis;
import com.pj.oil.cache.gasStation.entity.LowTop20PriceRedis;
import com.pj.oil.gasStation.repository.AreaAverageRecentPriceRepository;
import com.pj.oil.gasStation.repository.AverageAllPriceRepository;
import com.pj.oil.gasStation.repository.AverageRecentPriceRepository;
import com.pj.oil.gasStation.repository.LowTop20PriceRepository;
import com.pj.oil.cache.gasStation.repository.AreaAverageRecentPriceRedisRepository;
import com.pj.oil.cache.gasStation.repository.AverageAllPriceRedisRepository;
import com.pj.oil.cache.gasStation.repository.AverageRecentPriceRedisRepository;
import com.pj.oil.cache.gasStation.repository.LowTop20PriceRedisRepository;
import com.pj.oil.util.CoordinateUtil;
import com.pj.oil.util.DateUtil;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import org.locationtech.proj4j.ProjCoordinate;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class GasStationService {

    private final AreaAverageRecentPriceRepository areaAverageRecentPriceRepository;
    private final AverageRecentPriceRepository averageRecentPriceRepository;
    private final AverageAllPriceRepository averageAllPriceRepository;
    private final LowTop20PriceRepository lowTop20PriceRepository;

    private final AreaAverageRecentPriceRedisRepository areaAverageRecentPriceRedisRepository;
    private final AverageAllPriceRedisRepository averageAllPriceRedisRepository;
    private final AverageRecentPriceRedisRepository averageRecentPriceRedisRepository;
    private final LowTop20PriceRedisRepository lowTop20PriceRedisRepository;

    private final CoordinateUtil coordinateUtil;
    private final AreaRegistry areaRegistry;
    private final ProductRegistry productRegistry;
    private final PollDivRegistry pollDivRegistry;

    private final MeterRegistry meterRegistry;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private <T extends GasStationBase> List<T> processRedisData(List<T> redisData) {
        if (!redisData.isEmpty()) {
            LOGGER.info("cache data does exist");
            return redisData;
        }
        LOGGER.info("cache data does not exist");
        return null;
    }
    private String getPollDivNameByCode(String code) {
        return pollDivRegistry.getPollDivName(code);
    }

    private String getAreaNameByCode(String code) {
        return areaRegistry.getAreaName(code);
    }

    private String getProductNameByCode(String code) {
        return productRegistry.getProductName(code);
    }

    private void transformStationCoordinates(LowTop20PriceDto station) {
        ProjCoordinate transformedCoords = coordinateUtil.convertKATECToWGS84(station.getGisXCoor(), station.getGisYCoor());
        station.setGisXCoor(transformedCoords.x);
        station.setGisYCoor(transformedCoords.y);
    }

    private void recordMetric(String redisOrMaria, long start) {
        List<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("type", redisOrMaria));
        meterRegistry.timer("custom_requests_seconds", tags)
                .record(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
    }

    public List<LowTop20PriceRedis> findLowTop20PriceByAreaCodeAndProductCode(String areaCode, String productCode) {
        LOGGER.info("[findLowTop20PriceByAreaCodeAndProductCode] request area: {} product: {}", areaCode, productCode);
        long start = System.currentTimeMillis();
        List<LowTop20PriceRedis> redisPrice = lowTop20PriceRedisRepository.findByAreaCodeAndProductCode(areaCode, productCode);
        List<LowTop20PriceRedis> result = processRedisData(redisPrice);
        if (result != null) {
            recordMetric("redis", start);
            return result;
        }

        List<LowTop20PriceDto> dbPrice = lowTop20PriceRepository.findLowTop20PriceByAreaCodeAndProductCode(areaCode, productCode);
        if (!dbPrice.isEmpty()) {
            LOGGER.info("[findLowTop20PriceByAreaCodeAndProductCode] db data does exist");
            result = dbPrice.stream()
                    .peek(station -> {
                        station.setPollDivCode(getPollDivNameByCode(station.getPollDivCode()));
                        transformStationCoordinates(station);
                    })
                    .map(LowTop20PriceRedis::transferDataToRedis)
                    .toList();
            LOGGER.info("data size: {}", result.size());
            recordMetric("maria", start);
            LOGGER.info("data -> redis");
            lowTop20PriceRedisRepository.saveAll(result);

            return result;
        }
        LOGGER.info("[findLowTop20PriceByAreaCodeAndProductCode] db data does not exist");

        LOGGER.info("data does not exist");
        return null;
    }

    // 현재 오피넷에 게시되고 있는 전국 주유소 평균 가격
    public List<AverageAllPriceRedis> findAverageAllPriceByTradeDate() {
        LOGGER.info("[findAverageAllPriceByTradeDate]");
        long start = System.currentTimeMillis();
        String todayDateString = DateUtil.getTodayDateString();
        List<AverageAllPriceRedis> redisPrice = averageAllPriceRedisRepository.findByTradeDate(DateUtil.formatDateString(todayDateString));
        List<AverageAllPriceRedis> result = processRedisData(redisPrice);
        if (result != null) {
            recordMetric("redis", start);
            return result;
        }

        List<AverageAllPriceDto> dbPrice = averageAllPriceRepository.findByTradeDate(todayDateString);
        if (!dbPrice.isEmpty()) { // maria 존재
            LOGGER.info("[findAverageAllPriceByTradeDate] db data does exist");
            result = dbPrice.stream()
                    .peek(station -> {
                        station.setProductCode(getProductNameByCode(station.getProductCode()));
                        station.setTradeDate(DateUtil.formatDateString(station.getTradeDate()));
                    })
                    .map(AverageAllPriceRedis::transferDataToRedis)
                    .toList();
            LOGGER.info("data size: {}", result.size());
            recordMetric("maria", start);
            LOGGER.info("data -> redis");
            averageAllPriceRedisRepository.saveAll(result);

            return result;
        }
        LOGGER.info("[findAverageAllPriceByTradeDate] db data does not exist");

        LOGGER.info("data does not exist");
        return null;
    }

    // 일 평균가격 확정 수치이며, 전일부터 7일간의 전국 일일 지역별 평균가격
    public List<AreaAverageRecentPriceRedis> findAreaAverageRecentPriceSevenDays(String areaCode, String productCode) {
        LOGGER.info("[findAreaAverageRecentPriceSevenDays]");
        long start = System.currentTimeMillis();
        String sevenDaysBeforeDateString = DateUtil.getSevenDaysBeforeDateString();
        String yesterdayDateString = DateUtil.getYesterdayDateString();
        List<AreaAverageRecentPriceRedis> redisPrice = areaAverageRecentPriceRedisRepository.findByAreaCodeAndProductCode(getAreaNameByCode(areaCode), getProductNameByCode(productCode));
        List<AreaAverageRecentPriceRedis> result = processRedisData(redisPrice);
        if (result != null) {
            recordMetric("redis", start);
            return result;
        }

        List<AreaAverageRecentPriceDto> dbPrice = areaAverageRecentPriceRepository.findByLastSevenDays(sevenDaysBeforeDateString, yesterdayDateString, areaCode, productCode);
        if (!dbPrice.isEmpty()) { // maria 존재
            LOGGER.info("[findAreaAverageRecentPriceSevenDays] db data does exist");
            result = dbPrice.stream()
                    .peek(station -> {
                        station.setAreaCode(getAreaNameByCode(station.getAreaCode()));
                        station.setProductCode(getProductNameByCode(station.getProductCode()));
                        station.setBaseDate(DateUtil.formatDateString(station.getBaseDate()));
                    })
                    .map(AreaAverageRecentPriceRedis::transferDataToRedis)
                    .toList();
            LOGGER.info("data size: {}", result.size());
            recordMetric("maria", start);
            LOGGER.info("data -> redis");
            areaAverageRecentPriceRedisRepository.saveAll(result);

            return result;
        }
        LOGGER.info("[findAreaAverageRecentPriceSevenDays] db data does not exist");

        LOGGER.info("data does not exist");
        return null;
    }

    public List<AverageRecentPriceRedis> findAverageRecentPriceSevenDays(String productCode) {
        LOGGER.info("[findAverageRecentPriceSevenDays]");
        long start = System.currentTimeMillis();
        String sevenDaysBeforeDateString = DateUtil.getSevenDaysBeforeDateString();
        String yesterdayDateString = DateUtil.getYesterdayDateString();
        // 데이터베이스에서 지난 7일간의 AverageRecentPrice 객체를 불러옴
        List<AverageRecentPriceRedis> redisPrice = averageRecentPriceRedisRepository.findByProductCode(getProductNameByCode(productCode));
        List<AverageRecentPriceRedis> result = processRedisData(redisPrice);
        if (result != null) {
            recordMetric("redis", start);
            return result;
        }

        List<AverageRecentPriceDto> dbPrice = averageRecentPriceRepository.findByLastSevenDays(sevenDaysBeforeDateString, yesterdayDateString, productCode);
        if (!dbPrice.isEmpty()) { // maria 존재
            LOGGER.info("[findAverageRecentPriceSevenDays] db data does exist");
            result = dbPrice.stream()
                    .peek(station -> {
                        station.setProductCode(getProductNameByCode(station.getProductCode()));
                        station.setDate(DateUtil.formatDateString(station.getDate()));
                    })
                    .map(AverageRecentPriceRedis::transferDataToRedis)
                    .toList();
            LOGGER.info("data size: {}", result.size());
            recordMetric("maria", start);
            LOGGER.info("data -> redis");
            averageRecentPriceRedisRepository.saveAll(result);

            return result;
        }
        LOGGER.info("[findAverageRecentPriceSevenDays] db data does not exist");

        LOGGER.info("data does not exist");
        return null;
    }
}
