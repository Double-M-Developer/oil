package com.pj.oil.gasStation.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
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

    @OneToOne
    @JoinColumn(name = "price_oil", referencedColumnName = "id")
    @Nullable
    private PriceOil price_oil;

    @OneToOne
    @JoinColumn(name = "price_lpg", referencedColumnName = "id")
    @Nullable
    private PriceLpg price_lpg;

}


