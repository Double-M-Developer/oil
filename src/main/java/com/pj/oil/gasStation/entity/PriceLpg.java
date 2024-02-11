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
public class PriceLpg {

    @Id
    private String id;

    private int lpg;

}
