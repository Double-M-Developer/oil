package com.pj.oil.gasStation.repository.jpa;

import com.pj.oil.gasStation.entity.maria.Area;
import com.pj.oil.gasStation.entity.maria.LowTop20Price;
import com.pj.oil.gasStation.entity.maria.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 전국 또는 지역별 최저가 주유소 TOP20
 */
@Repository
public interface LowTop20PriceRepository extends JpaRepository<LowTop20Price, Long> {

    List<LowTop20Price> findLowTop20PriceByAreaAndProduct(Optional<Area> area, Optional<Product> product);
}
