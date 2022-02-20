package com.threeNerds.basketballDiary.constant;

import lombok.Getter;

@Getter
public enum PositionCode {

    GUARD("가드", "10"),
    FORWARD("포워드", "20"),
    CENTER("센터", "30"),
    POINT_GUARD("포인트가드", "11"),
    SHOOTING_GAURD("슈팅가드", "12"),
    SMALL_FORWARD("스몰포워드", "21"),
    POWER_FORWARD("파워포워드", "22");

    private final String name;
    private final String code;

    PositionCode(String name, String code) {
        this.name = name;
        this.code = code;
    }

//    public getCode (String name) {
//
//    }
}
