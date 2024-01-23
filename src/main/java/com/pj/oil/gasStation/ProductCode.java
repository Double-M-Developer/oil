package com.pj.oil.gasStation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductCode {
    B027("B027", "휘발유"),
    D047("D047","경유"),
    B034("B034","고급휘발유"),
    C004("C004","실내등유"),
    K015("K015","자동차부탄");

    private final String key;
    private final String title;

}
