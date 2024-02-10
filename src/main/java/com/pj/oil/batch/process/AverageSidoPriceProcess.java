package com.pj.oil.batch.process;

import com.pj.oil.gasStation.entity.maria.AverageSidoPrice;
import org.springframework.batch.item.*;


public class AverageSidoPriceProcess implements ItemProcessor<AverageSidoPrice, AverageSidoPrice> {

    @Override
    public AverageSidoPrice process(AverageSidoPrice item) throws Exception {
        return item;
    }
}
