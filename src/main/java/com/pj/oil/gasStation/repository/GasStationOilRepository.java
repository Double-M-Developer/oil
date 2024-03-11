package com.pj.oil.gasStation.repository;

import com.pj.oil.gasStation.entity.GasStationOil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface GasStationOilRepository extends JpaRepository<GasStationOil, String> {
        // 실제 구현에는 적절한 조인 조건과 엔티티 관계를 반영해야 합니다.
    @Query("SELECT g.osName FROM GasStationOilDto g JOIN PriceOil po ON g.uniId = po.uniId ORDER BY po.preGasoline ASC")
    List<String> findTopBrandsByPreGasolinePrice(Pageable pageable);

    @Query("SELECT g.osName FROM GasStationOilDto g JOIN PriceOil po ON g.uniId = po.uniId ORDER BY po.gasoline ASC")
    List<String> findTopBrandsByGasolinePrice(Pageable pageable);

    @Query("SELECT g.osName FROM GasStationOilDto g JOIN PriceOil po ON g.uniId = po.uniId ORDER BY po.diesel ASC")
    List<String> findTopBrandsByDieselPrice(Pageable pageable);

    @Query("SELECT g.osName FROM GasStationOilDto g JOIN PriceLpg po ON g.uniId = po.uniId ORDER BY po.lpg ASC")
    List<String> findTopBrandsByLpgPrice(Pageable pageable);
}
