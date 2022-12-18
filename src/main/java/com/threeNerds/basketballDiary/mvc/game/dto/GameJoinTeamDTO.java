package com.threeNerds.basketballDiary.mvc.game.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class GameJoinTeamDTO {

    private Long gameJoinTeamSeq;
    private String homeAwayCode;
    private String homeAwayCodeName;
    private List<PlayerInfoDTO> players;

    public GameJoinTeamDTO gameJoinTeamSeq(Long gameJoinTeamSeq) {
        this.gameJoinTeamSeq = gameJoinTeamSeq;
        return this;
    }

    public GameJoinTeamDTO homeAwayCode(String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        return this;
    }

    public GameJoinTeamDTO homeAwayCodeName(String homeAwayCodeName) {
        this.homeAwayCodeName = homeAwayCodeName;
        return this;
    }

    public GameJoinTeamDTO players(List<PlayerInfoDTO> players) {
        this.players = players;
        return this;
    }
}
