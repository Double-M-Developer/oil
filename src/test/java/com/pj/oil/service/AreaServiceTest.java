//package com.pj.oil.service;
//
//import com.pj.oil.gasStation.AreaService;
//import com.pj.oil.gasStation.entity.Area;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@Transactional
//class AreaServiceTest {
//
//    public static void printAreaDto(Area object) {
//        System.out.println(object.toString());
//    }
//    public static void printAreaList(List<Area> list) {
//        for (Area o : list) {
//            System.out.println(o.toString());
//        }
//    }
//
//    @Autowired
//    AreaService areaService;
//
//    @BeforeEach
//    void init() {
//
//    }
//
//    @Test
//    void DB에_있는_지역_데이터_1곳_조회() {
//        Optional<Area> area = areaService.findAreaByAreaCd("01");
//        printAreaDto(area.get());
//        area.ifPresent(value -> assertEquals("서울", value.getAreaName()));
//    }
//
//}