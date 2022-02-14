package com.threeNerds.basketballDiary.mvc.domain;

import lombok.Getter;

@Getter
public enum JoinRequestStateCode {
    WAITING("대기중", "01"),
    APPROVAL("승인", "02"),
    REJECTION("거절", "03");

    private final String name;
    private final String code;

    JoinRequestStateCode(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
