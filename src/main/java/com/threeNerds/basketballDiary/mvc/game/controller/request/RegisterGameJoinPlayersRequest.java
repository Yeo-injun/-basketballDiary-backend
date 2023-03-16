package com.threeNerds.basketballDiary.mvc.game.controller.request;

import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinPlayerDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RegisterGameJoinPlayersRequest {

    private Long gameSeq;
    private Long gameJoinTeamSeq;
    private List<GameJoinPlayerDTO> gameJoinPlayers;

    public RegisterGameJoinPlayersRequest( Long gameSeq, Long gameJoinTeamSeq, List<GameJoinPlayerDTO> gameJoinPlayers ) {
        this.gameSeq = gameSeq;
        this.gameJoinTeamSeq = gameJoinTeamSeq;
        this.gameJoinPlayers = gameJoinPlayers;
    }
}
