package com.pj.oil.service;

import com.pj.oil.gasStation.*;
import com.pj.oil.gasStation.entity.PollDivCode;
import com.pj.oil.gasStation.entity.ProductCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class GasStationServiceTest {

//    public static void printGasStationList(List<GasStation> list) {
//        for (GasStation o : list) {
//            System.out.println(o.toString());
//        }
//    }
//
//    public static void printGasStationPriceDtoList(List<GasStationPriceDto> list) {
//        for (GasStationPriceDto o : list) {
//            System.out.println(o.toString());
//        }
//    }
//
//    public static void printGasStation(GasStation object) {
//        System.out.println(object.toString());
//    }
//
//    @Autowired
//    GasStationService gasStationService;
//    @Autowired
//    GasStationRepository gasStationRepository;
//
//    @BeforeEach
//    void init() {
//
//    }
//
//    @Test
//    void 주유소_전체_및_가격조회() {
//        List<GasStation> gasStations = gasStationService.findAllGasStations();
//        printGasStationList(gasStations);
//        assertFalse(gasStations.isEmpty());
//    }
//
//    @Test
//    void 주유소_코드에_해당하는_주유소_조회() {
//        Optional<GasStation> gasStations = gasStationService.findByUniId("UNI_ID_1");
//        //then
//        printGasStation(gasStations.get());
//        gasStations.ifPresent(value -> assertEquals("UNI_ID_1", value.getUniId()));
//    }
//
//    @Test
//    void 주유소_제품에_해당하는_주유소_가격_조회() {
//        List<GasStationPriceDto> gasStations = gasStationService.findByProdcd(ProductCode.B027);
//        printGasStationPriceDtoList(gasStations);
//        assertFalse(gasStations.isEmpty());
//    }
//
//    @Test
//    void 시구군에_해당하는_주유소_전체_조회() {
//        List<GasStation> gasStations = gasStationService.findByArea("01");
//        printGasStationList(gasStations);
//        assertFalse(gasStations.isEmpty());
//    }
//
//    @Test
//    void 주유소_좌표에_해당하는_주유소_조회() {
//        Optional<GasStation> gasStations = gasStationService.findByGisCoor(37.4979, 127.0276);
//        //then
//        printGasStation(gasStations.get());
//        gasStations.ifPresent(value -> assertEquals("UNI_ID_1", value.getUniId()));
//    }
//
//    @Test
//    void 주유소_상표에_해당하는_주유소_조회() {
//        List<GasStation> gasStations = gasStationService.findByPollDivCd(PollDivCode.SKE);
//        //then
//        printGasStationList(gasStations);
//        assertFalse(gasStations.isEmpty());
//    }
//
//    @Test
//    void 주유소_상호에_해당하는_주유소_조회() {
//        Optional<GasStation> gasStations = gasStationService.findByOsNm("자영알뜰");
//        //then
//        printGasStation(gasStations.get());
//        gasStations.ifPresent(value -> assertEquals("UNI_ID_5", value.getUniId()));
//    }
//
//    @Test
//    void 가장_최근의_주유소_가격_정보_전체_조회() {
//        List<GasStationPriceDto> gasStations = gasStationService.findLatestPrices();
//        printGasStationPriceDtoList(gasStations);
//        assertFalse(gasStations.isEmpty());
//    }

}