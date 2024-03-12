package com.pj.oil.gasStation.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
public class Product {
    @Schema(description = "주유소 제품코드")
    @Id @Column(name = "product_code")
    private String productCode;

    @Schema(description = "주유소 제품명")
    @Column(name = "product_name")
    private String ProductName;
}
