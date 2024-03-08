package com.pj.oil.gasStation.repository;

import com.pj.oil.gasStation.entity.PriceLpg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PriceLpgRepository extends JpaRepository<PriceLpg, Long> {

    @Modifying
    @Query("DELETE FROM PriceLpgDto")
    void deleteAllData();

    @Query("SELECT AVG(p.lpg) FROM PriceLpgDto p")
    int findAverageLpg();
}
