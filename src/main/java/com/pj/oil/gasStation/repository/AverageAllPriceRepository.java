package com.pj.oil.gasStation.repository;

import com.pj.oil.gasStation.dto.AverageAllPriceDto;
import com.pj.oil.gasStation.entity.AverageAllPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 현재 오피넷에 게시되고 있는 전국 주유소 평균 가격
 */
@Repository
public interface AverageAllPriceRepository extends JpaRepository<AverageAllPrice, Long> {

    @Query("SELECT new com.pj.oil.gasStation.dto.AverageAllPriceDto(" +
            "a.id, a.tradeDate, a.productCode.productCode, a.priceAverage, a.priceChange) " +
            "FROM AverageAllPrice a " +
            "JOIN FETCH a.productCode pc " +
            "WHERE a.tradeDate = :tradeDate")
    List<AverageAllPriceDto> findByTradeDate(String tradeDate);

    @Modifying
    @Query("DELETE FROM AverageAllPrice a WHERE a.tradeDate = :tradeDate")
    void deleteByTradeDate(@Param("tradeDate") String tradeDate);
}
