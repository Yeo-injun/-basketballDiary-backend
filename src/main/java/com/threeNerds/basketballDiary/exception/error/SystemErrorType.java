package com.threeNerds.basketballDiary.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum SystemErrorType implements ErrorMessageType {

    /** 400 BAD_REQUEST : 잘못된 요청 */
    NOT_ALLOWED_FILE_EXTENSTION( BAD_REQUEST, "허용되지 않는 파일 확장자입니다." ),
    MISSING_REQUIRED_PARAMETERS( BAD_REQUEST, "필수 파라미터값이 누락되었습니다." ),

    /** 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    NOT_FOUND_IMAGE_FOR_URL( NOT_FOUND, "해당 URL에 이미지 파일이 존재하지 않습니다."),

    /** 500 INTERNAL_SERVER_ERROR : null point에러 등 */
    INTERNAL_ERROR( INTERNAL_SERVER_ERROR, "서버 내부에서 오류가 발생했습니다." ),
    ERROR_IN_PROCESS_FILE( INTERNAL_SERVER_ERROR, "파일처리중 오류가 발생했습니다." );
    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public Integer getStatus() {
        return this.httpStatus.value();
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
