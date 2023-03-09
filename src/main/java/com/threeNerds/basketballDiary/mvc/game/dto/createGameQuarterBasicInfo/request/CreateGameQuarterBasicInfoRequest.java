package com.threeNerds.basketballDiary.mvc.game.dto.createGameQuarterBasicInfo.request;

import lombok.Getter;

@Getter
public class CreateGameQuarterBasicInfoRequest {

    private Long gameSeq;
    private String quarterCode;

    public CreateGameQuarterBasicInfoRequest gameSeq(Long gameSeq) {
        this.gameSeq = gameSeq;
        return this;
    }

    public CreateGameQuarterBasicInfoRequest quarterCode(String quarterCode) {
        this.quarterCode = quarterCode;
        return this;
    }
}
