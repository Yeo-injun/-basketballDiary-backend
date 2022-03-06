package com.threeNerds.basketballDiary.exception;

/**
 * 중복에 대한 예외
 */
public class AlreadyExsitException extends RuntimeException {

    public AlreadyExsitException() {
        super();
    }

    public AlreadyExsitException(String message) {
        super(message);
    }
}
