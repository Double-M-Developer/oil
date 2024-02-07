package com.pj.oil.config;

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
     * 오피넷 데이터 관련 지역코드 - 시도
     * http://www.opinet.co.kr/api/areaCode.do?out=xml&code={apiKey}
     *
     * @param apiKey
     * @return AreaDto
     */
    @GetExchange("areaCode.do?out=json&code={apiKey}")
    String getSido(@PathVariable String apiKey);


    @GetExchange("avgWeekPrice.do?out=json&code={apiKey}")
    String getWeekAveragePrice(@PathVariable String apiKey);

}