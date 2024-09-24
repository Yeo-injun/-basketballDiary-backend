package com.threeNerds.basketballDiary.mvc.game.domain;

import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
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

    public void joinInSelfGame() {
        this.teamName = getTeamNamePrefixByHomeAway() + this.teamName;
    }

    public void joinInMatchGame() {
        this.teamName = this.teamName.replace( this.getTeamNamePrefixByHomeAway() , "" );
    }

    public boolean isHomeTeam() {
        return HOME_CODE.equals( this.homeAwayCode );
    }

    private String getTeamNamePrefixByHomeAway() {
        switch ( HomeAwayCode.typeOf( this.homeAwayCode ) ) {
            case HOME_TEAM : return HOME_NAME_PREFIX;
            case AWAY_TEAM : return AWAY_NAME_PREFIX;
            default:
                throw new CustomException( DomainErrorType.INVALID_HOME_AWAY_CODE );
        }
    }

    public GameJoinTeam toAwayInSelfGameType() {
        if ( HomeAwayCode.HOME_TEAM != HomeAwayCode.typeOf( this.homeAwayCode ) ) {
            throw new CustomException( DomainErrorType.INVALID_HOME_AWAY_CODE );
        }
        return GameJoinTeam.builder()
                .gameSeq( this.gameSeq )
                .teamSeq( this.teamSeq )
                .teamName( AWAY_NAME_PREFIX + this.teamName )
                .homeAwayCode( HomeAwayCode.AWAY_TEAM.getCode() )
                .build();
    }



}
