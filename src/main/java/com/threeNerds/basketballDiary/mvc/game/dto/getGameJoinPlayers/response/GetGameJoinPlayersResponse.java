package com.threeNerds.basketballDiary.mvc.game.dto.getGameJoinPlayers.response;

import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinTeamDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinTeamInfoDTO;
import lombok.Getter;

import java.util.List;


@Getter
public class GetGameJoinPlayersResponse {
    private Long gameSeq;
    private GameJoinTeamDTO homeTeam;
    private GameJoinTeamDTO awayTeam;

    public GetGameJoinPlayersResponse ( Long gameSeq, GameJoinTeamDTO homeTeam, GameJoinTeamDTO awayTeam ) {
        this.gameSeq = gameSeq;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }
}
