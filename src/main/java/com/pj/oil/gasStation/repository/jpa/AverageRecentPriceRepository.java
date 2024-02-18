package com.pj.oil.gasStation.repository.jpa;

import com.pj.oil.gasStation.entity.maria.AverageRecentPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 일 평균가격 확정 수치이며, 전일부터 7일간의 전국 일일 지역별 평균가격
 */
@Repository
public interface AverageRecentPriceRepository extends JpaRepository<AverageRecentPrice, Long> {
        List<AverageRecentPrice> findByDate(String date);
        void deleteByDate(String date);

        @Query("SELECT a FROM AverageRecentPrice a WHERE a.productCode = :productCode AND a.date BETWEEN :sevenDaysBefore AND :yesterday")
        List<AverageRecentPrice> findByLastSevenDays(String sevenDaysBefore, String yesterday, String productCode);
}
