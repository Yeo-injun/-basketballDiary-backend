package com.threeNerds.basketballDiary.exception.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@AllArgsConstructor
public class DomainErrorResponse implements ErrorResponse {

    private final Integer status;   // HTTP상태코드 ( 코드의 상위 도메인 )
    private final String code;      // Error별 코드값
    private final String message;   // Error에 대한 상세 메세지

    public static ResponseEntity<ErrorResponse> toEntity( ErrorMessageType error ) {
        return ResponseEntity
                .status( error.getStatus() )
                .body( DomainErrorResponse.builder()
                        .status(    error.getStatus() )
                        .code(      error.getCode() )
                        .message(   error.getMessage() )
                        .build() );
    }
}