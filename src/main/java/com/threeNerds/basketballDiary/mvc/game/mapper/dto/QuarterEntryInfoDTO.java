package com.threeNerds.basketballDiary.mvc.game.mapper.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class QuarterEntryInfoDTO {

    private Long gameSeq;
    private Long gameJoinTeamSeq;
    private String quarterCode;
    private String homeAwayCode;
    private List<PlayerInfoDTO> playerList;

    public QuarterEntryInfoDTO gameSeq(Long gameSeq) {
        this.gameSeq = gameSeq;
        return this;
    }

    public QuarterEntryInfoDTO gameJoinTeamSeq(Long gameJoinTeamSeq) {
        this.gameJoinTeamSeq = gameJoinTeamSeq;
        return this;
    }

    public QuarterEntryInfoDTO quarterCode(String quarterCode) {
        this.quarterCode = quarterCode;
        return this;
    }

    public QuarterEntryInfoDTO homeAwayCode(String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        return this;
    }

    public QuarterEntryInfoDTO playerList(List<PlayerInfoDTO> playerList) {
        this.playerList = playerList;
        return this;
    }
}
