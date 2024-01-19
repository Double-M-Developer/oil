package com.pj.oil.service;

import com.pj.oil.dto.api.AverageAllPriceDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface GasStationHttpInterface { //HttpServiceProxyFactory

    /**
     * 전국 주유소 평균가격
     * http://www.opinet.co.kr/api/avgAllPrice.do?out=json&code={apiKey}
     *
     * @param apiKey
     * @return AverageAllPriceDto
     */
    @GetExchange("avgAllPrice.do?out=json&code={apiKey}")
    String getAvgAllPrice(@PathVariable String apiKey);

    /**
     * 시도별 주유소 평균가격 - 전체 시도, 전체 제품
     * https://www.opinet.co.kr/api/avgSidoPrice.do?out=json&code={apiKey}
     *
     * @param apiKey
     * @return AverageSidoPriceDto
     */
    @GetExchange("avgSidoPrice.do?out=json&code={apiKey}")
    String getAvgAllSidoAllProdPrice(@PathVariable String apiKey);

    /**
     * 시도별 주유소 평균가격 - 특정 시도, 전체 제품
     * https://www.opinet.co.kr/api/avgSidoPrice.do?out=json&code={apiKey}&sido={areaCd}
     *
     * @param apiKey
     * @param areaCd
     * @return AverageSidoPriceDto
     */
    @GetExchange("avgSidoPrice.do?out=json&code={apiKey}&sido={areaCd}")
    String getAvgSidoAllProdPrice(@PathVariable String apiKey, @PathVariable String areaCd);

    /**
     * 시도별 주유소 평균가격 - 전체 시도, 특정 제품
     * https://www.opinet.co.kr/api/avgSidoPrice.do?out=json&code={apiKey}&prodcd={prodcd}
     *
     * @param apiKey
     * @param prodcd
     * @return AverageSidoPriceDto
     */
    @GetExchange("avgSidoPrice.do?out=json&code={apiKey}&prodcd={prodcd}")
    String getAvgAllSidoProdPrice(@PathVariable String apiKey, @PathVariable String prodcd);

    /**
     * 시도별 주유소 평균가격 - 특정 시도, 특정 제품
     * https://www.opinet.co.kr/api/avgSidoPrice.do?out=json&code={apiKey}&sido={areaCd}&prodcd={prodcd}
     *
     * @param apiKey
     * @param areaCd
     * @param prodcd
     * @return AverageSidoPriceDto
     */
    @GetExchange("avgSidoPrice.do?out=json&code={apiKey}&sido={areaCd}&prodcd={prodcd}")
    String getAvgSidoProdPrice(@PathVariable String apiKey, @PathVariable String areaCd, @PathVariable String prodcd);

    /**
     * 시군구별 주유소 평균가격 - 전체 시군구 전체 제품
     * http://www.opinet.co.kr/api/avgSigunPrice.do?out=xml&code={apiKey}&sido={sido}
     *
     * @param apiKey
     * @param sido
     * @return AverageSigunPriceDto
     */
    @GetExchange("avgSigunPrice.do?out=json&code={apiKey}&sido={sido}")
    String getAvgAllSigunAllProdPrice(@PathVariable String apiKey, @PathVariable String sido);

    /**
     * 시군구별 주유소 평균가격 - 특정 시군구 전체 제품
     * http://www.opinet.co.kr/api/areaCode.do?out=xml&code={apiKey}&sido={sido}&sigun={sigun}
     *
     * @param apiKey
     * @param sido
     * @param sigun
     * @return AverageSigunPriceDto
     */
    @GetExchange("avgSigunPrice.do?out=json&code={apiKey}&sido={sido}&sigun={sigun}")
    String getAvgSigunAllProdPrice(@PathVariable String apiKey, @PathVariable String sido, @PathVariable String sigun);

    /**
     * 시군구별 주유소 평균가격 - 전체 시군구 특정 제품
     * http://www.opinet.co.kr/api/areaCode.do?out=xml&code={apiKey}&sido={sido}&prodcd={prodcd}
     *
     * @param apiKey
     * @param sido
     * @param prodcd
     * @return AverageSigunPriceDto
     */
    @GetExchange("avgSigunPrice.do?out=json&code={apiKey}&sido={sido}&prodcd={prodcd}")
    String getAvgAllSigunProdPrice(@PathVariable String apiKey, @PathVariable String sido, @PathVariable String prodcd);

    /**
     * 시군구별 주유소 평균가격 - 특정 시군구 특정 제품
     * http://www.opinet.co.kr/api/areaCode.do?out=xml&code={apiKey}&sido={sido}&sigun={sigun}&prodcd={prodcd}
     *
     * @param apiKey
     * @param sido
     * @param sigun
     * @param prodcd
     * @return AverageSigunPriceDto
     */
    @GetExchange("avgSigunPrice.do?out=json&code={apiKey}&sido={sido}&sigun={sigun}&prodcd={prodcd}")
    String getAvgSigunProdPrice(@PathVariable String apiKey, @PathVariable String sido, @PathVariable String sigun, @PathVariable String prodcd);

    /**
     * 최근 7일간 모든 제품 전국 일일 평균가격
     * http://www.opinet.co.kr/api/avgRecentPrice.do?out=xml&code={apiKey}
     *
     * @param apiKey
     * @return AreaAverageRecentPriceDto
     */
    @GetExchange("avgRecentPrice.do?out=json&code={apiKey}")
    String getAvgRecent7DateAllProdPrice(@PathVariable String apiKey);

    /**
     * 최근 date일간 모든 제품 전국 일일 평균가격
     * http://www.opinet.co.kr/api/avgRecentPrice.do?out=xml&code={apiKey}&date={date}
     *
     * @param apiKey
     * @param date - 최대 7일 전까지
     * @return AreaAverageRecentPriceDto
     */
    @GetExchange("avgRecentPrice.do?out=json&code={apiKey}&date={date}")
    String getAvgRecentNDateAllProdPrice(@PathVariable String apiKey, @PathVariable String date);

    /**
     * 최근 date일간 특정 제품 전국 일일 평균가격
     * http://www.opinet.co.kr/api/avgRecentPrice.do?out=xml&code={apiKey}&date={date}&prodcd={prodcd}
     *
     * @param apiKey
     * @param date - 최대 7일 전까지
     * @param prodcd
     * @return AreaAverageRecentPriceDto
     */
    @GetExchange("avgRecentPrice.do?out=json&code={apiKey}&date={date}&prodcd={prodcd}")
    String getAvgRecentNDateProdPrice(@PathVariable String apiKey, @PathVariable String date, @PathVariable String prodcd);

    /**
     * 최근 7일간 특정 제품 전국 일일 평균가격
     * http://www.opinet.co.kr/api/avgRecentPrice.do?out=xml&code={apiKey}&prodcd={prodcd}
     *
     * @param apiKey
     * @param prodcd
     * @return AreaAverageRecentPriceDto
     */
    @GetExchange("avgRecentPrice.do?out=json&code={apiKey}&prodcd={prodcd}")
    String getAvgRecent7DateProdPrice(@PathVariable String apiKey, @PathVariable String prodcd);

    /**
     * 최근 7일간 전국 일일 모든 상표별 모든 제품 평균가격
     * http://www.opinet.co.kr/api/pollAvgRecentPrice.do?out=xml&code={apiKey}
     *
     * @param apiKey
     * @return PollAverageRecentPriceDto
     */
    @GetExchange("pollAvgRecentPrice.do?out=json&code={apiKey}")
    String getAllPollAvgRecent7DateAllProdPrice(@PathVariable String apiKey);

    /**
     * 최근 7일간 전국 일일 모든 상표별 특정 제품 평균가격
     * http://www.opinet.co.kr/api/pollAvgRecentPrice.do?out=xml&code={apiKey}&prodcd={prodcd}
     *
     * @param apiKey
     * @param prodcd
     * @return PollAverageRecentPriceDto
     */
    @GetExchange("pollAvgRecentPrice.do?out=json&code={apiKey}&prodcd={prodcd}")
    String getAllPollAvgRecent7DateProdPrice(@PathVariable String apiKey, @PathVariable String prodcd);

    /**
     * 최근 7일간 전국 일일 특정 상표별 특정 제품 평균가격
     * http://www.opinet.co.kr/api/pollAvgRecentPrice.do?out=xml&code={apiKey}&prodcd={prodcd}&pollcd={pollcd}
     *
     * @param apiKey
     * @param prodcd
     * @param pollcd
     * @return PollAverageRecentPriceDto
     */
    @GetExchange("pollAvgRecentPrice.do?out=json&code={apiKey}&prodcd={prodcd}&pollcd={pollcd}")
    String getPollAvgRecent7DateProdPrice(@PathVariable String apiKey, @PathVariable String prodcd, @PathVariable String pollcd);

    /**
     * 최근 7일간 전국 일일 특정 상표별 모든 제품 평균가격
     * http://www.opinet.co.kr/api/pollAvgRecentPrice.do?out=xml&code={apiKey}&pollcd={pollcd}
     *
     * @param apiKey
     * @param pollcd
     * @return PollAverageRecentPriceDto
     */
    @GetExchange("pollAvgRecentPrice.do?out=json&code={apiKey}&pollcd={pollcd}")
    String getPollAvgRecent7DateAllProdPrice(@PathVariable String apiKey, @PathVariable String pollcd);

    /**
     * 최근 7일간 전국 일일 지역별 모든 제품 평균가격
     * http://www.opinet.co.kr/api/areaAvgRecentPrice.do?out=json&code={apiKey}&area={areaCd}
     *
     * @param apiKey
     * @param areaCd
     * @return AreaAverageRecentPriceDto
     */
    @GetExchange("areaAvgRecentPrice.do?out=json&code={apiKey}&area={areaCd}")
    String getAreaAvgRecent7DateAllProdPrice(@PathVariable String apiKey, @PathVariable String areaCd);

    /**
     * 최근 date일간 전국 일일 지역별 모든 제품 평균가격
     * http://www.opinet.co.kr/api/areaAvgRecentPrice.do?out=json&code={apiKey}&area={areaCd}&date={date}
     *
     * @param apiKey
     * @param areaCd
     * @param date
     * @return AreaAverageRecentPriceDto
     */
    @GetExchange("areaAvgRecentPrice.do?out=json&code={apiKey}&area={areaCd}&date={date}")
    String getAreaAvgRecentNDateAllProdPrice(@PathVariable String apiKey, @PathVariable String areaCd, @PathVariable String date);

    /**
     * 최근 date일간 전국 일일 지역별 특정 제품 평균가격
     * http://www.opinet.co.kr/api/areaAvgRecentPrice.do?out=json&code={apiKey}&area={areaCd}&date={date}&prodcd={prodcd}
     *
     * @param apiKey
     * @param areaCd
     * @param date
     * @param prodcd
     * @return AreaAverageRecentPriceDto
     */
    @GetExchange("areaAvgRecentPrice.do?out=json&code={apiKey}&area={areaCd}&date={date}&prodcd={prodcd}")
    String getAreaAvgRecentNDateProdPrice(@PathVariable String apiKey, @PathVariable String areaCd, @PathVariable String date, @PathVariable String prodcd);

    /**
     * 최근 7 전국 일일 지역별 특정 제품 평균가격
     * http://www.opinet.co.kr/api/areaAvgRecentPrice.do?out=json&code={apiKey}&area={areaCd}&prodcd={prodcd}
     *
     * @param apiKey
     * @param areaCd
     * @param prodcd
     * @return
     */
    @GetExchange("areaAvgRecentPrice.do?out=json&code={apiKey}&area={areaCd}&prodcd={prodcd}")
    String getAreaAvgRecent7DateProdPrice(@PathVariable String apiKey, @PathVariable String areaCd, @PathVariable String prodcd);

    /**
     * 최근 1주의 전국 주간 모든 제품 평균유가
     * http://www.opinet.co.kr/api/avgLastWeek.do?code={apiKey}
     *
     * @param apiKey
     * @return AreaAverageLastWeekPriceDto
     */
    @GetExchange("avgLastWeek.do?out=json&code={apiKey}")
    String getAllAreaAvgLastWeekAllProd(@PathVariable String apiKey);

    /**
     * 최근 1주의 전국 주간 특정 제품 평균유가
     * http://www.opinet.co.kr/api/avgLastWeek.do?code={apiKey}&prodcd={prodcd}
     *
     * @param apiKey
     * @param prodcd
     * @return AreaAverageLastWeekPriceDto
     */
    @GetExchange("avgLastWeek.do?out=json&code={apiKey}&prodcd={prodcd}")
    String getAllAreaAvgLastWeekProd(@PathVariable String apiKey, @PathVariable String prodcd);

    /**
     * 최근 1주의 시도별 주간 특정 제품 평균유가
     * http://www.opinet.co.kr/api/avgLastWeek.do?code={apiKey}&prodcd={prodcd}&sido={sido}
     *
     * @param apiKey
     * @param prodcd
     * @param sido
     * @return AreaAverageLastWeekPriceDto
     */
    @GetExchange("avgLastWeek.do?out=json&code={apiKey}&prodcd={prodcd}&sido={sido}")
    String getAreaAvgLastWeekProd(@PathVariable String apiKey, @PathVariable String prodcd, @PathVariable String sido);

    /**
     * 최근 1주의 시도별 주간 모든 제품 평균유가
     * http://www.opinet.co.kr/api/avgLastWeek.do?code={apiKey}&sido={sido}
     *
     * @param apiKey
     * @param sido
     * @return
     */
    @GetExchange("avgLastWeek.do?out=json&code={apiKey}&sido={sido}")
    String getAreaAvgLastWeekAllProd(@PathVariable String apiKey, @PathVariable String sido);

    /**
     * 모든 지역 특정 제품 최저가 주유소(TOP20)
     * http://www.opinet.co.kr/api/lowTop10.do?out=xml&code={apiKey}&cnd=20&prodcd={prodcd}
     *
     * @param apiKey
     * @param prodcd
     * @return LowTop20PriceDto
     */
    @GetExchange("lowTop10.do?out=json&code={apiKey}&cnd=20&prodcd={prodcd}")
    String getAllAreaLowTop20ProdPrice(@PathVariable String apiKey, @PathVariable String prodcd);

    /**
     * 특정 지역 특정 제품 최저가 주유소(TOP20)
     * http://www.opinet.co.kr/api/lowTop10.do?out=xml&code={apiKey}&cnd=20&prodcd={prodcd}&area={areaCd}
     *
     * @param apiKey
     * @param prodcd
     * @param areaCd
     * @return LowTop20PriceDto
     */
    @GetExchange("lowTop10.do?out=json&code={apiKey}&cnd=20&prodcd={prodcd}&area={areaCd}")
    String getAreaLowTop20ProdPrice(@PathVariable String apiKey, @PathVariable String prodcd, @PathVariable String areaCd);

    /**
     * 특정 위치 중심으로 반경 내 주유소 가격/거리 순 정렬
     * http://www.opinet.co.kr/api/aroundAll.do?out=json&code={apiKey}&prodcd={prodcd}&x={x}&y={y}&radis={radius}&sort={sort}
     *
     * @param apiKey
     * @param prodcd
     * @param x      기준 위치 X좌표 (KATEC)
     * @param y      기준 위치 Y좌표 (KATEC)
     * @param radius 최대 5000 최소1, m단위
     * @param sort   가격순 1, 거리순 2
     * @return AroundAllPriceDto
     */
    @GetExchange("aroundAll.do?out=json&code={apiKey}&prodcd={prodcd}&x={x}&y={y}&radis={radius}&sort={sort}")
    String getAroundAll(@PathVariable String apiKey, @PathVariable String prodcd, @PathVariable String x, @PathVariable String y, @PathVariable String radius, @PathVariable String sort);


    /**
     * 오피넷 데이터 관련 지역코드 - 시도
     * http://www.opinet.co.kr/api/areaCode.do?out=xml&code={apiKey}
     *
     * @param apiKey
     * @return AreaDto
     */
    @GetExchange("areaCode.do?out=json&code={apiKey}")
    String getSido(@PathVariable String apiKey);

    /**
     * 오피넷 데이터 관련 지역코드 - 시군
     * http://www.opinet.co.kr/api/areaCode.do?out=xml&code={apiKey}&area={areaCd}
     *
     * @param apiKey
     * @return AreaDto
     */
    @GetExchange("areaCode.do?out=json&code={apiKey}&area={areaCd}")
    String getSigun(@PathVariable String apiKey, @PathVariable String areaCd);
}