package com.threeNerds.basketballDiary.constant.code.type;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GameRecordStateCode {
    CREATION("게임생성", "01"),
    JOIN_TEAM_CONFIRMATION("참가팀확정", "02"),
    CONFIRMATION("게임확정", "03");

    private final String name;
    private final String code;

    GameRecordStateCode(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /* enum의 열거된 항목들의 code값을 통해 이름을 가져오기 */
    public static String nameOf(String code) {
        String codeName = Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .map(GameRecordStateCode::getName)
                .findAny()
                .orElse("");
        return codeName;
    }
}
