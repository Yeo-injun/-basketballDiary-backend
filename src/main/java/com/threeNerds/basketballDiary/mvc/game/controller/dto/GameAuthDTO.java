package com.threeNerds.basketballDiary.mvc.game.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class GameAuthDTO {

    private Long gameSeq;

    private String auth;

    public GameAuthDTO gameSeq(Long gameSeq){
        this.gameSeq = gameSeq;
        return this;
    }

    public GameAuthDTO auth(String auth){
        this.auth = auth;
        return this;
    }
}
