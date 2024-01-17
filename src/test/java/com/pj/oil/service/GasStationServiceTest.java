package com.pj.oil.service;

import com.pj.oil.entity.GasStation;
import com.pj.oil.repository.GasStationRepository;
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

    @Autowired
    GasStationService gasStationService;
    @Autowired
    GasStationRepository gasStationRepository;

    @BeforeEach
    void init() {

    }

    @Test
    void 주유소_전체_조회() {
        List<GasStation> gasStations = gasStationService.findAllGasStations();
        assertFalse(gasStations.isEmpty());
    }

    @Test
    void 주유소_코드로_주유소_조회() {
        Optional<GasStation> gasStations = gasStationService.findByUniId("UNI_ID_1");
        //then
        gasStations.ifPresent(value -> assertEquals("UNI_ID_1", value.getUniId()));
    }

    @Test
    void 주유소_제품으로_주유소_조회() {
        List<GasStation> gasStations = gasStationService.findByProdcd("B027");
        assertFalse(gasStations.isEmpty());
    }

    @Test
    void 시구군으로_주유소_조회() {
        List<GasStation> gasStations = gasStationService.findByArea("서울");
        assertFalse(gasStations.isEmpty());
    }

    @Test
    void 주유소_좌표로_주유소_조회() {
        Optional<GasStation> gasStations = gasStationService.findByGisCoor(37.4979, 127.0276);
        //then
        gasStations.ifPresent(value -> assertEquals("UNI_ID_1", value.getUniId()));
    }

    @Test
    void 주유소_상표로_주유소_조회() {
        List<GasStation> gasStations = gasStationService.findByPollDivCd("SKE");
        //then
        assertFalse(gasStations.isEmpty());
    }

    @Test
    void 주유소_상호로_주유소_조회() {
        Optional<GasStation> gasStations = gasStationService.findByOsNm("자영알뜰");
        //then
        gasStations.ifPresent(value -> assertEquals("UNI_ID_5", value.getUniId()));
    }

    @Test
    void 업데이트_시간으로_주유소_검색() {
        List<GasStation> gasStations = gasStationService.findByUpdatedAt("2024-01-17");
        //then
        assertFalse(gasStations.isEmpty());
    }
}