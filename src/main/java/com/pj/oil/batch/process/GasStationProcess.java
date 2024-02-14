package com.pj.oil.batch.process;

import com.pj.oil.gasStation.entity.maria.GasStation;
import io.micrometer.common.util.StringUtils;
import org.springframework.batch.item.ItemProcessor;


public class GasStationProcess implements ItemProcessor<GasStation, GasStation>{

    @Override
    public GasStation process(GasStation item) {
        // ID가 null이거나 공백인 레코드를 필터링
        if (StringUtils.isBlank(item.getId())) {
            return null; // 이 레코드를 스킵
        }
        return item;
    }
}
