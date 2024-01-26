package com.pj.oil.gasStation;


import com.pj.oil.gasStation.entity.maria.Area;
import com.pj.oil.gasStation.entity.redis.AreaRedis;
import com.pj.oil.gasStation.repository.jpa.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AreaService {

    private final AreaRepository areaRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    //DB에_있는_지역_데이터_1곳_조회
    public Optional<Area> findAreaByAreaCd(String areaCode) {
        Optional<Area> findAreas = areaRepository.findById(areaCode);
        if (findAreas.isEmpty()) {
            LOGGER.info("[findAreaByAreaCd] area data dose not existed");
        }
        LOGGER.info("[findAreaByAreaCd] area data dose existed, area: {}", findAreas);
        return findAreas;
    }

    //DB에_있는_지역_데이터_전체_조회
    public List<Area> findAllArea() {
        List<Area> entity = areaRepository.findAll();
        if (entity.isEmpty()) {
            LOGGER.info("[findAreaByAreaCd] area data dose not existed");
        }
        LOGGER.info("[findAreaByAreaCd] area data dose existed, area size: {}", entity.size());
        return entity;
    }

    // Area 객체를 AreaRedis 객체로 변환
    public List<AreaRedis> toRedisEntities(List<Area> areas) {
        return areas.stream()
                .map(area -> new AreaRedis(area.getAreaCode(), area.getAreaName()))
                .collect(Collectors.toList());
    }
}
