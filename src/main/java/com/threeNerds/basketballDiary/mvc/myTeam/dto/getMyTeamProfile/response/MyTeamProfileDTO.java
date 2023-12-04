package com.threeNerds.basketballDiary.mvc.myTeam.dto.getMyTeamProfile.response;

import com.threeNerds.basketballDiary.constant.code.PlayerTypeCode;
import com.threeNerds.basketballDiary.constant.code.PositionCode;
import com.threeNerds.basketballDiary.constant.code.TeamAuthCode;
import lombok.Getter;


@Getter
public class MyTeamProfileDTO {

    /* 팀멤버 pk */
    private Long teamMemberSeq;
    /* 유저 pk */
    private Long userSeq;
    /* 팀 pk */
    private Long teamSeq;

    /* 팀이름 */
    private String teamName;
    /* 팀권한코드 */
    private String teamAuthCode;
    private String teamAuthCodeName;

    /* 프로필 이미지 경로 */
    private String memberImagePath;
    /* 선수유형코드 */
    private String playerTypeCode;
    /* 선수유형코드명 */
    private String playerTypeCodeName;

    /* 포지션코드 */
    private String positionCode;
    private String positionCodeName;
    /* 이름 */
    private String userName;
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


    public MyTeamProfileDTO setPlayerType(PlayerTypeCode codeEnum) {
        this.playerTypeCode = codeEnum.getCode();
        this.playerTypeCodeName = codeEnum.getName();
        return this;
    }

    public MyTeamProfileDTO userSeq (Long userSeq) {
        this.userSeq = userSeq;
        return this;
    }

    public MyTeamProfileDTO teamMemberSeq (Long teamMemberSeq) {
        this.teamMemberSeq = teamMemberSeq;
        return this;
    }

    public MyTeamProfileDTO teamSeq (Long teamSeq) {
        this.teamSeq = teamSeq;
        return this;
    }

    public MyTeamProfileDTO teamAuthCode (String teamAuthCode) {
        this.teamAuthCode = teamAuthCode;
        return this;
    }

    public MyTeamProfileDTO teamAuthCodeName () {
        this.teamAuthCodeName = TeamAuthCode.nameOf(this.teamAuthCode);
        return this;
    }

    public MyTeamProfileDTO positionCode (String positionCode) {
        this.positionCode = positionCode;
        return this;
    }

    public MyTeamProfileDTO positionCodeName () {
        this.positionCodeName = PositionCode.nameOf(this.positionCode);
        return this;
    }

    public MyTeamProfileDTO userName (String userName) {
        this.userName = userName;
        return this;
    }

    public MyTeamProfileDTO teamName(String teamName){
        this.teamName = teamName;
        return this;
    }

    public MyTeamProfileDTO birthYmd (String birthYmd) {
        this.birthYmd = birthYmd;
        return this;
    }

    public MyTeamProfileDTO height (String height) {
        this.height = height;
        return this;
    }

    public MyTeamProfileDTO weight (String weight) {
        this.weight = weight;
        return this;
    }

    public MyTeamProfileDTO backNumber (String backNumber) {
        this.backNumber = backNumber;
        return this;
    }

    public MyTeamProfileDTO joinYmd (String joinYmd) {
        this.joinYmd = joinYmd;
        return this;
    }

    public MyTeamProfileDTO totGame (Integer totGame) {
        this.totGame = totGame;
        return this;
    }

    public MyTeamProfileDTO setAllCodeName() {
        teamAuthCodeName();
        positionCodeName();
        return this;
    }

}
