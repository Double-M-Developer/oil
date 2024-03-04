package com.pj.oil.gasStation.repository.redis;

import com.pj.oil.gasStation.entity.redis.AreaAverageRecentPriceRedis;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 일 평균가격 확정 수치이며, 전일부터 7일간의 전국 일일 지역별 평균가격
 */
public interface AreaAverageRecentPriceRedisRepository extends CrudRepository<AreaAverageRecentPriceRedis, Long> {
        List<AreaAverageRecentPriceRedis> findByBaseDate(String baseDate);
        List<AreaAverageRecentPriceRedis> findByAreaCodeAndProductCode(String areaCode, String productCode);
}
