package com.pj.oil.batch.process;

import com.pj.oil.gasStation.entity.AverageAllPrice;
import org.springframework.batch.item.*;


public class AverageAllPriceProcess implements ItemProcessor<AverageAllPrice, AverageAllPrice> {

    @Override
    public AverageAllPrice process(AverageAllPrice item) throws Exception {
        return item;
    }
}
