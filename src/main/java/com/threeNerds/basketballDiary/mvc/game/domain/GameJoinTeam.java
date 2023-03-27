package com.threeNerds.basketballDiary.mvc.game.domain;

import com.threeNerds.basketballDiary.constant.code.HomeAwayCode;
import com.threeNerds.basketballDiary.mvc.team.domain.Team;
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

    public static GameJoinTeam create(Long gameSeq, HomeAwayCode homeAwayCode, Team team) {
        return createJoinTeam(gameSeq, team.getTeamSeq(), team.getTeamName(), homeAwayCode.getCode());
    }

    public static GameJoinTeam createHomeTeamForSelfGame(Long gameSeq, Team gameCreatorTeam)
    {
        String homeTeamNameInSelfGame = "HOME_" + gameCreatorTeam.getTeamName();
        return createJoinTeam(gameSeq, gameCreatorTeam.getTeamSeq(), homeTeamNameInSelfGame, HomeAwayCode.HOME_TEAM.getCode());
    }

    public static GameJoinTeam createAwayTeamForSelfGame(Long gameSeq, Team gameCreatorTeam)
    {
        String awayTeamNameInSelfGame = "AWAY_" + gameCreatorTeam.getTeamName();
        return createJoinTeam(gameSeq, gameCreatorTeam.getTeamSeq(), awayTeamNameInSelfGame, HomeAwayCode.AWAY_TEAM.getCode());
    }

    private static GameJoinTeam createJoinTeam(Long gameSeq, Long teamSeq, String teamName, String homeAwayCode)
    {
        return new GameJoinTeam().builder()
                .gameSeq(gameSeq)
                .teamSeq(teamSeq)
                .teamName(teamName)
                .homeAwayCode(homeAwayCode)
                .build();
    }
}
