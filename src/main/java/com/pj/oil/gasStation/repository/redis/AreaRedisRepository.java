package com.pj.oil.gasStation.repository.redis;

import com.pj.oil.gasStation.entity.redis.AreaRedis;
import org.springframework.data.repository.CrudRepository;

public interface AreaRedisRepository extends CrudRepository<AreaRedis, String> {
}
