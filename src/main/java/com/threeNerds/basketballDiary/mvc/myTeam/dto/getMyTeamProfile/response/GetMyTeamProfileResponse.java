package com.threeNerds.basketballDiary.mvc.myTeam.dto.getMyTeamProfile.response;

import com.threeNerds.basketballDiary.http.ResponseJsonBody;
import lombok.Getter;

@Getter
public class GetMyTeamProfileResponse extends ResponseJsonBody {

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

    public GetMyTeamProfileResponse toResponse( MyTeamProfileDTO dto ) {
        this.teamMemberSeq          = dto.getTeamMemberSeq();
        this.userSeq                = dto.getUserSeq();
        this.teamSeq                = dto.getTeamSeq();
        this.teamName               = dto.getTeamName();
        this.teamAuthCode           = dto.getTeamAuthCode();
        this.teamAuthCodeName       = dto.getTeamAuthCodeName();
        this.memberImagePath        = dto.getMemberImagePath();
        this.playerTypeCode         = dto.getPlayerTypeCode();
        this.playerTypeCodeName     = dto.getPlayerTypeCodeName();
        this.positionCode           = dto.getPositionCode();
        this.positionCodeName       = dto.getPositionCodeName();
        this.userName               = dto.getUserName();
        this.birthYmd               = dto.getBirthYmd();
        this.height                 = dto.getHeight();
        this.weight                 = dto.getWeight();
        this.backNumber             = dto.getBackNumber();
        this.joinYmd                = dto.getJoinYmd();
        this.totGame                = dto.getTotGame();
        return this;
    }
}
