package com.pj.oil.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface GasStationHttpInterface { //HttpServiceProxyFactory

    //전국 주유소 평균가격
    //http://www.opinet.co.kr/api/avgAllPrice.do?out=xml&code=XXXXXX
    @GetExchange("avgAllPrice.do?out=json&code={apiKey}")
    String getAvgAllPrice(@PathVariable String apiKey);

    //시도별 주유소 평균가격
    //https://www.opinet.co.kr/api/avgSidoPrice.do?out=json&code=XXXXXX
    @GetExchange("avgSidoPrice.do?out=json&code={apiKey}")
    String getAvgAllSidoAllProdPrice(@PathVariable String apiKey);

    @GetExchange("avgSidoPrice.do?out=json&code={apiKey}&sido={areaCd}")
    String getAvgSidoAllProdPrice(@PathVariable String apiKey, @PathVariable String areaCd);

    @GetExchange("avgSidoPrice.do?out=json&code={apiKey}&prodcd={prodcd}")
    String getAvgAllSidoProdPrice(@PathVariable String apiKey, @PathVariable String prodcd);

    @GetExchange("avgSidoPrice.do?out=json&code={apiKey}&sido={areaCd}&prodcd={prodcd}")
    String getAvgSidoProdPrice(@PathVariable String apiKey, @PathVariable String areaCd, @PathVariable String prodcd);

    //시군구별 주유소 평균가격
    //http://www.opinet.co.kr/api/avgSigunPrice.do?out=xml&sido=01&code=XXXXXX
    @GetExchange("avgSigunPrice.do?out=json&code={apiKey}&sido={sido}")
    String getAvgAllSigunAllProdPrice(@PathVariable String apiKey, @PathVariable String sido);

    //http://www.opinet.co.kr/api/areaCode.do?out=xml&code=XXXXXX
    @GetExchange("avgSigunPrice.do?out=json&code={apiKey}&sido={sido}&sigun={sigun}")
    String getAvgSigunAllProdPrice(@PathVariable String apiKey, @PathVariable String sido, @PathVariable String sigun);

    @GetExchange("avgSigunPrice.do?out=json&code={apiKey}&sido={sido}&prodcd={prodcd}")
    String getAvgAllSigunProdPrice(@PathVariable String apiKey, @PathVariable String sido, @PathVariable String prodcd);

    @GetExchange("avgSigunPrice.do?out=json&code={apiKey}&sido={sido}&sigun={sigun}&prodcd={prodcd}")
    String getAvgSigunProdPrice(@PathVariable String apiKey, @PathVariable String sido, @PathVariable String sigun, @PathVariable String prodcd);

    //최근 7일간 전국 일일 평균가격
    //http://www.opinet.co.kr/api/avgRecentPrice.do?out=xml&code=XXXXXX
    @GetExchange("avgRecentPrice.do?out=json&code={apiKey}")
    String getAvgRecent7DateAllProdPrice(@PathVariable String apiKey);

    @GetExchange("avgRecentPrice.do?out=json&code={apiKey}&date={date}")
    String getAvgRecentNDateAllProdPrice(@PathVariable String apiKey, @PathVariable String date);

    @GetExchange("avgRecentPrice.do?out=json&code={apiKey}&date={date}&prodcd={prodcd}")
    String getAvgRecentNDateProdPrice(@PathVariable String apiKey, @PathVariable String date, @PathVariable String prodcd);

    @GetExchange("avgRecentPrice.do?out=json&code={apiKey}&prodcd={prodcd}")
    String getAvgRecent7DateProdPrice(@PathVariable String apiKey, @PathVariable String prodcd);

    //최근 7일간 전국 일일 상표별 평균가격
    //http://www.opinet.co.kr/api/pollAvgRecentPrice.do?out=xml&code=XXXXXX&prodcd=B027
    @GetExchange("pollAvgRecentPrice.do?out=json&code={apiKey}")
    String getAllPollAvgRecent7DateAllProdPrice(@PathVariable String apiKey);

    @GetExchange("pollAvgRecentPrice.do?out=json&code={apiKey}&prodcd={prodcd}")
    String getAllPollAvgRecent7DateProdPrice(@PathVariable String apiKey, @PathVariable String prodcd);

    @GetExchange("pollAvgRecentPrice.do?out=json&code={apiKey}&prodcd={prodcd}&pollcd={pollcd}")
    String getPollAvgRecent7DateProdPrice(@PathVariable String apiKey, @PathVariable String prodcd, @PathVariable String pollcd);

    @GetExchange("pollAvgRecentPrice.do?out=json&code={apiKey}&pollcd={pollcd}")
    String getPollAvgRecent7DateAllProdPrice(@PathVariable String apiKey, @PathVariable String pollcd);

    //최근 7일간 전국 일일 지역별 평균가격
    //http://www.opinet.co.kr/api/areaAvgRecentPrice.do?out=xml&code=XXXXXX&area=0101&date=20220811
    @GetExchange("areaAvgRecentPrice.do?out=json&code={apiKey}&area={areaCd}")
    String getAreaAvgRecent7DateAllProdPrice(@PathVariable String apiKey, @PathVariable String areaCd);

    @GetExchange("areaAvgRecentPrice.do?out=json&code={apiKey}&area={areaCd}&date={date}")
    String getAreaAvgRecentNDateAllProdPrice(@PathVariable String apiKey, @PathVariable String areaCd, @PathVariable String date);

    @GetExchange("areaAvgRecentPrice.do?out=json&code={apiKey}&area={areaCd}&date={date}&prodcd={prodcd}")
    String getAreaAvgRecentNDateProdPrice(@PathVariable String apiKey, @PathVariable String areaCd, @PathVariable String date, @PathVariable String prodcd);

    @GetExchange("areaAvgRecentPrice.do?out=json&code={apiKey}&area={areaCd}&prodcd={prodcd}")
    String getAreaAvgRecent7DateProdPrice(@PathVariable String apiKey, @PathVariable String areaCd, @PathVariable String prodcd);

    //최근 1주의 주간 평균유가(전국/시도별)
    //http://www.opinet.co.kr/api/avgLastWeek.do?prodcd=B027&code=XXXXXX&out=xml
    @GetExchange("avgLastWeek.do?out=json&code={apiKey}")
    String getAllAreaAvgLastWeekAllProd(@PathVariable String apiKey);

    @GetExchange("avgLastWeek.do?out=json&code={apiKey}&prodcd={prodcd}")
    String getAllAreaAvgLastWeekProd(@PathVariable String apiKey, @PathVariable String prodcd);

    @GetExchange("avgLastWeek.do?out=json&code={apiKey}&prodcd={prodcd}&sido={sido}")
    String getAreaAvgLastWeekProd(@PathVariable String apiKey, @PathVariable String prodcd, @PathVariable String sido);

    @GetExchange("avgLastWeek.do?out=json&code={apiKey}&sido={sido}")
    String getAreaAvgLastWeekAllProd(@PathVariable String apiKey, @PathVariable String sido);

    //지역별 최저가 주유소(TOP20)
    //http://www.opinet.co.kr/api/lowTop10.do?out=xml&code=XXXXXX&prodcd=B027&area=0101&cnt=2
    @GetExchange("lowTop10.do?out=json&code={apiKey}&cnd=20&prodcd={prodcd}")
    String getAllAreaLowTop20ProdPrice(@PathVariable String apiKey, @PathVariable String prodcd);

    @GetExchange("lowTop10.do?out=json&code={apiKey}&cnd=20&prodcd={prodcd}&area={areaCd}")
    String getAreaLowTop20ProdPrice(@PathVariable String apiKey, @PathVariable String prodcd, @PathVariable String areaCd);

    //특정 위치 중심으로 반경 내 주유소
    //http://www.opinet.co.kr/api/aroundAll.do?code=XXXXXX&x=314681.8&y=544837&radius=5000&sort=1&prodcd=B027&out=xml
    @GetExchange("aroundAll.do?out=json&code={apiKey}&prodcd={prodcd}&x={x}&y={y}&radis={radius}&sort={sort}")
    String getAroundAll(@PathVariable String apiKey, @PathVariable String prodcd, @PathVariable String x, @PathVariable String y, @PathVariable String radius, @PathVariable String sort);


    /**
     * 오피넷 데이터 관련 지역코드
     * http://www.opinet.co.kr/api/areaCode.do?out=xml&code=XXXXXX&area=01
     * @param apiKey
     * @return AreaDto
     */
    @GetExchange("areaCode.do?out=json&code={apiKey}")
    String getSido(@PathVariable String apiKey);

    @GetExchange("areaCode.do?out=json&code={apiKey}&area={areaCd}")
    String getSigun(@PathVariable String apiKey, @PathVariable String areaCd);
}