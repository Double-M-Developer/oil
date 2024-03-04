package com.pj.oil.batch.process;

import com.pj.oil.gasStation.entity.maria.AverageRecentPrice;
import org.springframework.batch.item.ItemProcessor;


public class AverageRecentPriceProcess implements ItemProcessor<AverageRecentPrice, AverageRecentPrice>{

    @Override
    public AverageRecentPrice process(AverageRecentPrice item) {
        return item;
    }
}
