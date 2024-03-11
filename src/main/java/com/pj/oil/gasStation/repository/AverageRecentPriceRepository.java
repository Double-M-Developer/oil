package com.pj.oil.gasStation.repository;

import com.pj.oil.gasStation.dto.AverageRecentPriceDto;
import com.pj.oil.gasStation.entity.AverageRecentPrice;
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
public interface AverageRecentPriceRepository extends JpaRepository<AverageRecentPrice, Long> {
        List<AverageRecentPrice> findByDate(String date);

        @Modifying
        @Query("DELETE FROM AverageRecentPrice a WHERE a.date = :date")
        void deleteByDate(@Param("date") String date);

        @Query("SELECT new com.pj.oil.gasStation.dto.AverageRecentPriceDto(" +
                "a.id, a.date, a.productCode.productCode, a.priceAverage) " +
                "FROM AverageRecentPrice a " +
                "WHERE a.productCode = :productCode " +
                "AND a.date " +
                "BETWEEN :sevenDaysBefore AND :yesterday")
        List<AverageRecentPriceDto> findByLastSevenDays(String sevenDaysBefore, String yesterday, String productCode);
}
