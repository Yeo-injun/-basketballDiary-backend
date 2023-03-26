package com.threeNerds.basketballDiary.exception.custom;

import org.springframework.jdbc.BadSqlGrammarException;

import java.util.HashMap;
import java.util.Map;

public abstract class BasketballException extends RuntimeException{

    private Map<String,String> validation = new HashMap<>();

    public BasketballException(String message){
        super(message);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName,String message){
        this.validation.put(fieldName,message);
    }
}
