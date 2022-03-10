package com.threeNerds.basketballDiary.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * 참고자료 : https://velog.io/@kyle/%EC%9E%90%EB%B0%94-Enum-%EA%B8%B0%EB%B3%B8-%EB%B0%8F-%ED%99%9C%EC%9A%A9 (Enum을 통한 Code값 관리)
 * 가입요청 유형 코드 관리
 */

/* 22.02.16 인준 제안  - domain하위에 code Package구조 추가 */
@Getter
public enum JoinRequestTypeCode {

    ALL("전체", "00"),
    INVITATION("팀초대", "01"),
    JOIN_REQUEST("팀가입요청", "02");

    private final String name;
    private final String code;

    JoinRequestTypeCode(String name, String code) {
        this.name = name;
        this.code = code;
    }

    // enum의 열거된 항목들의 code값을 통해 이름을 가져오기
    public static String getName(String code) {
        Optional<JoinRequestTypeCode> targetEnum = Arrays.stream(JoinRequestTypeCode.values())
                .filter(item -> item.isTypeCode(code))
                .findAny();
        return targetEnum.get().getName(); // Optional객체 안에 감싸져 있는 본래 객체를 .get()메소드를 사용하영 가져오기!
    }

    private boolean isTypeCode(String code) {
        return this.code.equals(code);
    }
}
