package com.threeNerds.basketballDiary.mvc.game.dto;

import lombok.Getter;

@Getter
public class SearchMatchPlayersDTO {

    private Long gameSeq;

    private String homeAwayCode;

    public SearchMatchPlayersDTO gameSeq(Long gameSeq){
        this.gameSeq = gameSeq;
        return this;
    }

    public SearchMatchPlayersDTO homeAwayCode(String homeAwayCode){
        this.homeAwayCode = homeAwayCode;
        return this;
    }
}
