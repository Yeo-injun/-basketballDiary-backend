package com.threeNerds.basketballDiary.constant.code;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PlayerTypeCode {

    TEAM_MEMBER("팀원", "01"),
    AUTH_GUEST("게스트(회원)", "02"),
    UNAUTH_GUEST("게스트(비회원)", "03");

    private final String name;
    private final String code;

    PlayerTypeCode(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public static String nameOf(String code) {
        if (code == null) return "";
        String codeName = Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .map(PlayerTypeCode::getName)
                .findAny()
                .get();
        return codeName;
    }
}
