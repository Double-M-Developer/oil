package com.pj.oil.gasStation.repository;

import com.pj.oil.gasStation.entity.Area;
import com.pj.oil.gasStation.entity.GasStation;
import com.pj.oil.memberPost.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GasStationRepository extends JpaRepository<GasStation, Long> {

    @Query(value = "SELECT * FROM gas_station g INNER JOIN price_oil po ON g.price_oil = po.id ORDER BY po.pre_gasoline ASC LIMIT 5", nativeQuery = true)
    List<GasStation> findTop5ByCheapestPreGasoline();

    @Query(value = "SELECT * FROM gas_station g INNER JOIN price_oil po ON g.price_oil = po.id ORDER BY po.gasoline ASC LIMIT 5", nativeQuery = true)
    List<GasStation> findTop5ByCheapestGasoline();

    @Query(value = "SELECT * FROM gas_station g INNER JOIN price_oil po ON g.price_oil = po.id ORDER BY po.diesel ASC LIMIT 5", nativeQuery = true)
    List<GasStation> findTop5ByCheapestDiesel();
}
