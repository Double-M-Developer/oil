package com.pj.oil.gasStation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class GasStation {

    @Id
    private String id;

    private String area;

    private String brand;

    private String addr;

    private String brand2;

    private String self;

    private String oil1;

    private String oil2;

    private String oil3;
}


