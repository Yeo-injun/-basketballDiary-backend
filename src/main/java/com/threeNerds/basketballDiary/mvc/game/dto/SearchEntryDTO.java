package com.threeNerds.basketballDiary.mvc.game.dto;

import lombok.Getter;

@Getter
public class SearchEntryDTO {

    private Long gameSeq;
    private String homeAwayCode;
    private String quarterCode;

    public SearchEntryDTO gameSeq(Long gameSeq) {
        this.gameSeq = gameSeq;
        return this;
    }

    public SearchEntryDTO homeAwayCode(String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        return this;
    }

    public SearchEntryDTO quarterCode(String quarterCode) {
        this.quarterCode = quarterCode;
        return this;
    }
}
