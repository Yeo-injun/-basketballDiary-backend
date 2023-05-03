package com.threeNerds.basketballDiary.mvc.game.dto.createGameQuarterBasicInfo.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class CreateGameQuarterBasicInfoRequest {

    @NotNull
    private Long gameSeq;
    @NotEmpty
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
