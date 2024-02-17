package com.pj.oil.gasStation.repository.jpa;

import com.pj.oil.gasStation.entity.maria.AreaAverageRecentPrice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 일 평균가격 확정 수치이며, 전일부터 7일간의 전국 일일 지역별 평균가격
 */
@Repository
public interface AreaAverageRecentPriceRepository extends JpaRepository<AreaAverageRecentPrice, Long> {
        List<AreaAverageRecentPrice> findByBaseDate(String baseDate);
        void deleteByBaseDate(String baseDate);
}
