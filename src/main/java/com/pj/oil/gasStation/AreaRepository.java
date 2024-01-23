package com.pj.oil.gasStation;

import com.pj.oil.gasStation.SimpleAreaDto;
import com.pj.oil.gasStation.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AreaRepository extends JpaRepository<Area, String> {

    @Query("select new com.pj.oil.dto.SimpleAreaDto(" +
            "a.areaCd, a.areaNm ) " +
            "from Area a " +
            "where a.areaCd = :areaCd")
    Optional<SimpleAreaDto> findAreaByAreaCd(String areaCd);

    @Query("select a " +
            "from Area a " +
            "left join fetch a.childCd " +
            "where a.areaCd = :areaCd " +
            "or a.parentCd.areaCd = :areaCd")
    List<Area> findAreaWithParentByAreaCd(@Param("areaCd") String areaCd);

    @Query("select a.areaCd, a.areaNm, a.parentCd, a.gasStations " +
            "from Area a " +
            "where a.areaCd = :areaCd")
    Optional<Area> findAreaWithGasStationsByAreaCd(String areaCd);
}
