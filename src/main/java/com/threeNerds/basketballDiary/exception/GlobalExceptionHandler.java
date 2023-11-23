package com.threeNerds.basketballDiary.exception;

import com.threeNerds.basketballDiary.exception.error.*;
import com.threeNerds.basketballDiary.file.exception.FileException;
import com.threeNerds.basketballDiary.file.exception.NotAllowedExtensionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
     * 처리 흐름
     * 1. Exception 발생
     * 2. Exception내부에 ErrorMessage정보를 전달받음
     * 3. 전달받은 Message정보로 ResponseEntity를 만든다.
     */
    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<ErrorResponse> handleCustomException (CustomException ex ) {
        log.error( "throw CustomException : {}", ex.getMessage(), ex );
        return DomainErrorResponse.toEntity( ex.getError() );
    }

    @ExceptionHandler(value = { FileException.class })
    protected ResponseEntity<ErrorResponse> handleFileException ( FileException ex ) {
        log.error( "throw FileException" );
        if ( ex instanceof NotAllowedExtensionException ) {
            return SystemErrorResponse.toEntity( SystemErrorType.NOT_ALLOWED_FILE_EXTENSTION );
        }
        return SystemErrorResponse.toEntity( SystemErrorType.ERROR_IN_PROCESS_FILE );
    }

    @ExceptionHandler(value = { NullPointerException.class, IllegalArgumentException.class })
    protected ResponseEntity<ErrorResponse> handleNullPointerException (NullPointerException ex) {
        log.error( "throw NullPointerException" );
        return SystemErrorResponse.toEntity( SystemErrorType.INTERNAL_ERROR );
    }

    @ExceptionHandler(value = { MissingRequestValueException.class })
    protected ResponseEntity<ErrorResponse> handleMissingParametersException ( MissingRequestValueException ex ) {
        return SystemErrorResponse.toEntity( SystemErrorType.MISSING_REQUIRED_PARAMETERS ) ;
    }

    /**
     * Controller에서 메세지 바인딩시 발생하는 오류 ( BindException) 는 이 Handler에서 처리.
     * ErrorResponse의 기본 속성외에 다음 속성이 추가된다.
     * - errorFields : 오류건이 존재하는 속성(필드)명 목록. 화면에서 오류가 발생한 항목을 표시할때 사용
     * - validations : 오류건의 내용 목록. 속성(필드)명과 오류 메세지를 가지고 있는 객체목록을 리턴한다.
     * @param ex
     * @return
     */
    @ExceptionHandler(value = { BindException.class })
    protected ResponseEntity<ErrorResponse> handleBindException ( BindException ex ) {
        log.error( "throw BindException" );
        return BindErrorResponse.toEntity( new BindErrorType( ex.getFieldErrors() ) );
    }


    //TODO : 삭제예정
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
