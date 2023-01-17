package com.threeNerds.basketballDiary.mvc.game.dto;

import lombok.Getter;

@Getter
public class SearchGameJoinTeamDTO {

    private Long gameSeq;

    private String homeAwayCode;

    public SearchGameJoinTeamDTO gameSeq(Long gameSeq) {
        this.gameSeq = gameSeq;
        return this;
    }

    public SearchGameJoinTeamDTO homeAwayCode(String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        return this;
    }
}
