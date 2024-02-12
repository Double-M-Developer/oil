package com.pj.oil.gasStation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class PriceOil {

    @Id
    private String id;

    // 고급 휘발유
    private int preGasoline;

    // 휘발유
    private int gasoline;

    // 경유
    private int diesel;
}
