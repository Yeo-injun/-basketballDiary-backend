package com.threeNerds.basketballDiary.exception;

import com.threeNerds.basketballDiary.exception.custom.TeamNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

// TODO @RestControllerAdvice에 대한 학습 필요
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

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

    @ExceptionHandler(value = { NullPointerException.class })
    protected ResponseEntity<ErrorResponse> handleNullPointerException (NullPointerException ex)
    {
        log.error("handleCustomException throw CustomException : {}", ex.getMessage(),ex);
        return ErrorResponse.toResponseEntity(Error.INTERNAL_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponseV1> MethodArgumentNotValidException(MethodArgumentNotValidException ex){

        int statusCode = HttpStatus.BAD_REQUEST.value();
        String message = HttpStatus.BAD_REQUEST.getReasonPhrase();

        Map<String,String> validation = new HashMap<>();
        for (FieldError fieldError : ex.getFieldErrors()) {
            validation.put(fieldError.getField(),fieldError.getDefaultMessage());
        }

        ErrorResponseV1 responseError = ErrorResponseV1.builder()
                .code(statusCode)
                .message(message)
                .validation(validation)
                .build();
        return ResponseEntity.status(statusCode).body(responseError);
    }
}
