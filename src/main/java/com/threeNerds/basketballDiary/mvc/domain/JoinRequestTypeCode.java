package com.threeNerds.basketballDiary.mvc.domain;

import lombok.Getter;

/**
 * 참고자료 : https://velog.io/@kyle/%EC%9E%90%EB%B0%94-Enum-%EA%B8%B0%EB%B3%B8-%EB%B0%8F-%ED%99%9C%EC%9A%A9 (Enum을 통한 Code값 관리)
 * 가입요청 유형 코드 관리
 */
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
}
