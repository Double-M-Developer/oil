package com.pj.oil.gasStation.repository;

import com.pj.oil.gasStation.entity.Area;
import com.pj.oil.gasStation.entity.GasStation;
import com.pj.oil.memberPost.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GasStationRepository extends JpaRepository<GasStation, Long> {
}
