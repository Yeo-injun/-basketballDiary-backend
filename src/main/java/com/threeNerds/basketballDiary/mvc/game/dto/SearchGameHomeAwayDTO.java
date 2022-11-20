package com.threeNerds.basketballDiary.mvc.game.dto;

import lombok.Getter;

@Getter
public class SearchGameHomeAwayDTO {

    private Long gameSeq;

    private String homeAwayCode;

    public SearchGameHomeAwayDTO gameSeq(Long gameSeq){
        this.gameSeq = gameSeq;
        return this;
    }

    public SearchGameHomeAwayDTO homeAwayCode(String homeAwayCode){
        this.homeAwayCode = homeAwayCode;
        return this;
    }
}
