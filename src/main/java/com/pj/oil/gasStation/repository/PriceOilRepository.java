package com.pj.oil.gasStation.repository;

import com.pj.oil.gasStation.entity.PriceOil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PriceOilRepository extends JpaRepository<PriceOil, Long> {

    @Modifying
    @Query("DELETE FROM PriceOilDto")
    void deleteAllData();

    @Query("SELECT AVG(p.preGasoline) FROM PriceOilDto p")
    int findAveragePreGasoline();

    @Query("SELECT AVG(p.gasoline) FROM PriceOilDto p")
    int findAverageGasoline();

    @Query("SELECT AVG(p.diesel) FROM PriceOilDto p")
    int findAverageDiesel();

}
