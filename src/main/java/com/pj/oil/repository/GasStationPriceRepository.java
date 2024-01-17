package com.pj.oil.repository;

import com.pj.oil.entity.GasStationPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GasStationPriceRepository extends JpaRepository<GasStationPrice, Long> {

    List<GasStationPrice> findByProdcd(String prodcd);
    List<GasStationPrice> findByUpdatedAt(String updatedAt);


}
