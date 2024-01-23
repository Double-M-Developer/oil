package com.pj.oil.service;

import com.pj.oil.config.PropertyConfiguration;
import com.pj.oil.dto.api.*;
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
     * 시도별 주유소 평균가격 - 특정 시도, 전체 제품
     *
     * @param areaCd
     * @return AverageSidoPriceDto
     */
    public AverageSidoPriceDto getAvgSidoAllProdPrice(String areaCd) {
        LOGGER.info("[getAvgSidoAllProdPrice]");
        AverageSidoPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAvgSidoAllProdPrice(
                        properties.getApiKey(),
                        areaCd
                ), AverageSidoPriceDto.class);

        return processDto(dto, "data does not exist");
    }

    /**
     * 시도별 주유소 평균가격 - 전체 시도, 특정 제품
     *
     * @param prodcd
     * @return AverageSidoPriceDto
     */
    public AverageSidoPriceDto getAvgAllSidoProdPrice(String prodcd) {
        LOGGER.info("[getAvgSidoAllProdPrice]");
        AverageSidoPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAvgSidoAllProdPrice(
                        properties.getApiKey(),
                        prodcd
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
     * 시군구별 주유소 평균가격 - 전체 시군구 전체 제품
     *
     * @param sido
     * @return AverageSigunPriceDto
     */
    public AverageSigunPriceDto getAvgAllSigunAllProdPrice(String sido) {
        LOGGER.info("[getAvgAllSigunAllProdPrice]");
        AverageSigunPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAvgAllSigunAllProdPrice(
                        properties.getApiKey(),
                        sido
                ), AverageSigunPriceDto.class);

        return processDto(dto, "data does not exist");
    }

    /**
     * 시군구별 주유소 평균가격 - 특정 시군구 전체 제품
     *
     * @param sido
     * @param sigun
     * @return AverageSigunPriceDto
     */
    public AverageSigunPriceDto getAvgSigunAllProdPrice(String sido, String sigun) {
        LOGGER.info("[getAvgAllSigunAllProdPrice]");
        AverageSigunPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAvgSigunAllProdPrice(
                        properties.getApiKey(),
                        sido,
                        sigun
                ), AverageSigunPriceDto.class);

        return processDto(dto, "data does not exist");
    }

    /**
     * 시군구별 주유소 평균가격 - 전체 시군구 특정 제품
     *
     * @param sido
     * @param prodcd
     * @return AverageSigunPriceDto
     */
    public AverageSigunPriceDto getAvgAllSigunProdPrice(String sido, String prodcd) {
        LOGGER.info("[getAvgAllSigunAllProdPrice]");
        AverageSigunPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAvgAllSigunProdPrice(
                        properties.getApiKey(),
                        sido,
                        prodcd
                ), AverageSigunPriceDto.class);

        return processDto(dto, "data does not exist");
    }

    /**
     * 시군구별 주유소 평균가격 - 특정 시군구 특정 제품
     *
     * @param sido
     * @param sigun
     * @param prodcd
     * @return AverageSigunPriceDto
     */
    public AverageSigunPriceDto getAvgSigunProdPrice(String sido, String sigun, String prodcd) {
        LOGGER.info("[getAvgSigunProdPrice]");
        AverageSigunPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAvgSigunProdPrice(
                        properties.getApiKey(),
                        sido,
                        sigun,
                        prodcd
                ), AverageSigunPriceDto.class);

        return processDto(dto, "data does not exist");
    }

    /**
     * 최근 7일간 모든 제품 전국 일일 평균가격
     *
     * @return AreaAverageRecentPriceDto
     */
    public AreaAverageRecentPriceDto getAvgRecent7DateAllProdPrice() {
        LOGGER.info("[getAvgRecent7DateAllProdPrice]");
        AreaAverageRecentPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAvgRecent7DateAllProdPrice(
                        properties.getApiKey()
                ), AreaAverageRecentPriceDto.class);

        return processDto(dto, "data does not exist");
    }

    /**
     * 최근 date일간 모든 제품 전국 일일 평균가격
     *
     * @param date - 최대 7일 전까지 20240119
     * @return AreaAverageRecentPriceDto
     */
    public AreaAverageRecentPriceDto getAvgRecentNDateAllProdPrice(String date) {
        LOGGER.info("[getAvgRecentNDateAllProdPrice]");
        AreaAverageRecentPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAvgRecentNDateAllProdPrice(
                        properties.getApiKey(),
                        date
                ), AreaAverageRecentPriceDto.class);

        return processDto(dto, "data does not exist");
    }

    /**
     * 최근 date일간 특정 제품 전국 일일 평균가격
     *
     * @param date   - 최대 7일 전까지 20240119
     * @param prodcd
     * @return AreaAverageRecentPriceDto
     */
    public AreaAverageRecentPriceDto getAvgRecentNDateProdPrice(String date, String prodcd) {
        LOGGER.info("[getAvgRecentNDateProdPrice]");
        AreaAverageRecentPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAvgRecentNDateProdPrice(
                        properties.getApiKey(),
                        date,
                        prodcd
                ), AreaAverageRecentPriceDto.class);

        return processDto(dto, "data does not exist");
    }

    /**
     * 최근 7일간 특정 제품 전국 일일 평균가격
     *
     * @param prodcd
     * @return AreaAverageRecentPriceDto
     */
    public AreaAverageRecentPriceDto getAvgRecent7DateProdPrice(String prodcd) {
        LOGGER.info("[getAvgRecent7DateProdPrice]");
        AreaAverageRecentPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAvgRecent7DateProdPrice(
                        properties.getApiKey(),
                        prodcd
                ), AreaAverageRecentPriceDto.class);

        return processDto(dto, "data does not exist");
    }

    /**
     * 최근 7일간 전국 일일 모든 상표별 모든 제품 평균가격
     *
     * @return PollAverageRecentPriceDto
     */
    public PollAverageRecentPriceDto getAllPollAvgRecent7DateAllProdPrice() {
        LOGGER.info("[getAllPollAvgRecent7DateAllProdPrice]");
        PollAverageRecentPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAllPollAvgRecent7DateAllProdPrice(
                        properties.getApiKey()
                ), PollAverageRecentPriceDto.class);

        return processDto(dto, "data does not exist");
    }

    /**
     * 최근 7일간 전국 일일 모든 상표별 특정 제품 평균가격
     *
     * @param prodcd
     * @return PollAverageRecentPriceDto
     */
    public PollAverageRecentPriceDto getAllPollAvgRecent7DateProdPrice(String prodcd) {
        LOGGER.info("[getAllPollAvgRecent7DateProdPrice]");
        PollAverageRecentPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAllPollAvgRecent7DateProdPrice(
                        properties.getApiKey(),
                        prodcd
                ), PollAverageRecentPriceDto.class);

        return processDto(dto, "data does not exist");
    }

    /**
     * 최근 7일간 전국 일일 특정 상표별 특정 제품 평균가격
     *
     * @param prodcd
     * @param pollcd
     * @return PollAverageRecentPriceDto
     */
    public PollAverageRecentPriceDto getPollAvgRecent7DateProdPrice(String prodcd, String pollcd) {
        LOGGER.info("[getPollAvgRecent7DateProdPrice]");
        PollAverageRecentPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getPollAvgRecent7DateProdPrice(
                        properties.getApiKey(),
                        prodcd,
                        pollcd
                ), PollAverageRecentPriceDto.class);

        return processDto(dto, "data does not exist");
    }

    /**
     * 최근 7일간 전국 일일 특정 상표별 모든 제품 평균가격
     *
     * @param pollcd
     * @return PollAverageRecentPriceDto
     */
    public PollAverageRecentPriceDto getPollAvgRecent7DateAllProdPrice(String pollcd) {
        LOGGER.info("[getPollAvgRecent7DateAllProdPrice]");
        PollAverageRecentPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getPollAvgRecent7DateAllProdPrice(
                        properties.getApiKey(),
                        pollcd
                ), PollAverageRecentPriceDto.class);

        return processDto(dto, "data does not exist");
    }

    /**
     * 최근 7일간 전국 일일 지역별 모든 제품 평균가격
     *
     * @param areaCd
     * @return AreaAverageRecentPriceDto
     */
    public AreaAverageRecentPriceDto getAreaAvgRecent7DateAllProdPrice(String areaCd) {
        LOGGER.info("[getAreaAvgRecent7DateAllProdPrice]");
        AreaAverageRecentPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAreaAvgRecent7DateAllProdPrice(
                        properties.getApiKey(),
                        areaCd
                ), AreaAverageRecentPriceDto.class);

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
     * 최근 date일간 전국 일일 지역별 특정 제품 평균가격
     *
     * @param areaCd
     * @param date
     * @param prodcd
     * @return AreaAverageRecentPriceDto
     */
    public AreaAverageRecentPriceDto getAreaAvgRecentNDateProdPrice(String areaCd, String date, String prodcd) {
        LOGGER.info("[getAreaAvgRecentNDateProdPrice]");
        AreaAverageRecentPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAreaAvgRecentNDateProdPrice(
                        properties.getApiKey(),
                        areaCd,
                        date,
                        prodcd
                ), AreaAverageRecentPriceDto.class);

        return processDto(dto, "data does not exist");
    }

    /**
     * 최근 7 전국 일일 지역별 특정 제품 평균가격
     *
     * @param areaCd
     * @param prodcd
     * @return
     */
    public AreaAverageRecentPriceDto getAreaAvgRecent7DateProdPrice(String areaCd, String prodcd) {
        LOGGER.info("[getAreaAvgRecent7DateProdPrice]");
        AreaAverageRecentPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAreaAvgRecent7DateProdPrice(
                        properties.getApiKey(),
                        areaCd,
                        prodcd
                ), AreaAverageRecentPriceDto.class);

        return processDto(dto, "data does not exist");
    }

    /**
     * 최근 1주의 전국 주간 모든 제품 평균유가
     *
     * @return AreaAverageLastWeekPriceDto
     */
    public AreaAverageLastWeekPriceDto getAllAreaAvgLastWeekAllProd() {
        LOGGER.info("[getAllAreaAvgLastWeekAllProd]");
        AreaAverageLastWeekPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAllAreaAvgLastWeekAllProd(
                        properties.getApiKey()
                ), AreaAverageLastWeekPriceDto.class);

        return processDto(dto, "data does not exist");
    }

    /**
     * 최근 1주의 전국 주간 특정 제품 평균유가
     *
     * @param prodcd
     * @return AreaAverageLastWeekPriceDto
     */
    public AreaAverageLastWeekPriceDto getAllAreaAvgLastWeekProd(String prodcd) {
        LOGGER.info("[getAllAreaAvgLastWeekProd]");
        AreaAverageLastWeekPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAllAreaAvgLastWeekProd(
                        properties.getApiKey(),
                        prodcd
                ), AreaAverageLastWeekPriceDto.class);

        return processDto(dto, "data does not exist");
    }

    /**
     * 최근 1주의 시도별 주간 특정 제품 평균유가
     *
     * @param prodcd
     * @param sido
     * @return AreaAverageLastWeekPriceDto
     */
    public AreaAverageLastWeekPriceDto getAreaAvgLastWeekProd(String prodcd, String sido) {
        LOGGER.info("[getAreaAvgLastWeekProd]");
        AreaAverageLastWeekPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAreaAvgLastWeekProd(
                        properties.getApiKey(),
                        prodcd,
                        sido
                ), AreaAverageLastWeekPriceDto.class);

        return processDto(dto, "data does not exist");
    }

    /**
     * 최근 1주의 시도별 주간 모든 제품 평균유가
     *
     * @param sido
     * @return
     */
    public AreaAverageLastWeekPriceDto getAreaAvgLastWeekAllProd(String sido) {
        LOGGER.info("[getAreaAvgLastWeekAllProd]");
        AreaAverageLastWeekPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAreaAvgLastWeekAllProd(
                        properties.getApiKey(),
                        sido
                ), AreaAverageLastWeekPriceDto.class);

        return processDto(dto, "data does not exist");
    }

    /**
     * 모든 지역 특정 제품 최저가 주유소(TOP20)
     *
     * @param prodcd
     * @return LowTop20PriceDto
     */
    public LowTop20PriceDto getAllAreaLowTop20ProdPrice(String prodcd) {
        LOGGER.info("[getAllAreaLowTop20ProdPrice]");
        LowTop20PriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAllAreaLowTop20ProdPrice(
                        properties.getApiKey(),
                        prodcd
                ), LowTop20PriceDto.class);

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
     * 특정 위치 중심으로 반경 내 주유소 가격/거리 순 정렬
     *
     * @param prodcd
     * @param x      기준 위치 X좌표 (KATEC)
     * @param y      기준 위치 Y좌표 (KATEC)
     * @param radius 최대 5000 최소1, m단위
     * @param sort   가격순 1, 거리순 2
     * @return AroundAllPriceDto
     */
    public AroundAllPriceDto getAroundAll(String prodcd, String x, String y, String radius, String sort) {
        LOGGER.info("[getAroundAll]");
        AroundAllPriceDto dto = JsonUtil.convertJsonStringToObject(
                service.getAroundAll(
                        properties.getApiKey(),
                        prodcd,
                        x,
                        y,
                        radius,
                        sort
                ), AroundAllPriceDto.class);

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

    /**
     * 오피넷 데이터 관련 시/군/구 지역코드 조회
     * 시/도 하위 항목
     *
     * @return AreaDto
     */
    public AreaDto getSigun(String areaCd) {
        LOGGER.info("[getSigun]");
        AreaDto dto = JsonUtil.convertJsonStringToObject(
                service.getSigun(
                        properties.getApiKey(), areaCd
                ), AreaDto.class);

        return processDto(dto, "data does not exist");
    }

}