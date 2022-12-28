package com.threeNerds.basketballDiary.constant.code;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GameRecordAuthCode {
    CREATOR("게임생성자", "01"),
    ONLY_WRITER("입력권한자", "02");

    private final String name;
    private final String code;

    GameRecordAuthCode(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /* enum의 열거된 항목들의 code값을 통해 이름을 가져오기 */
    public static String nameOf(String code) {
        String codeName = Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .map(GameRecordAuthCode::getName)
                .findAny()
                .orElse("");
        return codeName;
    }
}
