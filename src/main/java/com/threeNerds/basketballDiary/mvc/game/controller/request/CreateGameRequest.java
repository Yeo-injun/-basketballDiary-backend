package com.threeNerds.basketballDiary.mvc.game.controller.request;

import com.threeNerds.basketballDiary.mvc.game.dto.GameCreationDTO;
import lombok.Getter;

@Getter
public class CreateGameRequest {

    private GameCreationDTO gameCreationInfo;
}
