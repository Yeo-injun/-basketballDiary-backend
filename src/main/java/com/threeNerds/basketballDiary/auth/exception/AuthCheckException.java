package com.threeNerds.basketballDiary.auth.exception;

import lombok.Getter;

@Getter
public class AuthCheckException implements AuthorizationException {

    private String name;
    private String message;

    public AuthCheckException( String message ) {
        this.name       = "NOT_AVAILABLE_AUTH_CHECK";
        this.message    = message;
    }

    public AuthCheckException( String name, String message ) {
        this.name       = name;
        this.message    = message;
    }
}
