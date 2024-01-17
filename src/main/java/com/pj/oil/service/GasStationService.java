package com.pj.oil.service;

import com.pj.oil.dto.GasStationPriceDto;
import com.pj.oil.entity.GasStation;
import com.pj.oil.entity.PollDivCode;
import com.pj.oil.entity.ProductCode;
import com.pj.oil.repository.GasStationRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GasStationService {

    private final GasStationRepository gasStationRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(GasStationService.class);

    //주유소_전체_및_가격_조회
    public List<GasStation> findAllGasStations() {
        List<GasStation> findGasStations = gasStationRepository.findAll();
        if (findGasStations.isEmpty()) {
            LOGGER.info("[findAllGasStations] gas station data dose not existed");
        }
        LOGGER.info("[findAllGasStations] gas station data dose existed, gas station size: {}", findGasStations.size());
        return findGasStations;
    }

    //주유소_코드에_해당하는_주유소_조회
    public Optional<GasStation> findByUniId(String uniId) {
        Optional<GasStation> findGasStations = gasStationRepository.findByUniId(uniId);
        if (findGasStations.isPresent()) {
            LOGGER.info("[findByUniId] gas station data dose existed : {}", findGasStations);
        }
        LOGGER.info("[findByUniId] gas station data dose not existed, UNI_ID: {}", uniId);
        return findGasStations;
    }

    //주유소_제품에_해당하는_주유소_가격_조회 휘발유:B027, 경유:D047, 고급휘발유: B034, 실내등유:C004, 자동차부탄: K015
    public List<GasStationPriceDto> findByProdcd(ProductCode prodcd) {
        List<GasStationPriceDto> findGasStations = gasStationRepository.findByProdcd(prodcd.getKey());
        if (findGasStations.isEmpty()) {
            LOGGER.info("[findByProdcd] gas station data dose not existed");
        }
        LOGGER.info("[findByProdcd] gas station data dose existed, gas station size: {}", findGasStations.size());
        return findGasStations;
    }

    //시구군에_해당하는_주유소_전체_조회
    public List<GasStation> findByArea(String areaCd) {
        List<GasStation> findGasStations = gasStationRepository.findByArea(areaCd);
        if (findGasStations.isEmpty()) {
            LOGGER.info("[findByArea] gas station data dose not existed");
        }
        LOGGER.info("[findByArea] gas station data dose existed, gas station size: {}", findGasStations.size());
        return findGasStations;
    }

    //주유소_좌표에_해당하는_주유소_조회
    public Optional<GasStation> findByGisCoor(double gisXCoor, double gisYCoor) {
        Optional<GasStation> findGasStations = gasStationRepository.findByGisXCoorAndGisYCoor(gisXCoor, gisYCoor);
        if (findGasStations.isPresent()) {
            LOGGER.info("[findByGisCoor] gas station data dose existed : {}", findGasStations);
        }
        LOGGER.info("[findByGisCoor] gas station data dose not existed, GIS_X_COOR: {}, GIS_Y_COOR: {}", gisXCoor, gisYCoor);
        return findGasStations;
    }

    //주유소_상표에_해당하는_주유소_조회
    public List<GasStation> findByPollDivCd(PollDivCode pollDivCd) {
        List<GasStation> findGasStations = gasStationRepository.findByPollDivCd(pollDivCd.getKey());
        if (findGasStations.isEmpty()) {
            LOGGER.info("[findByPollDivCd] gas station data dose not existed");
        }
        LOGGER.info("[findByPollDivCd] gas station data dose existed, gas station size: {}", findGasStations.size());
        return findGasStations;
    }

    //주유소_상호에_해당하는_주유소_조회
    public Optional<GasStation> findByOsNm(String osNm) {
        Optional<GasStation> findGasStations = gasStationRepository.findByOsNm(osNm);
        if (findGasStations.isPresent()) {
            LOGGER.info("[findByOsNm] gas station data dose existed : {}", findGasStations);
        }
        LOGGER.info("[findByOsNm] gas station data dose not existed, OS_NM: {}", osNm);
        return findGasStations;
    }

    //가장_최근의_주유소_가격_정보_전체_조회
    public List<GasStationPriceDto> findLatestPrices() {
        List<GasStationPriceDto> findGasStations = gasStationRepository.findLatestPrices();
        if (findGasStations.isEmpty()) {
            LOGGER.info("[findLatestPrices] gas station data dose not existed");
        }
        LOGGER.info("[findLatestPrices] gas station data dose existed, gas station size: {}", findGasStations.size());
        return findGasStations;
    }

}
