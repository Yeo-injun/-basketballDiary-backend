package com.threeNerds.basketballDiary.mvc.game.dto.deleteGameQuarter.request;

import lombok.Getter;

@Getter
public class DeleteGameQuarterRequest {

    private Long gameSeq;
    private String quarterCode;

    public DeleteGameQuarterRequest gameSeq(Long gameSeq) {
        this.gameSeq = gameSeq;
        return this;
    }

    public DeleteGameQuarterRequest quarterCode(String quarterCode) {
        this.quarterCode = quarterCode;
        return this;
    }
}
