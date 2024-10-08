package com.threeNerds.basketballDiary.mvc.game.mapper.dto;

import lombok.Getter;

@Getter
public class SearchEntryDTO {
    private Long gameSeq;
    private Long gameJoinTeamSeq;
    private String homeAwayCode;
    private String quarterCode;

    public SearchEntryDTO gameSeq(Long gameSeq) {
        this.gameSeq = gameSeq;
        return this;
    }

    public SearchEntryDTO gameJoinTeamSeq(Long gameJoinTeamSeq) {
        this.gameJoinTeamSeq = gameJoinTeamSeq;
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
