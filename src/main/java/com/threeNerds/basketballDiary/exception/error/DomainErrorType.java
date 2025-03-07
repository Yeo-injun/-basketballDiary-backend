package com.threeNerds.basketballDiary.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum DomainErrorType implements ErrorMessageType {
    // TODO Domain별로 에러타입 관리하기 ( DomainErrorType을 상속받은 별도 클래스 생성 )
    /** 400 BAD_REQUEST : 잘못된 요청 */
    INSUFFICIENT_PLAYERS_ON_ENTRY(BAD_REQUEST, "엔트리에 등록할 선수가 부족합니다."),
    INSUFFICIENT_PLAYERS_ON_GAME(BAD_REQUEST, "경기에 참가하는 선수가 부족합니다."),
    INVALID_SEARCH_DATE_SPAN(BAD_REQUEST, "검색기간이 올바르지 않습니다. 검색기간을 확인해주세요."),
    INCORRECT_PASSWORD( BAD_REQUEST, "비밀번호가 일치하지 않습니다." ),
    INCORRECT_LOGIN_INFO( BAD_REQUEST,"입력한 ID 혹은 비밀번호가 일치하지 않습니다"),
    INVALID_GAME_TYPE( BAD_REQUEST, "유효하지 않은 경기유형입니다."),
    INVALID_HOME_AWAY_CODE( BAD_REQUEST, "유효하지 않은 홈/어웨이 코드입니다."),
    INVALID_STATE_FOR_MANAGER_AUTH( BAD_REQUEST, "관리자 권한으로 변경하기 위해서는 팀원이어야 합니다." ),
    INVALID_STATE_FOR_TEAM_MEMBER_AUTH( BAD_REQUEST, "팀원 권한으로 변경하기 위해서는 관리자여야 합니다." ),

    /** 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    JOIN_REQUEST_NOT_FOUND(NOT_FOUND, "대기 중인 가입요청 건이 존재하지 않습니다."),
    REGULAR_EXERCISE_NOT_FOUND(NOT_FOUND, "정기운동 정보가 존재하지 않습니다"),
    MANAGER_NOT_FOUND(NOT_FOUND, "운영진 정보가 존재하지 않습니다"),
    NOT_FOUND_ASSIGNED_TEAM(NOT_FOUND, "소속팀 정보가 존재하지 않습니다"),
    NOT_FOUND_TEAM_INFO(NOT_FOUND, "팀 정보가 존재하지 않습니다."),
    NOT_FOUND_GAME(NOT_FOUND,"경기 정보가 존재하지 않습니다."),
    NOT_FOUND_GAME_FOR_DELETE(NOT_FOUND,"삭제할 경기정보가 존재하지 않습니다."),
    NOT_FOUND_GAME_JOIN_TEAM(NOT_FOUND, "경기참가팀 정보가 존재하지 않습니다."),
    NOT_FOUND_HOME_TEAM(NOT_FOUND, "홈팀 정보가 존재하지 않습니다."),
    NOT_FOUND_AWAY_TEAM(NOT_FOUND, "어웨이팀 정보가 존재하지 않습니다."),
    NOT_FOUND_REJECT_INVITATION( NOT_FOUND, "거절할 팀초대정보가 존재하지 않습니다." ),
    USER_NOT_FOUND(NOT_FOUND, "사용자를 찾을 수 없습니다."),
    NO_EXIST_PASSWORD(NOT_FOUND, "비밀번호를 입력해주시기 바랍니다."),
    NO_JOIN_TEAM_MEMBER(NOT_FOUND, "팀에 소속되지 않은 팀원입니다."),
    NO_EXIST_GAME_JOIN_PLAYER(NOT_FOUND, "경기참가선수 정보가 존재하지 않습니다.") ,

    /** 403 FORBIDDEN : 서버에 정상적인 요청이 전송됐지만, 권한 문제로 거절 */
    ONLY_TEAM_MEMBER_QUERY( FORBIDDEN, "소속된 팀원만 조회할 수 있습니다." ),
    ONLY_CREATE_GAME_BY_TEAM_MEMBER(FORBIDDEN,"소속된 팀원만 경기를 생성할 수 있습니다."),
    ONLY_REMOVE_TEAM_BY_LEADER( FORBIDDEN, "팀장만 팀을 삭제할 수 있습니다."),
    ONLY_RECORD_BY_GAME_JOIN_PLAYER( FORBIDDEN, "경기에 참가하고 있는 선수만 경기 기록 권한을 부여받을 수 있습니다." ),
    CANT_DISMISSAL_MANAGER(FORBIDDEN, "관리자가 아닙니다. 팀장이나 팀원은 관리자에서 해임할 수 없습니다."),
    CANT_APPOINTMENT_MANAGER(FORBIDDEN, "팀원이 아닙니다. 팀원만 관리자로 임명할 수 있습니다."),
    CANT_ADD_QUARTER_RECORD( FORBIDDEN, "경기기록이 확정된 상태입니다. 쿼터기록을 입력할 수 없습니다." ),
    CANT_ADD_GAME_JOIN_PLAYER( FORBIDDEN, "경기참가선수를 변경할 수 없습니다. 참가선수는 경기의 참가팀이 확정된 상태에서만 변경할 수 있습니다."),
    ALREADY_GAME_CONFIRMED( FORBIDDEN, "경기 기록이 이미 확정된 상태입니다."),
    CANT_UPDATE_GAME_CONFIRMATION( FORBIDDEN, "경기 기록을 확정지을 수 없는 상태입니다. 경기 진행상태를 확인해주시기 바랍니다."),
    CANT_REMOVE_PLAYER_FOR_RECORDER( FORBIDDEN, "경기기록 권한을 가지고 있는 선수는 삭제할 수 없습니다."),
    CANT_DECISION_JOIN_REQUEST_BY_TEAM( FORBIDDEN, "팀이 처리할 수 있는 가입요청이 아닙니다."),
    INSUFFICIENT_GAME_JOIN_TEAMS( FORBIDDEN, "경기에 참가하는 팀의 수가 부족합니다."),
    ALREADY_EXIST_QUARTER_RECORDS(FORBIDDEN, "이미 쿼터기록이 입력되고 있어 선수 추가가 불가합니다."),
    ALREADY_EXIST_RECORD_AUTH(FORBIDDEN, "이미 경기기록권한을 가지고 있습니다."),
    CANT_REMOVE_GAME_RECORD_AUTH( FORBIDDEN, "기록권한을 가진 경기가 아닙니다." ),
    TEAM_LEADER_CANT_WITHDRAW( FORBIDDEN, "팀장은 팀탈퇴를 할 수 없습니다." ),


    /** 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_BACK_NUMBER(CONFLICT, "중복된 등번호가 존재합니다."),
    DUPLICATE_EMAIL( CONFLICT, "이메일은 중복될 수 없습니다." ),
    ALREADY_EXIST_JOIN_PLAYER( CONFLICT, "이미 존재하는 참가선수입니다." ),

    NOT_AVAILABLE_USER_ID(CONFLICT, "동일한 회원ID가 존재합니다."),
    ALREADY_EXIST_TEAM_MEMBER(CONFLICT, "이미 팀원으로 존재합니다."),
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
