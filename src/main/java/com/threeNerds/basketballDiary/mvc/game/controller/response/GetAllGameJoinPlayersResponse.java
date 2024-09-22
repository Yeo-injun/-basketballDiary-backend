package com.threeNerds.basketballDiary.mvc.game.controller.response;

import com.threeNerds.basketballDiary.mvc.game.dto.PlayerInfoDTO;
import lombok.Getter;

import java.util.List;


@Getter
public class GetAllGameJoinPlayersResponse {
    private Long gameSeq;
    private List<PlayerInfoDTO> homePlayers;
    private List<PlayerInfoDTO> awayPlayers;

    public GetAllGameJoinPlayersResponse ( Long gameSeq, List<PlayerInfoDTO> homePlayers, List<PlayerInfoDTO> awayPlayers ) {
        this.gameSeq = gameSeq;
        this.homePlayers = homePlayers;
        this.awayPlayers = awayPlayers;
    }
}
