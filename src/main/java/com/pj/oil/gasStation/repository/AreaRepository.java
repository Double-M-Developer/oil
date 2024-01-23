package com.pj.oil.gasStation.repository;

import com.pj.oil.gasStation.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * db에 저장된 지역 정보
 */
@Repository
public interface AreaRepository extends JpaRepository<Area, String> {
}
