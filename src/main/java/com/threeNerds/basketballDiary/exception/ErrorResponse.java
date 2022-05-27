package com.threeNerds.basketballDiary.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String code;
    private final String message;

    /**
     * ResponseEntityExceptionHandler를 상속받아 구현한 CustomExceptionHandler에서
     * 발생한 예외를 처리하고, 예외처리시 넘겨받은 ErrorCode객체를 파라미터로 받아서
     * ErrorResponse.toResponseEntity Static 메소드를 이용해서
     * HTTP 상태코드와 에러메세지 등의 정보를 HttpResponse로 만들어줌.
     */
    public static ResponseEntity<ErrorResponse> toResponseEntity(Error error){
        return ResponseEntity
                .status(error.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(error.getHttpStatus().value())
                        .error(error.getHttpStatus().name())
                        .code(error.name())
                        .message(error.getMessage())
                        .build());
    }
}