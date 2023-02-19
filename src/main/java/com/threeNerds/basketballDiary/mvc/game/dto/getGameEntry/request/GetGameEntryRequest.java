package com.threeNerds.basketballDiary.mvc.game.dto.getGameEntry.request;

import lombok.Getter;

@Getter
public class GetGameEntryRequest {

    private Long gameSeq;
    private String homeAwayCode;
    private String quarterCode;

    public GetGameEntryRequest gameSeq(Long gameSeq) {
        this.gameSeq = gameSeq;
        return this;
    }

    public GetGameEntryRequest homeAwayCode(String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        return this;
    }

    public GetGameEntryRequest quarterCode(String quarterCode) {
        this.quarterCode = quarterCode;
        return this;
    }
}
