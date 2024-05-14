package com.threeNerds.basketballDiary.mvc.game.controller.response;

import lombok.Getter;

@Getter
public class CreateGameResponse {
    private Long gameSeq;

    public CreateGameResponse( Long gameSeq ) {
        this.gameSeq = gameSeq;
    }
}
