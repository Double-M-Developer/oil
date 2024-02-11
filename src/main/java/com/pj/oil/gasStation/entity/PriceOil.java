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

    private int oil1;

    private int oil2;

    private int oil3;
}
