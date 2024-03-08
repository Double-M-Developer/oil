package com.pj.oil.gasStation.repository;

import com.pj.oil.gasStation.entity.PriceOil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PriceOilRepository extends JpaRepository<PriceOil, Long> {

    @Modifying
    @Query("DELETE FROM PriceOil")
    void deleteAllData();

    @Query("SELECT AVG(p.preGasoline) FROM PriceOil p")
    int findAveragePreGasoline();

    @Query("SELECT AVG(p.gasoline) FROM PriceOil p")
    int findAverageGasoline();

    @Query("SELECT AVG(p.diesel) FROM PriceOil p")
    int findAverageDiesel();

}
