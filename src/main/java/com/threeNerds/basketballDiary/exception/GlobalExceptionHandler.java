package com.threeNerds.basketballDiary.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @ExceptionHandelr에 value로 할당해준 Exception 클래스가 예외로 던져지면 해당 메소드가 호출된다.
     *  >> 다른 Exception클래스를 적어주면 해당 클래스가 예외로 던져질때 메소드가 호출됨.
     * 메소드의 파라미터로 발생한 Exception 객체가 넘어오고
     * ErrorResponse클래스의 toResponseEntity()메소드를 호출하여
     * HttpResponse 메세지를 만들어서 클라이언트에게 Reponse한다.
     */
    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<ErrorResponse> handleCustomException (CustomException e)
    {
        log.error("handleCustomException throw CustomException : {}", e.getError());
        return ErrorResponse.toResponseEntity(e.getError());
    }
}