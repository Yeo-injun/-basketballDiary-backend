package com.threeNerds.basketballDiary.auth.exception;

import lombok.Getter;

@Getter
public class RequiredLoginException implements AuthorizationException {

    private String name;
    private String message;
    public RequiredLoginException() {
        this.name       = "REQUIRED_LOGIN";
        this.message    = "로그인이 필요합니다.";
    }
}
