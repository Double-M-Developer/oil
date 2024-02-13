package com.pj.oil.batch.process;

import com.pj.oil.gasStation.entity.GasStation;
import org.springframework.batch.item.ItemProcessor;


public class GasStationOILProcess implements ItemProcessor<GasStation, GasStation>{

    @Override
    public GasStation process(GasStation item) {
        return item;
    }
}
