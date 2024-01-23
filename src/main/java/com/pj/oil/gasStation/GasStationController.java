package com.pj.oil.gasStation;

import com.pj.oil.gasStation.entity.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gas-station")
public class GasStationController {

    private final GasStationService gasStationService;
//
//    /**
//     * 일 평균가격 확정 수치이며, 전일부터 7일간의 전국 일일 지역별 평균가격
//     * @param baseDate
//     * @return
//     */
//    @GetMapping("/area-average-recent-price")
//    public List<AreaAverageRecentPrice> findAreaAverageRecentPriceByBaseDate(String baseDate) {
//        List<AreaAverageRecentPrice> entity = gasStationService.findAreaAverageRecentPriceByBaseDate(baseDate);
////        if (entity.isEmpty()) {
////            LOGGER.info("[findAreaAverageRecentPrice] AreaAverageRecentPrice data dose not existed");
////        }
////        LOGGER.info("[findAreaAverageRecentPrice] AreaAverageRecentPrice data dose existed, AreaAverageRecentPrice size: {}", entity.size());
//        return entity;
//    }
//
//    /**
//     * 현재 오피넷에 게시되고 있는 전국 주유소 평균 가격
//     * @param tradeDate
//     * @return
//     */
//    @GetMapping("/area-average-all-price")
//    public Optional<AverageAllPrice> findAverageAllPriceByTradeDate(String tradeDate) {
//        Optional<AverageAllPrice> entity = gasStationService.findAverageAllPriceByTradeDate(tradeDate);
////        if (entity.isEmpty()) {
////            LOGGER.info("[findAverageAllPriceByTradeDate] AverageAllPrice data dose not existed");
////        }
////        LOGGER.info("[findAverageAllPriceByTradeDate] AverageAllPrice data dose existed, AverageAllPrice entity: {}", entity);
//        return entity;
//    }
//
//    /**
//     * 현재 오피넷에 게시되고 있는 시도별 주유소 평균 가격
//     * @param areaCode
//     * @return
//     */
//    public List<AverageSidoPrice> findAverageSidoPriceByAreaCode(String areaCode) {
//        List<AverageSidoPrice> entity = gasStationService.findAverageSidoPriceByAreaCode(areaCode);
////        if (entity.isEmpty()) {
////            LOGGER.info("[findAverageSidoPriceByAreaCode] AverageSidoPrice data dose not existed");
////        }
////        LOGGER.info("[findAverageSidoPriceByAreaCode] AverageSidoPrice data dose existed, AverageSidoPrice size: {}", entity.size());
//        return entity;
//    }
//
//    /**
//     * 전국 또는 지역별 최저가 주유소 TOP20
//     * @param areaCode
//     * @return
//     */
//    public List<LowTop20Price> findLowTop20PriceByAreaCode(String areaCode) {
//        List<LowTop20Price> entity = gasStationService.findLowTop20PriceByAreaCode(areaCode);
////        if (entity.isEmpty()) {
////            LOGGER.info("[findLowTop20PriceByAreaCode] LowTop20Price data dose not existed");
////        }
////        LOGGER.info("[findLowTop20PriceByAreaCode] LowTop20Price data dose existed, LowTop20Price size: {}", entity.size());
//        return entity;
//    }

}
