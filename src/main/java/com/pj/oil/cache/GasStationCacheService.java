package com.pj.oil.cache;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class GasStationCacheService {


//    public Area getArea(String areaCode) {
//        // 캐시에서 Area 찾기
//        Optional<AreaRedis> areaRedis = areaRedisRepository.findById(areaCode);
//        if (areaRedis.isPresent()) {
//            return new Area(areaRedis.get().getAreaCode(),areaRedis.get().getAreaName());
//        }
//
//        // 캐시에 없다면 데이터베이스에서 찾고 캐시에 추가
//        Optional<Area> area = areaRepository.findById(areaCode);
//        if (area.isPresent()) {
//            areaRedisRepository.save(AreaRedis.builder()
//                            .areaCode(areaCode)
//                            .areaName(area.get().getAreaName())
//                            .build());
//        }
//        return area.get();
//    }
//
//    public Product getProduct(String productCodeStr) {
//        // 캐시에서 Product 찾기
//        Optional<ProductRedis> productRedis = productRedisRepository.findById(productCodeStr);
//        if (productRedis.isPresent()) {
//            // 문자열 이름으로부터 enum 상수 참조를 가져옵니다.
//            ProductCode productCodeEnum = ProductCode.valueOf(productCodeStr);
//            return new Product(productCodeEnum);
//        }
//
//        // 문자열로부터 enum 상수를 가져옵니다.
//        ProductCode productCodeEnum = ProductCode.valueOf(productCodeStr);
//
//        // 캐시에 없다면 데이터베이스에서 찾고 캐시에 추가
//        Optional<Product> product = productRepository.findById(productCodeEnum); // 이 줄을 수정
//        if (product.isPresent()) {
//            Product productEntity = product.get();
//            productRedisRepository.save(new ProductRedis(productCodeStr, productEntity.getProductCode().getTitle()));
//            return productEntity;
//        }
//
//        throw new EntityNotFoundException("Product not found with code: " + productCodeStr);
//    }
//
//
//    public void saveAllArea(List<AreaRedis> areaRedisEntities) {
//        areaRedisRepository.saveAll(areaRedisEntities);
//    }
//
//    public void saveAllProduct(List<ProductRedis> productRedisEntities) {
//        productRedisRepository.saveAll(productRedisEntities);
//    }
}