package com.threeNerds.basketballDiary.mvc.game.controller.dto;

import lombok.Getter;

@Getter
public class QuarterEntryDTO {

    private Long gameSeq;
    private Long gameJoinTeamSeq;
    private Long gameJoinPlayerSeq;
    private Long userSeq;

    public QuarterEntryDTO gameSeq(Long gameSeq) {
        this.gameSeq = gameSeq;
        return this;
    }

    public QuarterEntryDTO gameJoinTeamSeq(Long gameJoinTeamSeq) {
        this.gameJoinTeamSeq = gameJoinTeamSeq;
        return this;
    }

    public QuarterEntryDTO gameJoinPlayerSeq(Long gameJoinPlayerSeq) {
        this.gameJoinPlayerSeq = gameJoinPlayerSeq;
        return this;
    }

    public QuarterEntryDTO userSeq(Long userSeq) {
        this.userSeq = userSeq;
        return this;
    }
}
