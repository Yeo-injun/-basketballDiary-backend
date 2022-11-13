package com.threeNerds.basketballDiary.mvc.game.dto;

import lombok.Getter;

@Getter
public class SearchGameDTO {
    private Long gameSeq;
    private String quarterCode;
    private String homeAwayCode;

    public SearchGameDTO gameSeq(Long gameSeq) {
        this.gameSeq = gameSeq;
        return this;
    }

    public SearchGameDTO quarterCode(String quarterCode) {
        this.quarterCode = quarterCode;
        return this;
    }

    public SearchGameDTO homeAwayCode(String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        return this;
    }
}
