package com.threeNerds.basketballDiary.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum DomainErrorType implements ErrorMessageType {

    /** SystemException으로 분리할 Error내용 */
    NO_PARAMETER(BAD_REQUEST, "'{0}' 파라미터가 없습니다."),
    INVALID_PARAMETER(BAD_REQUEST, "유효하지 않은 파라미터입니다."),
    /** 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    UNAUTHORIZED_ACCESS(UNAUTHORIZED, "접근 권한이 없습니다."),
    LOGIN_REQUIRED(UNAUTHORIZED, "로그인이 필요합니다"),

    NO_EXIST_PASSWORD(NOT_FOUND, "비밀번호를 입력해주시기 바랍니다."),

    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "로그아웃 된 사용자입니다"),
    INCORRECT_PASSWORD(FORBIDDEN,"비밀번호가 일치하지 않습니다"),
    NOT_AVAILABLE_USER_ID( CONFLICT, "동일한 회원ID가 존재합니다."),
    // TODO 위의 Error 항목들은 추후 별도 클래스로 이동


    /** 400 BAD_REQUEST : 잘못된 요청 */
    INSUFFICIENT_PLAYERS_ON_ENTRY(BAD_REQUEST, "엔트리에 등록할 선수가 부족합니다."),
    INSUFFICIENT_PLAYERS_ON_GAME(BAD_REQUEST, "게임에 참가한 선수가 부족합니다."),

    /** 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    JOIN_REQUEST_NOT_FOUND(NOT_FOUND, "대기 중인 가입요청 건이 존재하지 않습니다."),
    INVITATION_NOT_FOUND(NOT_FOUND, "대기 중인 초대건이 존재하지 않습니다."),

    REGULAR_EXERCISE_NOT_FOUND(NOT_FOUND, "정기운동 정보가 존재하지 않습니다"),

    MANAGER_NOT_FOUND(NOT_FOUND, "운영진 정보가 존재하지 않습니다"),
    MY_TEAM_NOT_FOUND(NOT_FOUND, "소속팀 정보가 존재하지 않습니다"),
    TEAM_NOT_FOUND(NOT_FOUND, "해당 팀이 존재하지 않습니다."),

    NOT_FOUND_GAME(NOT_FOUND,"게임 정보가 존재하지 않습니다."),
    NOT_FOUND_GAME_JOIN_TEAM(NOT_FOUND, "게임참가팀 정보가 존재하지 않습니다."),
    NOT_FOUND_HOME_TEAM(NOT_FOUND, "홈팀 정보가 존재하지 않습니다."),
    NOT_FOUND_AWAY_TEAM(NOT_FOUND, "어웨이팀 정보가 존재하지 않습니다."),

    USER_NOT_FOUND(NOT_FOUND, "해당 사용자를 찾을 수 없습니다"),

    /** 403 FORBIDDEN : 서버에 정상적인 요청이 전송됐지만, 권한 문제로 거절 */
    ONLY_TEAM_MEMBER_HANDLE(FORBIDDEN,"해당팀에 소속된 팀원만 처리할 수 있습니다."),

    CANT_DISMISSAL_MANAGER(FORBIDDEN, "관리자가 아닙니다. 팀장이나 팀원은 관리자에서 해임할 수 없습니다."),
    CANT_APPOINTMENT_MANAGER(FORBIDDEN, "팀원이 아닙니다. 팀원만 관리자로 임명할 수 있습니다."),

    CANT_ADD_QUARTER_RECORD( FORBIDDEN, "경기기록이 확정된 상태입니다. 쿼터기록을 입력할 수 없습니다." ),
    CANT_ADD_GAME_JOIN_PLAYER( FORBIDDEN, "경기기록이 확정된 상태입니다. 경기참가선수를 변경할 수 없습니다."),
    ALREADY_GAME_CONFIRMED( FORBIDDEN, "해당 경기는 이미 확정된 상태입니다."),
    CANT_UPDATE_GAME_CONFIRMATION( FORBIDDEN, "경기기록을 확정 상태로 바꿀 수 없습니다. 경기기록 상태를 확인해주시기 바랍니다."),

    INSUFFICIENT_GAME_JOIN_TEAMS( FORBIDDEN, "게임참가팀의 수가 부족합니다."),
    INVALID_REGISTER_PLAYERS_FOR_ALREADY_HAS_RECORDS(FORBIDDEN, "이미 쿼터기록이 입력되고 있어 선수 추가가 불가합니다."),

    /** 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_BACK_NUMBER(CONFLICT, "중복된 등번호가 존재합니다."),

    ALREADY_EXIST_TEAM_MEMBER(CONFLICT, "이미 팀원으로 존재합니다"),
    ALREADY_EXIST_JOIN_REQUEST(CONFLICT, "아직 처리 대기중인 가입요청이 존재합니다."),
    ALREADY_EXIST_JOIN_TEAM(CONFLICT, "이미 게임참가팀이 존재합니다.");

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
