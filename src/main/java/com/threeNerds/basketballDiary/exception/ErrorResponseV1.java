package com.threeNerds.basketballDiary.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ErrorResponseV1 {

    private int code;

    private String message;

    private Map<String,String> validation = new HashMap<>();

    @Builder
    public ErrorResponseV1(int code, String message, Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation;
    }
}
