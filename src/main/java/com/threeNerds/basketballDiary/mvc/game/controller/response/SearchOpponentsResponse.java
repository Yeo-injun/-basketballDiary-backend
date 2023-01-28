package com.threeNerds.basketballDiary.mvc.game.controller.response;

import com.threeNerds.basketballDiary.mvc.game.dto.GameOpponentDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class SearchOpponentsResponse {

    private List<GameOpponentDTO> opponents;

    public SearchOpponentsResponse opponents(List<GameOpponentDTO> opponents) {
        this.opponents = opponents;
        return this;
    }
}
