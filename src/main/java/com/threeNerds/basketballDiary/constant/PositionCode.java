package com.threeNerds.basketballDiary.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

@Getter
public enum PositionCode {

    ALL("전체", "00"),
    GUARD("가드", "10"),
    FORWARD("포워드", "20"),
    CENTER("센터", "30"),
    POINT_GUARD("포인트가드", "11"),
    SHOOTING_GAURD("슈팅가드", "12"),
    SMALL_FORWARD("스몰포워드", "23"),
    POWER_FORWARD("파워포워드", "24"),
    TEMP_CODE("임시용코드", "40"), // TODO 임시 코드 차후 데이터 변경후 삭제 예정
    NULL_CODE("임시용코드", null); // TODO 임시 코드 차후 데이터 변경후 삭제 예정

    private final String name;
    private final String code;

    PositionCode(String name, String code) {
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
        Optional<PositionCode> targetEnum = Arrays.stream(PositionCode.values())
                                            .filter(item -> item.isTypeCode(code))
                                            .findAny(); // TODO 문법확인 필요
        return targetEnum.get().getName(); // Optional객체 안에 감싸져 있는 본래 객체를 .get()메소드를 사용하영 가져오기!
    }

    private boolean isTypeCode(String code) {
        return this.code.equals(code);
    }

}
