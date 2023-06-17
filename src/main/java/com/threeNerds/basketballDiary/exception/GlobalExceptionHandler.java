package com.threeNerds.basketballDiary.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * 부모 클래스인 ResponseEntityExceptionHandler 내부에서 기본적으로 처리해주는 Exception이 존재 ( MethodArgumentNotValidException 포함 )
 * 다음 오류 해결 : Ambiguous @ExceptionHandler method mapped for [class org.springframework.web.bind.MethodArgumentNotValidException]
 * https://velog.io/@litsynp/Getting-Ambiguous-ExceptionHandler-method-mapped-for-XXXException
 * 이때문에 @ExceptionHandler로 MethodArgumentNotValidException를 처리하려고 하는 메소드를 따로 만들면 Spring은 MethodArgumentNotValidExcpetion을 처리하는 Bean을 무엇을 기준으로 만들어야 할지 특정할 수 없음
 * 따라서 ResponseEntityExceptionHandler에서 구현한 handleMethodArgumentNotValid() 메소드를 override해서 구현해야 함.
 */

// TODO @RestControllerAdvice에 대한 학습 필요
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler { //extends ResponseEntityExceptionHandler {

    /**
     * @ExceptionHandelr에 value로 할당해준 Exception 클래스가 예외로 던져지면 해당 메소드가 호출된다.
     *  >> 다른 Exception클래스를 적어주면 해당 클래스가 예외로 던져질때 메소드가 호출됨.
     * 메소드의 파라미터로 발생한 Exception 객체가 넘어오고
     * ErrorResponse클래스의 toResponseEntity()메소드를 호출하여
     * HttpResponse 메세지를 만들어서 클라이언트에게 Reponse한다.
     */
    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<ErrorResponse> handleCustomException (CustomException ex)
    {
        log.error("handleCustomException throw CustomException : {}", ex.getError(),ex);
        return ErrorResponse.toResponseEntity(ex.getError());
    }

    @ExceptionHandler(value = { NullPointerException.class  })
    protected ResponseEntity<ErrorResponse> handleNullPointerException (NullPointerException ex)
    {
        log.error("handleCustomException throw CustomException : {}", ex.getMessage(),ex);
        return ErrorResponse.toResponseEntity(Error.INTERNAL_ERROR);
    }


    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    protected ResponseEntity<ErrorResponseV1> handleMethodArgumentNotValid ( MethodArgumentNotValidException ex ) {
        int status = HttpStatus.BAD_REQUEST.value();
        String message = HttpStatus.BAD_REQUEST.getReasonPhrase();

        Map<String,String> validation = new HashMap<>();
        for (FieldError fieldError : ex.getFieldErrors()) {
            validation.put(fieldError.getField(),fieldError.getDefaultMessage());
        }

        ErrorResponseV1 responseError = ErrorResponseV1.builder()
                .status(status)
                .message(message)
                .validation(validation)
                .build();
        return ResponseEntity.status(status).body(responseError);
    }

    @ExceptionHandler(value = BasketballException.class)
    public ErrorResponseV1 handlerBasketballException(BasketballException ex){
        ErrorType exception = ex.getException();
        int status  = ex.getStatus().value();
        String message = exception.getMessage();

        ErrorResponseV1 responseError = ErrorResponseV1.builder()
                .status(status)
                .message(message)
                .build();
        return responseError;
    }
}
