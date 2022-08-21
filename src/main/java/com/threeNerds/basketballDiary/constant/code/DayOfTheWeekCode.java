package com.threeNerds.basketballDiary.constant.code;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum DayOfTheWeekCode {
    MON("월", "1"),
    TUE("화", "2"),
    WED("수", "3"),
    THU("목", "4"),
    FRI("금", "5"),
    SAT("토", "6"),
    SUN("일", "7");

    private final String name;
    private final String code;

    DayOfTheWeekCode(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /* enum의 열거된 항목들의 code값을 통해 이름을 가져오기 */
    public static String nameOf(String code) {
        if (code == null) return "";
        String codeName = Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .map(DayOfTheWeekCode::getName)
                .findAny()
                .get();
        return codeName;
    }
}
