package com.pj.oil.gasStation.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PollDivCode {
    SKE("SKE", "SK에너지"),
    GSC("GSC", "GS칼텍스"),
    HDO("HDO", "현대오일뱅크"),
    SOL("SOL", "S-OIL"),
    RTE("RTE", "자영알뜰"),
    RTX("RTX", "고속도로 알뜰"),
    NHO("NHO", "농협알뜰"),
    ETC("ETC", "자가상표"),
    E1G("E1G", "E1"),
    SKG("SKG", "SK가스");

    private final String key;
    private final String title;
}
