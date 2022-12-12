package com.threeNerds.basketballDiary.mvc.game.dto;

import lombok.Getter;

@Getter
public class PlayerInfoDTO {

    private Long gameSeq;
    private Long gameJoinTeamSeq;
    private Long teamSeq;

    private Long gameJoinPlayerSeq;
    private Long userSeq;

    private String playerTypeCode;
    private String playerTypeCodeName;
    private String homeAwayCode;

    private String teamName;
    private String playerName;

    private String backNumber;
    private String positionCode;
    private String positionCodeName;

    private String email;

    public PlayerInfoDTO gameJoinTeamSeq(Long gameJoinTeamSeq) {
        this.gameJoinTeamSeq = gameJoinTeamSeq;
        return this;
    }
}
