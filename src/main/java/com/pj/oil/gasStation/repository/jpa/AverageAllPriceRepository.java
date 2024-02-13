package com.pj.oil.gasStation.repository.jpa;

import com.pj.oil.gasStation.entity.maria.AverageAllPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 현재 오피넷에 게시되고 있는 전국 주유소 평균 가격
 */
@Repository
public interface AverageAllPriceRepository extends JpaRepository<AverageAllPrice, Long> {

    List<AverageAllPrice> findByTradeDate(String tradeDate);
}