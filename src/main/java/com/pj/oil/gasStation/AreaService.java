package com.pj.oil.gasStation;


import com.pj.oil.gasStation.entity.Area;
import com.pj.oil.gasStation.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
}
