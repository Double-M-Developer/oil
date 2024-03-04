package com.pj.oil.gasStationApi;

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

    /**
     * 공통 로직
     *
     * @param entity
     * @param <T>
     * @return
     */
    public <T extends GasStationBase> T resultProcess(T entity) {
        if (entity == null) {
            LOGGER.info("data dose not exist");
            return null;
        }
        LOGGER.info("data dose exist, entity: {}", entity);
        return entity;
    }

    public <T extends GasStationBase> List<T> resultProcess(List<T> entity) {
        if (entity == null) {
            LOGGER.info("data dose not exist");
            return null;
        }
        LOGGER.info("data dose exist, entity size: {}", entity.size());
        return entity;
    }

    /**
     * 현재 전국 주유소 평균가격
     *
     * @return AverageAllPriceDto
     */
    public List<AverageAllPrice> getAvgAllPrice() {
        LOGGER.info("[getAvgAllPrice]");
        List<AverageAllPrice> entity = JsonUtil.convertOilJsonToList(
                httpInterface.getAvgAllPrice(
                        properties.getApiKey()
                ), AverageAllPrice.class);
        return resultProcess(entity);
    }

    /**
     * 전날부터 최대 7일 전국 일일 평균가격
     *
     * @param date
     * @return AverageRecentPriceDto
     */
    public List<AverageRecentPrice> getAvgRecentNDateAllProdPrice(String date) {
        LOGGER.info("[getAvgRecentNDateAllProdPrice]");
        List<AverageRecentPrice> entity = JsonUtil.convertOilJsonToList(
                httpInterface.getAvgRecentNDateAllProdPrice(
                        properties.getApiKey(),
                        date
                ), AverageRecentPrice.class);
        return resultProcess(entity);
    }
    //getAvgRecentNDateAllProdPrice

    /**
     * 최근 date일간 전국 일일 지역별 모든 제품 평균가격
     *
     * @param areaCd
     * @param date
     * @return AreaAverageRecentPriceDto
     */
    public List<AreaAverageRecentPrice> getAreaAvgRecentNDateAllProdPrice(String areaCd, String date) {
        LOGGER.info("[getAreaAvgRecentNDateAllProdPrice]");
        List<AreaAverageRecentPrice> entity = JsonUtil.convertOilJsonToList(
                httpInterface.getAreaAvgRecentNDateAllProdPrice(
                        properties.getApiKey(),
                        areaCd,
                        date
                ), AreaAverageRecentPrice.class);
        return resultProcess(entity);
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
        return resultProcess(dto);
    }
    public List<LowTop20Price> setProductAndAreaInfo(List<LowTop20Price> entity, String productCode, String areaCode) {
        // Product 와 Area 를 각 LowTop20Price 객체에 설정
        for (LowTop20Price dto : entity) {
            dto.setProductCode(productCode);
            dto.setAreaCode(areaCode);
        }

        return entity;
    }
}