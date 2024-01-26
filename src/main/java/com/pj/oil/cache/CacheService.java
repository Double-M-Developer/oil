package com.pj.oil.cache;

import com.pj.oil.gasStation.entity.ProductCode;
import com.pj.oil.gasStation.entity.maria.Area;
import com.pj.oil.gasStation.entity.maria.Product;
import com.pj.oil.gasStation.entity.redis.AreaRedis;
import com.pj.oil.gasStation.entity.redis.ProductRedis;
import com.pj.oil.gasStation.repository.jpa.AreaRepository;
import com.pj.oil.gasStation.repository.jpa.ProductRepository;
import com.pj.oil.gasStation.repository.redis.AreaRedisRepository;
import com.pj.oil.gasStation.repository.redis.ProductRedisRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final AreaRedisRepository areaRedisRepository; // area cache
    private final ProductRedisRepository productRedisRepository; // product cache
    private final AreaRepository areaRepository; // 데이터베이스 접근을 위한 JPA Repository
    private final ProductRepository productRepository; // 데이터베이스 접근을 위한 JPA Repository


    public Area getArea(String areaCode) {
        // 캐시에서 Area 찾기
        Optional<AreaRedis> areaRedis = areaRedisRepository.findById(areaCode);
        if (areaRedis.isPresent()) {
            return new Area(areaRedis.get().getAreaCode(),areaRedis.get().getAreaName());
        }

        // 캐시에 없다면 데이터베이스에서 찾고 캐시에 추가
        Optional<Area> area = areaRepository.findById(areaCode);
        if (area.isPresent()) {
            areaRedisRepository.save(AreaRedis.builder()
                            .areaCode(areaCode)
                            .areaName(area.get().getAreaName())
                            .build());
        }
        return area.get();
    }

    public Product getProduct(String productCodeStr) {
        // 캐시에서 Product 찾기
        Optional<ProductRedis> productRedis = productRedisRepository.findById(productCodeStr);
        if (productRedis.isPresent()) {
            // 문자열 이름으로부터 enum 상수 참조를 가져옵니다.
            ProductCode productCodeEnum = ProductCode.valueOf(productCodeStr);
            return new Product(productCodeEnum);
        }

        // 캐시에 없다면 데이터베이스에서 찾고 캐시에 추가
        Optional<Product> product = productRepository.findById(productCodeStr);
        if (product.isPresent()) {
            Product productEntity = product.get();
            productRedisRepository.save(new ProductRedis(productCodeStr, productEntity.getProductCode().getTitle()));
            return productEntity;
        }

        throw new EntityNotFoundException("Product not found with code: " + productCodeStr);
    }
}