package com.threeNerds.basketballDiary.exception.error;

public interface ErrorMessageType {
    Integer getStatus();
    String getCode();
    String getMessage();
}
