package com.threeNerds.basketballDiary.constant.code;

import com.threeNerds.basketballDiary.constant.Constant;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TeamAuthCode {

    // TODO 상수의 코드값 부여 정책 정해야 함 - 권한처리시 사용해야 하니까.
    // TODO 22.02.26(인준) code의 자료형을 int로 수정하는 것은? long타입은 과할것 같음
    LEADER("팀장", Long.toString(Constant.LEADER)),
    MANAGER("관리자", Long.toString(Constant.MANAGER)),
    TEAM_MEMBER("일반팀원", Long.toString(Constant.TEAM_MEMBER)),
    USER("사용자", Long.toString(Constant.USER));

    private final String name;
    private final String code;

    TeamAuthCode(String name, String code) {
        this.name = name;
        this.code = code;
    }

    // enum의 열거된 항목들의 code값을 통해 이름을 가져오기
    public static String nameOf(String code)
    {
//        if (code == null) return "";

        String codeName = Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .map(TeamAuthCode::getName)
                .findAny()
                .orElse("");
//                .get();
        return codeName;
    }

}
