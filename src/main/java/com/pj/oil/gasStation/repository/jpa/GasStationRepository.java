package com.pj.oil.gasStation.repository.jpa;

import com.pj.oil.gasStation.entity.maria.GasStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GasStationRepository extends JpaRepository<GasStation, Long> {

    // 실제 구현에는 적절한 조인 조건과 엔티티 관계를 반영해야 합니다.
    @Query("SELECT g FROM GasStation g JOIN PriceOil po ON g.id = po.uniId ORDER BY po.preGasoline ASC")
    List<GasStation> findTop5ByPreGasolinePrice();

    @Query("SELECT g FROM GasStation g JOIN PriceOil po ON g.id = po.uniId ORDER BY po.gasoline ASC")
    List<GasStation> findTop5ByGasolinePrice();

    @Query("SELECT g FROM GasStation g JOIN PriceOil po ON g.id = po.uniId ORDER BY po.diesel ASC")
    List<GasStation> findTop5ByDieselPrice();

    @Query("SELECT g FROM GasStation g JOIN PriceLpg po ON g.id = po.uniId ORDER BY po.lpg ASC")
    List<GasStation> findTop5ByLpgPrice();

}
