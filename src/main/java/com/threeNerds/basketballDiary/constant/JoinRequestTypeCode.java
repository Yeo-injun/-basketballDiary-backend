package com.threeNerds.basketballDiary.constant;

import lombok.Getter;

import java.util.Arrays;

/**
 * 참고자료 : https://velog.io/@kyle/%EC%9E%90%EB%B0%94-Enum-%EA%B8%B0%EB%B3%B8-%EB%B0%8F-%ED%99%9C%EC%9A%A9 (Enum을 통한 Code값 관리)
 * 가입요청 유형 코드 관리
 */

/* 22.02.16 인준 제안  - domain하위에 code Package구조 추가 */
@Getter
public enum JoinRequestTypeCode {

    INVITATION("팀초대", "01"),
    JOIN_REQUEST("팀가입요청", "02");

    private final String name;
    private final String code;

    JoinRequestTypeCode(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /* enum의 열거된 항목들의 code값을 통해 이름을 가져오기 */
    // Stream으로 구현한 이유 : https://tecoble.techcourse.co.kr/post/2021-09-30-java8-functional-programming/
    // Steam API정리 : https://tecoble.techcourse.co.kr/post/2021-05-23-stream-api-basic/
    public static String nameOf(String code) {
        String codeName = Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .map(JoinRequestTypeCode::getName)
                .findAny()
                .get();
        return codeName;
    }
}
