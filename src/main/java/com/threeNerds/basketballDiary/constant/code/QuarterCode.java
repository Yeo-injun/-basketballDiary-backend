package com.threeNerds.basketballDiary.constant.code;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum QuarterCode {
    FIRST("1쿼터", "01"),
    SECOND("2쿼터", "02"),
    THIRD("3쿼터", "03"),
    FOURTH("4쿼터", "04");

    private final String name;
    private final String code;

    QuarterCode(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /* enum의 열거된 항목들의 code값을 통해 이름을 가져오기 */
    public static String nameOf(String code) {
//        if (code == null) return "";
        String codeName = Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .map(QuarterCode::getName)
                .findAny()
                .orElse("");
//                .get();
        return codeName;
    }
}
