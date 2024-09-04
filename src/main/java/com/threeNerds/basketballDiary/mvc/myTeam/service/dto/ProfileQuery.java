package com.threeNerds.basketballDiary.mvc.myTeam.service.dto;

import com.threeNerds.basketballDiary.constant.code.type.JoinRequestStateCode;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.ProfileDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.TeamJoinRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileQuery {
    private Long userSeq;
    private Long teamSeq;

    @Getter
    public class Result {

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
        Result( ProfileDTO profile ) {
            this.teamMemberSeq          = profile.getTeamMemberSeq();
            this.userSeq                = profile.getUserSeq();
            this.teamSeq                = profile.getTeamSeq();
            this.teamName               = profile.getTeamName();
            this.teamAuthCode           = profile.getTeamAuthCode();
            this.teamAuthCodeName       = profile.getTeamAuthCodeName();
            this.memberImagePath        = profile.getProfileImagePath(); // TODO 연계명도 바꿀 필요 있음
            this.playerTypeCode         = profile.getPlayerTypeCode();
            this.playerTypeCodeName     = profile.getPlayerTypeCodeName();
            this.positionCode           = profile.getPositionCode();
            this.positionCodeName       = profile.getPositionCodeName();
            this.userName               = profile.getUserName();
            this.birthYmd               = profile.getBirthYmd();
            this.height                 = profile.getHeight();
            this.weight                 = profile.getWeight();
            this.backNumber             = profile.getBackNumber();
            this.joinYmd                = profile.getJoinYmd();
            this.totGame                = profile.getTotGame();
        }
    }
    public ProfileQuery.Result buildResult( ProfileDTO profile ) {
        return new Result( profile );
    }
}
