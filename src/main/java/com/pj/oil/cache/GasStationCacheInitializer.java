package com.pj.oil.cache;

import com.pj.oil.gasStation.AreaService;
import com.pj.oil.gasStation.ProductService;
import com.pj.oil.gasStation.entity.maria.Area;
import com.pj.oil.gasStation.entity.maria.Product;
import com.pj.oil.gasStation.entity.redis.AreaRedis;
import com.pj.oil.gasStation.entity.redis.ProductRedis;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 애플리케이션 가동 시 사용할 캐시 초기화
 */
@Component
@RequiredArgsConstructor
public class GasStationCacheInitializer implements CommandLineRunner {

    private final AreaService areaService;
    private final ProductService productService;
    private final GasStationCacheService gasStationCacheService;

    @Override
    public void run(String... args) throws Exception {
        initializeCache();
    }

    @Async
    public void initializeCache() {
        // Area 객체를 Redis에 캐싱
        List<Area> areas = areaService.findAllArea();
        List<AreaRedis> areaRedisEntities = areaService.toRedisEntities(areas);
        gasStationCacheService.saveAllArea(areaRedisEntities);

        // Product 객체를 Redis에 캐싱
        List<Product> products = productService.findAllProduct();
        List<ProductRedis> productRedisEntities = productService.toRedisEntities(products);
        gasStationCacheService.saveAllProduct(productRedisEntities);
    }
}
