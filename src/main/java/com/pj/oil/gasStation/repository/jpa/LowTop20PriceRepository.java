package com.pj.oil.gasStation.repository.jpa;

import com.pj.oil.gasStation.entity.maria.LowTop20Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 전국 또는 지역별 최저가 주유소 TOP20
 */
@Repository
public interface LowTop20PriceRepository extends JpaRepository<LowTop20Price, Long> {

    List<LowTop20Price> findLowTop20PriceByAreaAndProduct(String area, String product);

}
