package com.pj.oil.gasStation.repository;

import com.pj.oil.gasStation.entity.PriceLpg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PriceLpgRepository extends JpaRepository<PriceLpg, Long> {

    @Query("SELECT AVG(p.lpg) FROM PriceLpg p")
    int findAverageLpg();
}