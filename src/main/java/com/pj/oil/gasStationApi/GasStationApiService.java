package com.pj.oil.gasStationApi;

import com.pj.oil.config.PropertyConfiguration;
import com.pj.oil.config.GasStationHttpInterface;
import com.pj.oil.gasStationApi.dto.*;
import com.pj.oil.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GasStationApiService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final PropertyConfiguration properties;
    private final GasStationHttpInterface service;

    /**
     * 공통 로직
     *
     * @param dto
     * @param notExistedLogMessage
     * @param <T>
     * @return
     */
    public <T extends ApiBaseDto> T processDto(T dto, String notExistedLogMessage) {
        if (dto == null) {
            LOGGER.info(notExistedLogMessage);
            return dto;
        }
        LOGGER.info("data dose existed, dto: {}", dto);
        return dto;
    }

    /**
     * 전국 주유소 평균가격
     *
     * @return AverageAllPriceDto
     */
    public AverageAllPriceDto getAvgAllPrice() {
        LOGGER.info("[getAvgAllPrice]");
        AverageAllPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAvgAllPrice(
                        properties.getApiKey()
                ), AverageAllPriceDto.class);
        return processDto(dto, "data does not exist");
    }

    /*
    * 전국 주유소 최근 일주일 평균 가격
    * */
    public AverageWeekPriceDto getAvgWeekPrice(){
        LOGGER.info("[getAvgWeekPrice]");
        AverageWeekPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getWeekAveragePrice(
                        properties.getApiKey()
                ), AverageWeekPriceDto.class);
        return processDto(dto, "data does not exist");
    }

    /**
     * 시도별 주유소 평균가격 - 전체 시도, 전체 제품
     *
     * @return AverageSidoPriceDto
     */
    public AverageSidoPriceDto getAvgAllSidoAllProdPrice() {
        LOGGER.info("[getAvgAllSidoAllProdPrice]");
        AverageSidoPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAvgAllSidoAllProdPrice(
                        properties.getApiKey()
                ), AverageSidoPriceDto.class);

        return processDto(dto, "data does not exist");
    }


    /**
     * 시도별 주유소 평균가격 - 특정 시도, 특정 제품
     *
     * @param areaCd
     * @param prodcd
     * @return AverageSidoPriceDto
     */
    public AverageSidoPriceDto getAvgSidoProdPrice(String areaCd, String prodcd) {
        LOGGER.info("[getAvgSidoAllProdPrice]");
        AverageSidoPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAvgSidoProdPrice(
                        properties.getApiKey(),
                        areaCd,
                        prodcd
                ), AverageSidoPriceDto.class);

        return processDto(dto, "data does not exist");
    }

    /**
     * 최근 date일간 전국 일일 지역별 모든 제품 평균가격
     *
     * @param areaCd
     * @param date
     * @return AreaAverageRecentPriceDto
     */
    public AreaAverageRecentPriceDto getAreaAvgRecentNDateAllProdPrice(String areaCd, String date) {
        LOGGER.info("[getAreaAvgRecentNDateAllProdPrice]");
        AreaAverageRecentPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAreaAvgRecentNDateAllProdPrice(
                        properties.getApiKey(),
                        areaCd,
                        date
                ), AreaAverageRecentPriceDto.class);

        return processDto(dto, "data does not exist");
    }



    /**
     * 특정 지역 특정 제품 최저가 주유소(TOP20)
     *
     * @param prodcd
     * @param areaCd
     * @return LowTop20PriceDto
     */
    public LowTop20PriceDto getAreaLowTop20ProdPrice(String prodcd, String areaCd) {
        LOGGER.info("[getAreaLowTop20ProdPrice]");
        LowTop20PriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAreaLowTop20ProdPrice(
                        properties.getApiKey(),
                        prodcd,
                        areaCd
                ), LowTop20PriceDto.class);

        return processDto(dto, "data does not exist");
    }

    /**
     * 오피넷 데이터 관련 시/도 지역코드 조회
     *
     * @return AreaDto
     */
    public AreaDto getSido() {
        LOGGER.info("[getSido]");
        AreaDto dto = JsonUtil.convertJsonStringToObject(
                service.getSido(
                        properties.getApiKey()
                ), AreaDto.class);

        return processDto(dto, "data does not exist");
    }

}