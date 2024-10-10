package com.threeNerds.basketballDiary.mvc.team.controller.response;

import com.threeNerds.basketballDiary.mvc.team.service.dto.ProfileQuery;
import lombok.Getter;

@Getter
public class GetProfileResponse {

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
    private Integer totalGameCount;

     public GetProfileResponse( ProfileQuery.Result result ) {
        this.teamMemberSeq          = result.getTeamMemberSeq();
        this.userSeq                = result.getUserSeq();
        this.teamSeq                = result.getTeamSeq();
        this.teamName               = result.getTeamName();
        this.teamAuthCode           = result.getTeamAuthCode();
        this.teamAuthCodeName       = result.getTeamAuthCodeName();
        this.memberImagePath        = result.getMemberImagePath();
        this.playerTypeCode         = result.getPlayerTypeCode();
        this.playerTypeCodeName     = result.getPlayerTypeCodeName();
        this.positionCode           = result.getPositionCode();
        this.positionCodeName       = result.getPositionCodeName();
        this.userName               = result.getUserName();
        this.birthYmd               = result.getBirthYmd();
        this.height                 = result.getHeight();
        this.weight                 = result.getWeight();
        this.backNumber             = result.getBackNumber();
        this.joinYmd                = result.getJoinYmd();
        this.totalGameCount         = result.getTotGame();
    }
}
