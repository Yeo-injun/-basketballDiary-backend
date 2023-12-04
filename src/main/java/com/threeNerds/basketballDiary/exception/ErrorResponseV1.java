package com.threeNerds.basketballDiary.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ErrorResponseV1 {

    private int status;
    private String message;
    private Map<String,String> validation;

    @Builder
    public ErrorResponseV1(int status, String message, Map<String, String> validation) {
        this.status = status;
        this.message = message;
        this.validation = validation != null ? validation : new HashMap<>();
    }

    void addValidation(String field,String errorMessage){
        validation.put(field,errorMessage);
    }
}
