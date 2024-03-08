package com.pj.oil.batch.process;

import com.pj.oil.gasStation.dto.PriceOilDto;
import io.micrometer.common.util.StringUtils;
import org.springframework.batch.item.ItemProcessor;


public class PriceOilProcess implements ItemProcessor<PriceOilDto, PriceOilDto>{

    @Override
    public PriceOilDto process(PriceOilDto item) {
        // ID가 null이거나 공백인 레코드를 필터링
        if (StringUtils.isBlank(item.getUniId())) {
            return null; // 이 레코드를 스킵
        }
        return item;
    }
}