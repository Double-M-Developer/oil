package com.pj.oil.gasStation.repository.redis;

import com.pj.oil.gasStation.entity.redis.AverageAllPriceRedis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 현재 오피넷에 게시되고 있는 전국 주유소 평균 가격
 */
public interface AverageAllPriceRedisRepository extends JpaRepository<AverageAllPriceRedis, String> {

    List<AverageAllPriceRedis> findByTradeDate(String tradeDate);
}
