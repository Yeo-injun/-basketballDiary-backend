package com.threeNerds.basketballDiary.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum Error {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    CANNOT_FOLLOW_MYSELF(BAD_REQUEST, "자기 자신은 팔로우 할 수 없습니다"),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),
    LOGIN_REQUIRED(UNAUTHORIZED, "로그인이 필요합니다"),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    USER_NOT_FOUND(NOT_FOUND, "해당 사용자를 찾을 수 없습니다"),
    TEAM_NOT_FOUND(NOT_FOUND, "해당 팀이 존재하지 않습니다."),
    JOIN_REQUEST_NOT_FOUND(NOT_FOUND, "대기 중인 가입요청 건이 존재하지 않습니다."),
    INVITATION_NOT_FOUND(NOT_FOUND, "대기 중인 초대건이 존재하지 않습니다."),
    MANAGER_NOT_FOUND(NOT_FOUND, "운영진 정보가 존재하지 않습니다"),
    MY_TEAM_NOT_FOUND(NOT_FOUND, "소속팀 정보가 존재하지 않습니다"),
    REGULAR_EXERCISE_NOT_FOUND(NOT_FOUND, "정기운동 정보가 존재하지 않습니다"),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "로그아웃 된 사용자입니다"),

    /*403 FORBIDDEN : 서버에 정상적인 요청이 전송됐지만, 권한 문제로 거절*/
    INCORRECT_PASSWORD(FORBIDDEN,"비밀번호가 일치하지 않습니다"),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),
    ALREADY_EXIST_TEAM_MEMBER(CONFLICT, "이미 팀원으로 존재합니다"),
    ALREADY_EXIST_JOIN_REQUEST(CONFLICT, "아직 처리 대기중인 가입요청이 존재합니다."),

    /* 500 INTERNAL_SERVER_ERROR : null point에러 등 */
    INTERNAL_ERROR(INTERNAL_SERVER_ERROR, "서버 내부에서 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
