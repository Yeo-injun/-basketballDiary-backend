package com.threeNerds.basketballDiary.mvc.game.dto;

import lombok.Getter;

@Getter
public class QuarterCodeDTO {

    private Long gameSeq;

    private String quaterCode;

    public QuarterCodeDTO gameSeq(Long gameSeq){
        this.gameSeq = gameSeq;
        return this;
    }

    public QuarterCodeDTO quaterCodeDTO(String quaterCode){
        this.quaterCode = quaterCode;
        return this;
    }
}
