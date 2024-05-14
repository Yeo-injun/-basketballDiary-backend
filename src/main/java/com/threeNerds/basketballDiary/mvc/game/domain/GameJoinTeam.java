package com.threeNerds.basketballDiary.mvc.game.domain;

import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameJoinTeam {

    private Long gameJoinTeamSeq;   // 게임참가팀Seq
    private Long gameSeq;           // 게임Seq
    private Long teamSeq;           // 팀Seq
    private String teamName;        // 팀명
    private String homeAwayCode;    // 홈/어웨이 코드

    private static final String HOME_CODE = HomeAwayCode.HOME_TEAM.getCode();
    private static final String AWAY_CODE = HomeAwayCode.AWAY_TEAM.getCode();

    private static final String HOME_NAME_PREFIX = "HOME_";
    private static final String AWAY_NAME_PREFIX = "AWAY_";

    public void inSelfGame() {
        switch ( HomeAwayCode.typeOf( this.homeAwayCode ) ) {
            case HOME_TEAM : this.teamName = HOME_NAME_PREFIX + this.teamName; break;
            case AWAY_TEAM : this.teamName = AWAY_NAME_PREFIX + this.teamName; break;
        }
    }


}
