package com.threeNerds.basketballDiary.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum SystemErrorType implements ErrorMessageType {

    /** 400 BAD_REQUEST : 잘못된 요청 */
    NOT_NULLALLBE_VALUE( BAD_REQUEST, "필수값이 존재하지 않습니다." ),
    NOT_ALLOWED_FILE_EXTENSTION( BAD_REQUEST, "허용되지 않는 파일 확장자입니다." ),
    MISSING_REQUIRED_PARAMETERS( BAD_REQUEST, "필수 파라미터값이 누락되었습니다." ),
    EXCEED_MAX_FILE_SIZE( BAD_REQUEST, "파일 업로드 최대크기를 초과하였습니다." ),
    PARAMETER_FORMAT_ERROR( BAD_REQUEST, "파라미터 값 형식이 맞지 않습니다." ),

    /** 400 BAD_REQUEST : 코드 도메인 유효성 실패 */
    INVALID_CODE_DOMAIN_FOR_HOME_AWAY_CODE( BAD_REQUEST, "유효하지 않은 홈어웨이 코드입니다." ),
    INVALID_CODE_DOMAIN_FOR_QUARTER_CODE( BAD_REQUEST, "유효하지 않은 쿼터 코드입니다." ),


    /** 401 UNAUTHORIZED : 인증되지 않은 사용자 ( 시스템 권한 으로 관리 ) */
    UNAUTHORIZED_ACCESS(UNAUTHORIZED, "접근 권한이 없습니다."),
    LOGIN_REQUIRED(UNAUTHORIZED, "로그인이 필요합니다"),

    /** 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    NOT_FOUND_IMAGE_FOR_URL( NOT_FOUND, "해당 URL에 이미지 파일이 존재하지 않습니다."),
    NOT_FOUND_VALID_VALUE( NOT_FOUND, "유효한 값을 찾지 못했습니다." ),
    NOT_FOUND_USER_FOR_UPDATE( NOT_FOUND, "프로필을 수정할 사용자정보가 없습니다." ),
    NOT_FOUND_USER_FOR_WITHDRAWAL( NOT_FOUND, "탈퇴처리할 사용자정보가 존재하지 않습니다."),

    /** 500 INTERNAL_SERVER_ERROR : null point에러 등 */
    INTERNAL_ERROR( INTERNAL_SERVER_ERROR, "서버 내부에서 오류가 발생했습니다." ),

    NOT_FOUND_VALID_AUTH_INFO( INTERNAL_SERVER_ERROR, "유효한 권한정보를 찾는데 실패하였습니다." ),
    ERROR_IN_PROCESS_FILE( INTERNAL_SERVER_ERROR, "파일처리중 오류가 발생했습니다." ),
    ERROR_IN_TEAM_AUTH_CHECK(  INTERNAL_SERVER_ERROR, "요청한 URL은 팀권한을 체크할 수 없습니다." );

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
