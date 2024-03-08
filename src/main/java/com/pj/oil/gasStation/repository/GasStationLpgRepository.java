package com.pj.oil.gasStation.repository;

import com.pj.oil.gasStation.entity.GasStationLpg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GasStationLpgRepository extends JpaRepository<GasStationLpg, String> {
}
