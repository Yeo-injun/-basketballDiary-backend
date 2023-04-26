package com.threeNerds.basketballDiary.mvc.game.dto;

import lombok.Getter;

@Getter
public class
SearchPlayersDTO {

    private Long gameSeq;
    private String homeAwayCode;

    public SearchPlayersDTO gameSeq(Long gameSeq) {
        this.gameSeq = gameSeq;
        return this;
    }

    public SearchPlayersDTO homeAwayCode(String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        return this;
    }

}
