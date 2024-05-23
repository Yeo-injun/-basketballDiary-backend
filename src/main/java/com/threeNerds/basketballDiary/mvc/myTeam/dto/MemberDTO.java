package com.threeNerds.basketballDiary.mvc.myTeam.dto;

import com.threeNerds.basketballDiary.constant.code.type.PlayerTypeCode;
import com.threeNerds.basketballDiary.constant.code.type.PositionCode;
import com.threeNerds.basketballDiary.constant.code.type.TeamAuthCode;
import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.Getter;

/**
 * Author: 강창기
 * 설명: 소속팀 Controller에서 팀원 및 운영진의 정보를 관리하는 DTO
 */

@Getter
public class MemberDTO {
    /* 페이징을 위한 총 row갯수 */
    private Integer totalCount;
    /* 페이징 처리를 위한 VO */
    private Pagination pagination;

    /* 선수유형코드 */
    private String playerTypeCode;
    /* 선수유형코드명 */
    private String playerTypeCodeName;

    /* 팀멤버 pk */
    private Long teamMemberSeq;
    /* 유저 pk */
    private Long userSeq;
    /* 이메일주소 */
    private String email;
    /* 팀 pk */
    private Long teamSeq;
    /* 팀권한코드 */
    // TODO DBResult를 매핑할때 @으로 해당 속성의 @Code( 코드Enum )을 참조하여 값 설정해주도록 AOP구현하기
    private String teamAuthCode;
    private String teamAuthCodeName;
    /* 포지션코드 */
    private String positionCode;
    private String positionCodeName;
    /* 이름 */
    private String userName;
    /* 팀이름 */
    private String teamName;
    /* 생년월일 */
    private String birthYmd;
    /* 신장 */
    private String height;
    /* 몸무게 */
    private String weight;
    /* 등번호 */
    private String backNumber;
    /* 가입일자 */
    private String joinYmd;
    /* 경기참여횟수 */
    private Integer totGame;


    public MemberDTO setPositionCode( String positionCode ) {
        this.positionCode       = positionCode;
        this.positionCodeName   = PositionCode.nameOf( this.positionCode );
        return this;
    }
    public MemberDTO setPlayerTypeCode( String playerTypeCode ) {
        this.playerTypeCode     = playerTypeCode;
        this.playerTypeCodeName = PlayerTypeCode.nameOf( this.playerTypeCode );
        return this;
    }
    public MemberDTO setTeamAuthCode( String teamAuthCode ) {
        this.teamAuthCode       = teamAuthCode;
        this.teamAuthCodeName   = TeamAuthCode.nameOf( this.teamAuthCode );
        return this;
    }


    /**
     * 조회조건 파라미터
     */
    public MemberDTO pagination( Pagination pagination ) {
        this.pagination = pagination;
        return this;
    }
    public MemberDTO teamSeq( Long teamSeq ) {
        this.teamSeq = teamSeq;
        return this;
    }
    public MemberDTO userName( String userName ) {
        this.userName = userName;
        return this;
    }

}
