package com.threeNerds.basketballDiary.exception.error;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Builder
public class BindErrorResponse implements ErrorResponse {

    private final Integer status;
    private final String code;
    private final String message;

    private final String[] errorFields;             // Binding에 실패한 필드명 목록 ( 오류가 존재하는 항목을 표시하기 위한 목적 )
    private final ValidationResult[] validations;    // Binding에 실패한 필드명별 메세지

    public static ResponseEntity<ErrorResponse> toEntity( BindErrorType error ) {
        List<FieldError> fielderrors        = error.getFieldErrors();
        return ResponseEntity
                .status( error.getStatus() )
                .body( BindErrorResponse.builder()
                        .status(    error.getStatus() )
                        .code(      error.getCode() )
                        .message(   error.getMessage() )
                        .errorFields( toErrorFields( fielderrors ) )
                        .validations( toValidationResults( fielderrors ) )
                        .build() );
    }

    private static String[] toErrorFields( List<FieldError> fielderrors ) {
        // 같은 필드명에 2개이상의 오류가 존재할 수 있기 떄문에 중복 제거가 필요
        return fielderrors.stream()
                .map( FieldError::getField )
                .distinct()
                .toArray( String[]::new );
    }

    private static ValidationResult[] toValidationResults( List<FieldError> fielderrors ) {
        return fielderrors.stream()
                .map( f -> new ValidationResult( f.getField(), f.getDefaultMessage() ) )
                .toArray( ValidationResult[]::new );
    }

    @Getter
    public static class ValidationResult {
        private final String name;
        private final String message;

        ValidationResult( String name, String message ) {
            this.name = name;
            this.message = message;
        }
    }
}