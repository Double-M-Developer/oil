//package com.pj.oil.batch.process;
//
//import com.pj.oil.cache.CacheService;
//import com.pj.oil.gasStation.entity.maria.Area;
//import com.pj.oil.gasStation.entity.maria.AreaAverageRecentPrice;
//import com.pj.oil.gasStation.entity.maria.Product;
//import com.pj.oil.gasStationApi.dto.AreaAverageRecentPriceDto;
//import org.springframework.batch.item.ItemProcessor;
//
//
//public class LowTop20PriceProcess implements ItemProcessor<AreaAverageRecentPriceDto, AreaAverageRecentPrice>{
//
//    private final CacheService cacheService;
//
//    public LowTop20PriceProcess(CacheService cacheService) {
//        this.cacheService = cacheService;
//    }
//
//    @Override
//    public AreaAverageRecentPrice process(AreaAverageRecentPriceDto item) {
//        // CacheService를 사용하여 Area와 Product 찾기
//        Area area = cacheService.getArea(item.getAreaCode());
//        Product product = cacheService.getProduct(item.getProductCode());
//
//        // DTO를 Entity로 변환
//        return AreaAverageRecentPrice.builder()
//                .baseDate(item.getBaseDate())
//                .area(area)
//                .product(product)
//                .averagePrice(item.getAveragePrice())
//                .build();
//    }
//}
