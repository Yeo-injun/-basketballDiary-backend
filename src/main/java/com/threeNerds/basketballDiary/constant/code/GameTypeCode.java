package com.threeNerds.basketballDiary.constant.code;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GameTypeCode {
    SELF_GAME("자체전", "01"),
    MATCH_UP_GAME("교류전", "02"),
    COMPETITION("대회", "03");

    private final String name;
    private final String code;

    GameTypeCode(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /* enum의 열거된 항목들의 code값을 통해 이름을 가져오기 */
    public static String nameOf(String code) {
        if (code == null) return "";
        String codeName = Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .map(GameTypeCode::getName)
                .findAny()
                .get();
        return codeName;
    }
}
