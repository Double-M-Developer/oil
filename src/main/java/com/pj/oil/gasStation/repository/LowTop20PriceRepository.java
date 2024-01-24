package com.pj.oil.gasStation.repository;

import com.pj.oil.gasStation.entity.*;
import com.pj.oil.gasStationApi.dto.ApiBaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 전국 또는 지역별 최저가 주유소 TOP20
 */
@Repository
public interface LowTop20PriceRepository extends JpaRepository<LowTop20Price, Long> {

    List<LowTop20Price> findLowTop20PriceByAreaAndProduct(Optional<Area> area, Optional<Product> product);
}
