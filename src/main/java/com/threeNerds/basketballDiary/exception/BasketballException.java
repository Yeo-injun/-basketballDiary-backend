package com.threeNerds.basketballDiary.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Deprecated
@Getter
public class BasketballException extends RuntimeException{

    private HttpStatus status;
    private final ErrorType exception = ErrorType.from(this.getClass());
}
