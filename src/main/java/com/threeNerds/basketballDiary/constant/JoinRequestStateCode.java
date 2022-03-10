package com.threeNerds.basketballDiary.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum JoinRequestStateCode {

    ALL("전체상태", "00"),
    WAITING("대기중", "01"),
    APPROVAL("승인", "02"),
    REJECTION("거절", "03");

    private final String name;
    private final String code;

    JoinRequestStateCode(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public static String getName(String code) {
        Optional<JoinRequestStateCode> targetEnum = Arrays.stream(JoinRequestStateCode.values())
                                                        .filter(item -> item.isTypeCode(code))
                                                        .findAny();
        return targetEnum.get().getName();
    }

    public boolean isTypeCode(String code) {
        return this.code.equals(code);
    }

}
