package com.pj.oil.gasStationApi;

//import com.pj.oil.cache.GasStationCacheService;
import com.pj.oil.config.PropertyConfiguration;
import com.pj.oil.config.GasStationHttpInterface;
import com.pj.oil.gasStation.entity.maria.*;
import com.pj.oil.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GasStationApiService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final PropertyConfiguration properties;
    private final GasStationHttpInterface httpInterface;
//    private final GasStationCacheService gasStationCacheService;

    /**
     * 공통 로직
     *
     * @param dto
     * @param notExistedLogMessage
     * @param <T>
     * @return
     */
    public <T extends GasStationBase> T processDto(T dto, String notExistedLogMessage) {
        if (dto == null) {
            LOGGER.info(notExistedLogMessage);
            return dto;
        }
        LOGGER.info("data dose existed, dto: {}", dto);
        return dto;
    }

    public <T extends GasStationBase> List<T> processDto(List<T> dto, String notExistedLogMessage) {
        if (dto == null) {
            LOGGER.info(notExistedLogMessage);
            return dto;
        }
        LOGGER.info("data dose existed, dto size: {}", dto.size());
        LOGGER.info("data: {}", dto.toString());
        return dto;
    }


    /**
     * 공통 로직
     *
     * @param dto
     * @param notExistedLogMessage
     * @param <T>
     * @return
     */
    public <T extends GasStationBase> List<T> processDtoList(List<T> dto, String notExistedLogMessage) {
        if (dto.isEmpty()) {
            LOGGER.info(notExistedLogMessage);
            return dto;
        }
        LOGGER.info("data dose existed, dto size: {}", dto.size());
        return dto;
    }

    /**
     * 전국 주유소 평균가격
     *
     * @return AverageAllPriceDto
     */
    public List<AverageAllPrice> getAvgAllPrice() {
        LOGGER.info("[getAvgAllPrice]");
        List<AverageAllPrice> dto = JsonUtil.convertOilJsonToList(
                httpInterface.getAvgAllPrice(
                        properties.getApiKey()
                ), AverageAllPrice.class);
        return processDto(dto, "data does not exist");
    }

    /**
     * 최근 date일간 전국 일일 지역별 모든 제품 평균가격
     *
     * @param areaCd
     * @param date
     * @return AreaAverageRecentPriceDto
     */
    public List<AreaAverageRecentPrice> getAreaAvgRecentNDateAllProdPrice(String areaCd, String date) {
        LOGGER.info("[getAreaAvgRecentNDateAllProdPrice]");
        List<AreaAverageRecentPrice> dto = JsonUtil.convertOilJsonToList(
                httpInterface.getAreaAvgRecentNDateAllProdPrice(
                        properties.getApiKey(),
                        areaCd,
                        date
                ), AreaAverageRecentPrice.class);
        return processDtoList(dto, "data does not exist");
    }

    /**
     * 특정 지역 특정 제품 최저가 주유소(TOP20)
     *
     * @param prodcd
     * @param areaCd
     * @return LowTop20PriceDto
     */
    public List<LowTop20Price> getAreaLowTop20ProdPrice(String prodcd, String areaCd) {
        LOGGER.info("[getAreaLowTop20ProdPrice]");
        List<LowTop20Price> dtoList = JsonUtil.convertOilJsonToList(
                httpInterface.getAreaLowTop20ProdPrice(
                        properties.getApiKey(),
                        prodcd,
                        areaCd
                ), LowTop20Price.class);
        List<LowTop20Price> dto = setProductAndAreaInfo(dtoList, prodcd, areaCd);
        return processDto(dto, "data does not exist");
    }
    public List<LowTop20Price> setProductAndAreaInfo(List<LowTop20Price> dtoList, String prodcd, String areaCd) {
        // Product와 Area 엔티티를 조회

        // 조회된 Product와 Area를 각 LowTop20Price 객체에 설정
        for (LowTop20Price dto : dtoList) {
            dto.setProductCode(prodcd);
            dto.setAreaCode(areaCd);
        }

        return dtoList;
    }

}