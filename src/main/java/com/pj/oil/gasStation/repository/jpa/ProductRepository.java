package com.pj.oil.gasStation.repository.jpa;

import com.pj.oil.gasStation.entity.maria.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * db에 저장된 지역 정보
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}
