package com.pj.oil.batch.process;

import com.pj.oil.gasStation.entity.PriceLpg;
import io.micrometer.common.util.StringUtils;
import org.springframework.batch.item.ItemProcessor;


public class PriceLpgProcess implements ItemProcessor<PriceLpg, PriceLpg>{

    @Override
    public PriceLpg process(PriceLpg item) {
        // ID가 null이거나 공백인 레코드를 필터링
        if (StringUtils.isBlank(item.getUniId())) {
            return null; // 이 레코드를 스킵
        }
        return item;
    }
}
