package com.pj.oil.gasStation.repository.maria;

import com.pj.oil.gasStation.entity.maria.PriceLpg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PriceLpgRepository extends JpaRepository<PriceLpg, Long> {

    @Query("SELECT AVG(p.lpg) FROM PriceLpg p")
    int findAverageLpg();
}
