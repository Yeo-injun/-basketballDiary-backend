package com.threeNerds.basketballDiary.mvc.game.controller.request;

import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinPlayerDTO;
import com.threeNerds.basketballDiary.mvc.game.service.dto.GameJoinPlayerCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class RegisterGameJoinPlayersRequest {

    @NotNull
    private List<GameJoinPlayerDTO> gameJoinPlayers;

    public GameJoinPlayerCommand toCommand( Long gameSeq, String homeAwayCode ) {
        return GameJoinPlayerCommand.builder()
                .gameSeq(           gameSeq )
                .homeAwayCode(      homeAwayCode )
                .gameJoinPlayers(   this.getGameJoinPlayers() )
                .build();
    }
}
