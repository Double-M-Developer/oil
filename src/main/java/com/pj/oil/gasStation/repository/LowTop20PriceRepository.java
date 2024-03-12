package com.pj.oil.gasStation.repository;

import com.pj.oil.gasStation.dto.LowTop20PriceDto;
import com.pj.oil.gasStation.entity.LowTop20Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 전국 또는 지역별 최저가 주유소 TOP20
 */
@Repository
public interface LowTop20PriceRepository extends JpaRepository<LowTop20Price, Long> {

    @Modifying
    @Query("DELETE FROM LowTop20Price")
    void deleteAllData();

    @Query("SELECT new com.pj.oil.gasStation.dto.LowTop20PriceDto(" +
            "l.uniId, l.priceCurrent, l.pollDivCode, l.osName, l.vanAddress, l.newAddress, l.gisXCoor, l.gisYCoor, l.productCode.productCode, l.areaCode.areaCode) " +
            "FROM LowTop20Price l " +
            "JOIN FETCH l.areaCode ac " +
            "JOIN FETCH l.productCode pc " +
            "WHERE ac.areaCode = :areaCode " +
            "AND pc.productCode = :productCode " +
            "ORDER BY l.priceCurrent ASC")
    List<LowTop20PriceDto> findLowTop20PriceByAreaCodeAndProductCode(@Param("areaCode") String areaCode, @Param("productCode") String productCode);

}
