package com.threeNerds.basketballDiary.mvc.team.mapper.dto;

import com.threeNerds.basketballDiary.constant.code.type.PlayerTypeCode;
import com.threeNerds.basketballDiary.constant.code.type.PositionCode;
import com.threeNerds.basketballDiary.constant.code.type.TeamAuthCode;
import lombok.Getter;


@Getter
public class ProfileDTO {

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
    /* 선수유형코드 */
    private String playerTypeCode;
    private String playerTypeCodeName;
    /* 포지션코드 */
    private String positionCode;
    private String positionCodeName;

    /* 프로필 이미지 경로 */
    private String profileImagePath;
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

    private ProfileDTO( Long userSeq, Long teamSeq ) {
        this.userSeq = userSeq;
        this.teamSeq = teamSeq;
    }

    public static ProfileDTO ofParam( Long userSeq, Long teamSeq ) {
        return new ProfileDTO( userSeq, teamSeq );
    }

    public void setTeamAuthCode( String teamAuthCode ) {
        this.teamAuthCode     = teamAuthCode;
        this.teamAuthCodeName = TeamAuthCode.nameOf( teamAuthCode );
    }
    public void setPlayerType( String playerTypeCode ) {
        this.playerTypeCode     = playerTypeCode;
        this.playerTypeCodeName = PlayerTypeCode.nameOf( playerTypeCode );
    }
    public void setPositionCode( String positionCode ) {
        this.positionCode     = positionCode;
        this.positionCodeName = PositionCode.nameOf( positionCode );
    }


}
