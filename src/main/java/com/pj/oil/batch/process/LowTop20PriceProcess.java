package com.pj.oil.batch.process;

import com.pj.oil.gasStation.dto.LowTop20PriceDto;
import org.springframework.batch.item.*;


public class LowTop20PriceProcess implements ItemProcessor<LowTop20PriceDto, LowTop20PriceDto> {

    @Override
    public LowTop20PriceDto process(LowTop20PriceDto item) throws Exception {
        return item;
    }
}