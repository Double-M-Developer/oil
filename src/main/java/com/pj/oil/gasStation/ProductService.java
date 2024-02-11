package com.pj.oil.gasStation;




import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

//    private final ProductRepository productRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    //DB에_있는_제품_하나_조회
//    public Optional<Product> findProductByProductCode(String productCode) {
//        Optional<Product> entity = productRepository.findById(productCode);
//        if (entity.isEmpty()) {
//            LOGGER.info("[findProductByProductCode] product data dose not existed");
//        }
//        LOGGER.info("[findProductByProductCode] product data dose existed, product: {}", entity);
//        return entity;
//    }

    //DB에_있는_제품_모두_조회
//    public List<Product> findAllProduct() {
//        List<Product> entity = productRepository.findAll();
//        if (entity.isEmpty()) {
//            LOGGER.info("[findProductByProductCode] product data dose not existed");
//        }
//        LOGGER.info("[findProductByProductCode] product data dose existed, size: {}", entity.size());
//        return entity;
//    }

}
