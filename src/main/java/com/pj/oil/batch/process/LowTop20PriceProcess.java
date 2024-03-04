package com.pj.oil.batch.process;

import com.pj.oil.gasStation.entity.maria.LowTop20Price;
import org.springframework.batch.item.*;


public class LowTop20PriceProcess implements ItemProcessor<LowTop20Price, LowTop20Price> {

    @Override
    public LowTop20Price process(LowTop20Price item) throws Exception {
        return item;
    }
}