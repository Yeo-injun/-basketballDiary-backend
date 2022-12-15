package com.threeNerds.basketballDiary.mvc.game.controller.request;

import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinPlayerDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class RegisterGameJoinPlayersRequest {

    private List<GameJoinPlayerDTO> gameJoinPlayers;
}
