package com.threeNerds.basketballDiary.constant.code;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum JoinRequestStateCode {

    WAITING("대기중", "01"),
    APPROVAL("승인", "02"),
    REJECTION("거절", "03"),
    CANCEL("취소", "04");

    private final String name;
    private final String code;

    JoinRequestStateCode(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /* enum의 열거된 항목들의 code값을 통해 이름을 가져오기 */
    public static String nameOf(String code) {
//        if (code == null) return "";
        String codeName = Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .map(JoinRequestStateCode::getName)
                .findAny()
                .orElse("");
//                .get();
        return codeName;
    }

}
