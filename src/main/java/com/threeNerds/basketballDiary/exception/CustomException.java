package com.threeNerds.basketballDiary.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    /**
     * 예외를 발생시킬 때
     * CustomException클래스를 생성하고
     * ErrorCode객체를 파라미터로 넘겨준다.
     *
     * CustomException이 발생하면 CustomExceptionHandler이 호출되고
     * CustomException의 ErrorCode를 넘겨받게된다.
     */
    private final Error error;
}
