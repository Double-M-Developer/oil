package com.pj.oil.gasStation.repository;

import com.pj.oil.gasStation.entity.Area;
import com.pj.oil.gasStation.entity.AverageSidoPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 현재 오피넷에 게시되고 있는 시도별 주유소 평균 가격
 */
@Repository
public interface AverageSidoPriceRepository extends JpaRepository<AverageSidoPrice, Long> {

    List<AverageSidoPrice> findByArea(Optional<Area> area);
}