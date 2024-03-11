package com.pj.oil.gasStation.repository;

import com.pj.oil.gasStation.dto.AreaAverageRecentPriceDto;
import com.pj.oil.gasStation.entity.AreaAverageRecentPrice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 일 평균가격 확정 수치이며, 전일부터 7일간의 전국 일일 지역별 평균가격
 */
@Repository
public interface AreaAverageRecentPriceRepository extends JpaRepository<AreaAverageRecentPrice, Long> {

        @Query("SELECT new com.pj.oil.gasStation.dto.AreaAverageRecentPriceDto(" +
                "a.id, a.baseDate, a.areaCode.AreaCode, a.productCode.productCode, a.priceAverage) " +
                "FROM AreaAverageRecentPrice a " +
                "WHERE a.areaCode = :areaCode " +
                "AND a.productCode = :productCode " +
                "AND a.baseDate " +
                "BETWEEN :sevenDaysBefore AND :yesterday")
        List<AreaAverageRecentPriceDto> findByLastSevenDays(String sevenDaysBefore, String yesterday, String areaCode, String productCode);

        @Modifying
        @Query("DELETE FROM AreaAverageRecentPrice a WHERE a.baseDate = :baseDate")
        void deleteByBaseDate(@Param("baseDate")String baseDate);
}
