package com.pj.oil.gasStation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@Entity
public class GasStation {

    @Id
    private String id;

    private String area;

    private String brand;

    private String addr;

    private String brand2;

    private String self;

}


