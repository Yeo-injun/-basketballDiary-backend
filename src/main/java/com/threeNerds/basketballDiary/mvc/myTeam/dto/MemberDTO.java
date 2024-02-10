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

    public MemberDTO setPlayerType(PlayerTypeCode codeEnum) {
        this.playerTypeCode = codeEnum.getCode();
        this.playerTypeCodeName = codeEnum.getName();
        return this;
    }

    public MemberDTO userSeq (Long userSeq) {
        this.userSeq = userSeq;
        return this;
    }

    public MemberDTO teamMemberSeq (Long teamMemberSeq) {
        this.teamMemberSeq = teamMemberSeq;
        return this;
    }

    public MemberDTO teamSeq (Long teamSeq) {
        this.teamSeq = teamSeq;
        return this;
    }

    public MemberDTO teamAuthCode (String teamAuthCode) {
        this.teamAuthCode = teamAuthCode;
        return this;
    }

    public MemberDTO teamAuthCodeName () {
        this.teamAuthCodeName = TeamAuthCode.nameOf(this.teamAuthCode);
        return this;
    }

    public MemberDTO positionCode (String positionCode) {
        this.positionCode = positionCode;
        return this;
    }

    public MemberDTO positionCodeName () {
        this.positionCodeName = PositionCode.nameOf(this.positionCode);
        return this;
    }

    public MemberDTO userName (String userName) {
        this.userName = userName;
        return this;
    }

    public MemberDTO teamName(String teamName){
        this.teamName = teamName;
        return this;
    }

    public MemberDTO birthYmd (String birthYmd) {
        this.birthYmd = birthYmd;
        return this;
    }

    public MemberDTO height (String height) {
        this.height = height;
        return this;
    }

    public MemberDTO weight (String weight) {
        this.weight = weight;
        return this;
    }

    public MemberDTO backNumber (String backNumber) {
        this.backNumber = backNumber;
        return this;
    }

    public MemberDTO joinYmd (String joinYmd) {
        this.joinYmd = joinYmd;
        return this;
    }

    public MemberDTO totGame (Integer totGame) {
        this.totGame = totGame;
        return this;
    }
    public MemberDTO pagination( Pagination pagination ) {
        this.pagination = pagination;
        return this;
    }

    public MemberDTO setAllCodeName() {
        teamAuthCodeName();
        positionCodeName();
        return this;
    }

}
