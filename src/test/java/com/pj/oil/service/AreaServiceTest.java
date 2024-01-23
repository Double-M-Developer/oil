package com.pj.oil.service;

import com.pj.oil.gasStation.AreaService;
import com.pj.oil.gasStation.SimpleAreaDto;
import com.pj.oil.gasStation.Area;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class AreaServiceTest {

    public static void printSimpleAreaDto(SimpleAreaDto object) {
        System.out.println(object.toString());
    }
    public static void printAreaList(List<Area> list) {
        for (Area o : list) {
            System.out.println(o.toString());
        }
    }

    @Autowired
    AreaService areaService;

    @BeforeEach
    void init() {

    }

    @Test
    void DB에_있는_지역_데이터_1곳_조회() {
        Optional<SimpleAreaDto> area = areaService.findAreaByAreaCd("01");
        printSimpleAreaDto(area.get());
        area.ifPresent(value -> assertEquals("서울", value.getAreaNm()));
    }

    @Test
    void DB에_있는_지역_데이터_및_하위_지역_조회() {
        List<Area> area = areaService.findAreaWithParentByAreaCd("01");
        printAreaList(area);
        assertFalse(area.isEmpty());
    }

}