package com.pj.oil.gasStation;

import com.pj.oil.gasStation.entity.maria.*;
import com.pj.oil.gasStation.entity.redis.AreaAverageRecentPriceRedis;
import com.pj.oil.gasStation.entity.redis.AverageAllPriceRedis;
import com.pj.oil.gasStation.entity.redis.AverageRecentPriceRedis;
import com.pj.oil.gasStation.entity.redis.LowTop20PriceRedis;
import com.pj.oil.gasStation.repository.jpa.*;
import com.pj.oil.gasStation.repository.redis.AreaAverageRecentPriceRedisRepository;
import com.pj.oil.gasStation.repository.redis.AverageAllPriceRedisRepository;
import com.pj.oil.gasStation.repository.redis.AverageRecentPriceRedisRepository;
import com.pj.oil.gasStation.repository.redis.LowTop20PriceRedisRepository;
import com.pj.oil.util.CoordinateUtil;
import com.pj.oil.util.DateUtil;
import org.locationtech.proj4j.ProjCoordinate;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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
    private final DateUtil dateUtil;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private static final Map<String, String> POLL_DIV_NAME = Map.of(
            "SKE", "SK에너지",
            "GSC", "GS칼텍스",
            "HDO", "현대오일뱅크",
            "SOL", "S-OIL",
            "RTE", "자영알뜰",
            "RTX", "고속도로알뜰",
            "NHO", "농협알뜰",
            "ETC", "자가상표",
            "E1G", "E1",
            "SKG", "SK가스");
    private static final Map<String, String> PRODUCT_CODE = Map.of(
            "B034", "고급휘발유",
            "B027", "휘발유",
            "D047", "경유",
            "K015", "LPG");
    private static final Map<String, String> AREA_CODE = new HashMap<>();

    static {
        AREA_CODE.put("01", "서울");
        AREA_CODE.put("02", "경기");
        AREA_CODE.put("03", "강원");
        AREA_CODE.put("04", "충북");
        AREA_CODE.put("05", "충남");
        AREA_CODE.put("06", "전북");
        AREA_CODE.put("07", "전남");
        AREA_CODE.put("08", "경북");
        AREA_CODE.put("09", "경남");
        AREA_CODE.put("10", "부산");
        AREA_CODE.put("11", "제주");
        AREA_CODE.put("14", "대구");
        AREA_CODE.put("15", "인천");
        AREA_CODE.put("16", "광주");
        AREA_CODE.put("17", "대전");
        AREA_CODE.put("18", "울산");
        AREA_CODE.put("19", "세종");
    }


    private <T extends GasStationBase> List<T> processRedisData(List<T> redisData) {
        if (!redisData.isEmpty()) {
            LOGGER.info("cache data does exist");
            return redisData;
        }
        LOGGER.info("cache data does not exist");
        return null;
    }
    private String getPollDivNameByCode(String code) {
        return POLL_DIV_NAME.getOrDefault(code, "기타");
    }

    private String getAreaNameByCode(String code) {
        return AREA_CODE.getOrDefault(code, "기타");
    }

    private String getProductNameByCode(String code) {
        return PRODUCT_CODE.getOrDefault(code, "기타");
    }

    private void transformStationCoordinates(LowTop20Price station) {
        ProjCoordinate transformedCoords = coordinateUtil.convertKATECToWGS84(station.getGisXCoor(), station.getGisYCoor());
        station.setGisXCoor(transformedCoords.x);
        station.setGisYCoor(transformedCoords.y);
    }


    public List<LowTop20PriceRedis> findLowTop20PriceByAreaCodeAndProductCode(String areaCode, String productCode) {
        LOGGER.info("[findLowTop20PriceByAreaCodeAndProductCode] request area: {} product: {}", areaCode, productCode);
        List<LowTop20PriceRedis> redisPrice = lowTop20PriceRedisRepository.findByAreaCodeAndProductCode(areaCode, productCode);
        List<LowTop20PriceRedis> result = processRedisData(redisPrice);
        if (result != null) return result;

        List<LowTop20Price> dbPrice = lowTop20PriceRepository.findLowTop20PriceByAreaCodeAndProductCode(areaCode, productCode);
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
            LOGGER.info("data: {}", result);

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
        String todayDateString = dateUtil.getTodayDateString();
        List<AverageAllPriceRedis> redisPrice = averageAllPriceRedisRepository.findByTradeDate(dateUtil.formatDateString(todayDateString));
        List<AverageAllPriceRedis> result = processRedisData(redisPrice);
        if (result != null) return result;

        List<AverageAllPrice> dbPrice = averageAllPriceRepository.findByTradeDate(todayDateString);
        if (!dbPrice.isEmpty()) { // maria 존재
            LOGGER.info("[findAverageAllPriceByTradeDate] db data does exist");
            result = dbPrice.stream()
                    .peek(station -> {
                        station.setProductCode(getProductNameByCode(station.getProductCode()));
                        station.setTradeDate(dateUtil.formatDateString(station.getTradeDate()));
                    })
                    .map(AverageAllPriceRedis::transferDataToRedis)
                    .toList();
            LOGGER.info("data size: {}", result.size());
            LOGGER.info("data: {}", result);

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
        String sevenDaysBeforeDateString = dateUtil.getSevenDaysBeforeDateString();
        String yesterdayDateString = dateUtil.getYesterdayDateString();
        List<AreaAverageRecentPriceRedis> redisPrice = areaAverageRecentPriceRedisRepository.findByAreaCodeAndProductCode(areaCode, productCode);
        List<AreaAverageRecentPriceRedis> result = processRedisData(redisPrice);
        if (result != null) return result;

        List<AreaAverageRecentPrice> dbPrice = areaAverageRecentPriceRepository.findByLastSevenDays(sevenDaysBeforeDateString, yesterdayDateString, areaCode, productCode);
        if (!dbPrice.isEmpty()) { // maria 존재
            LOGGER.info("[findAreaAverageRecentPriceSevenDays] db data does exist");
            result = dbPrice.stream()
                    .peek(station -> {
                        station.setAreaCode(getAreaNameByCode(station.getAreaCode()));
                        station.setProductCode(getProductNameByCode(station.getProductCode()));
                        station.setBaseDate(dateUtil.formatDateString(station.getBaseDate()));
                    })
                    .map(AreaAverageRecentPriceRedis::transferDataToRedis)
                    .toList();
            LOGGER.info("data size: {}", result.size());
            LOGGER.info("data: {}", result);

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
        String sevenDaysBeforeDateString = dateUtil.getSevenDaysBeforeDateString();
        String yesterdayDateString = dateUtil.getYesterdayDateString();
        // 데이터베이스에서 지난 7일간의 AverageRecentPrice 객체를 불러옴
        List<AverageRecentPriceRedis> redisPrice = averageRecentPriceRedisRepository.findByProductCode(productCode);
        List<AverageRecentPriceRedis> result = processRedisData(redisPrice);
        if (result != null) return result;

        List<AverageRecentPrice> dbPrice = averageRecentPriceRepository.findByLastSevenDays(sevenDaysBeforeDateString, yesterdayDateString, productCode);
        if (!dbPrice.isEmpty()) { // maria 존재
            LOGGER.info("[findAverageRecentPriceSevenDays] db data does exist");
            result = dbPrice.stream()
                    .peek(station -> {
                        station.setProductCode(getProductNameByCode(station.getProductCode()));
                        station.setDate(dateUtil.formatDateString(station.getDate()));
                    })
                    .map(AverageRecentPriceRedis::transferDataToRedis)
                    .toList();
            LOGGER.info("data size: {}", result.size());
            LOGGER.info("data: {}", result);

            LOGGER.info("data -> redis");
            averageRecentPriceRedisRepository.saveAll(result);

            return result;
        }
        LOGGER.info("[findAverageRecentPriceSevenDays] db data does not exist");

        LOGGER.info("data does not exist");
        return null;
    }
}
