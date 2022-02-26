package com.threeNerds.basketballDiary.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum TeamAuthCode {

    // TODO 상수의 코드값 부여 정책 정해야 함 - 권한처리시 사용해야 하니까.
    // TODO 22.02.26(인준) code의 자료형을 int로 수정하는 것은? long타입은 과할것 같음
    LEADER("팀장", "3"),
    MANGER("관리자", "2"),
    TEAM_MEMBER("일반팀원", "1"),
    GUEST("게스트", "0");

    private final String name;
    private final String code;

    TeamAuthCode(String name, String code) {
        this.name = name;
        this.code = code;
    }

    // enum의 열거된 항목들의 code값을 통해 이름을 가져오기
    public static String getName(String code) {
        /** stream의 활용방법
         * 1. Collections객체를 Stream객체로 만든다.
         * 2. 중간연산(filter, map 등의 메소드를 통해 데이터를 정제한다.)
         * 3. 최종연산 - 중간연산을 통해 처리한 결과를 출력하거나 검색한다. (forEach(), findAny() ...)
         *  findAny()를 최종연산자로 사용할 경우 Optional객체를 반환받음
         */
        Optional<TeamAuthCode> targetEnum = Arrays.stream(TeamAuthCode.values())
                .filter(item -> item.isTypeCode(code))
                .findAny(); // TODO 문법확인 필요
        return targetEnum.get().getName(); // Optional객체 안에 감싸져 있는 본래 객체를 .get()메소드를 사용하영 가져오기!
    }

    private boolean isTypeCode(String code) {
        return this.code.equals(code);
    }

}
