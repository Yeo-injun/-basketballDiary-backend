package com.threeNerds.basketballDiary.mvc.game.controller.response;

import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinTeamDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.PlayerInfoDTO;
import lombok.Getter;

import java.util.List;


@Getter
public class GetAllGameJoinPlayersResponse {
    private Long gameSeq;
    private GameJoinTeamDTO homeTeam;
    private GameJoinTeamDTO awayTeam;

    public GetAllGameJoinPlayersResponse ( Long gameSeq, GameJoinTeamDTO homeTeam, GameJoinTeamDTO awayTeam ) {
        this.gameSeq = gameSeq;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }
}
