package com.pj.oil.cache.gasStation.repository;

import com.pj.oil.cache.gasStation.entity.LowTop20PriceRedis;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 전국 또는 지역별 최저가 주유소 TOP20
 */
public interface LowTop20PriceRedisRepository extends CrudRepository<LowTop20PriceRedis, String> {

    List<LowTop20PriceRedis> findByAreaCodeAndProductCode(String areaCode, String productCode);

}
