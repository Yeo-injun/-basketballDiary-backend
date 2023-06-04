package com.threeNerds.basketballDiary.mvc.game.controller.request;

import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinPlayerDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class RegisterGameJoinPlayersRequest {

    @NotNull private Long gameSeq;
    private String homeAwayCode;
    @NotNull private List<GameJoinPlayerDTO> gameJoinPlayers;

    public RegisterGameJoinPlayersRequest( Long gameSeq, String homeAwayCode, List<GameJoinPlayerDTO> gameJoinPlayers ) {
        this.gameSeq = gameSeq;
        this.homeAwayCode = homeAwayCode;
        this.gameJoinPlayers = gameJoinPlayers;
    }
}
