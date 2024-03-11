package com.pj.oil.batch.process;

import com.pj.oil.gasStation.dto.PriceLpgDto;
import io.micrometer.common.util.StringUtils;
import org.springframework.batch.item.ItemProcessor;


public class PriceLpgProcess implements ItemProcessor<PriceLpgDto, PriceLpgDto>{

    @Override
    public PriceLpgDto process(PriceLpgDto item) {
        // ID가 null이거나 공백인 레코드를 필터링
        if (StringUtils.isBlank(item.getUniId())) {
            return null; // 이 레코드를 스킵
        }
        return item;
    }
}
