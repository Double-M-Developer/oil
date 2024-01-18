package com.pj.oil.service;


import com.pj.oil.dto.SimpleAreaDto;
import com.pj.oil.entity.Area;
import com.pj.oil.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AreaService {

    private final AreaRepository areaRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    //DB에_있는_지역_데이터_1곳_조회
    public Optional<SimpleAreaDto> findAreaByAreaCd(String areaCd) {
        Optional<SimpleAreaDto> findAreas = areaRepository.findAreaByAreaCd(areaCd);
        if (findAreas.isEmpty()) {
            LOGGER.info("[findAreaByAreaCd] area data dose not existed");
        }
        LOGGER.info("[findAreaByAreaCd] area data dose existed, area: {}", findAreas);
        return findAreas;
    }

    //DB에_있는_지역_데이터_및_하위_지역_조회
    public List<Area> findAreaWithParentByAreaCd(String areaCd) {
        List<Area> findAreas = areaRepository.findAreaWithParentByAreaCd(areaCd);
        if (findAreas.isEmpty()) {
            LOGGER.info("[findAreaWithParentByAreaCd] area data dose not existed");
        }
        LOGGER.info("[findAreaWithParentByAreaCd] area data dose existed, area: {}", findAreas);
        return findAreas;
    }
}
