package com.pj.oil.gasStation.repository.maria;

import com.pj.oil.gasStation.entity.maria.LowTop20Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 전국 또는 지역별 최저가 주유소 TOP20
 */
@Repository
public interface LowTop20PriceRepository extends JpaRepository<LowTop20Price, Long> {

    @Query("SELECT l FROM LowTop20Price l WHERE l.areaCode = :areaCode AND l.productCode = :productCode ORDER BY l.priceCurrent ASC")
    List<LowTop20Price> findLowTop20PriceByAreaCodeAndProductCode(@Param("areaCode") String areaCode, @Param("productCode") String productCode);

}
