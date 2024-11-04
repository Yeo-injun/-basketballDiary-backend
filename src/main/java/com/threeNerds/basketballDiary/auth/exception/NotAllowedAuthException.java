package com.threeNerds.basketballDiary.auth.exception;

import lombok.Getter;

@Getter
public class NotAllowedAuthException implements AuthorizationException {

    private String name;
    private String message;


    public NotAllowedAuthException( String message ) {
        this.name       = "NOT_ALLOWED_AUTH";
        this.message    = message;
    }
    public NotAllowedAuthException( String name, String message ) {
        this.name       = name;
        this.message    = message;
    }
}
