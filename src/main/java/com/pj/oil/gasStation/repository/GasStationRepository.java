package com.pj.oil.gasStation.repository;

import com.pj.oil.gasStation.entity.GasStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GasStationRepository extends JpaRepository<GasStation, Long> {
}
