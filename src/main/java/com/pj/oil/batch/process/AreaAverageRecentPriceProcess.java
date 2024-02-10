package com.pj.oil.batch.process;

import com.pj.oil.gasStation.entity.maria.AreaAverageRecentPrice;
import org.springframework.batch.item.*;


public class AreaAverageRecentPriceProcess implements ItemProcessor<AreaAverageRecentPrice, AreaAverageRecentPrice>{

    @Override
    public AreaAverageRecentPrice process(AreaAverageRecentPrice item) {
        return item;
    }
}
