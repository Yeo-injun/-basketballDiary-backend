package com.threeNerds.basketballDiary.exception.error;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
public class BindErrorType implements ErrorMessageType {

    private static final Integer status     = 400;
    private static final String code        = "ILLEAGAL_MESSAGE_SPECIFICATIONS";
    private static final String message     = "API메세지 규격 오류입니다.";
    private final List<FieldError> fieldErrors;   // Error에 대한 상세 메세지

    public BindErrorType( List<FieldError> fieldErrors ) {
        this.fieldErrors = fieldErrors;
    }

    @Override
    public Integer getStatus() {
        return this.status;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}