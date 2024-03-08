package com.pj.oil.batch.process;

import com.pj.oil.gasStation.entity.GasStationLpg;
import io.micrometer.common.util.StringUtils;
import org.springframework.batch.item.ItemProcessor;


public class GasStationLpgProcess implements ItemProcessor<GasStationLpg, GasStationLpg>{

    @Override
    public GasStationLpg process(GasStationLpg item) {
        // ID가 null이거나 공백인 레코드를 필터링
        if (StringUtils.isBlank(item.getUniId())) {
            return null; // 이 레코드를 스킵
        }
        return item;
    }
}
