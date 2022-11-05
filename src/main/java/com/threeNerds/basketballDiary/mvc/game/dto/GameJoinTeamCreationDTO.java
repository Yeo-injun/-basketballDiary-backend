package com.threeNerds.basketballDiary.mvc.game.dto;

import lombok.Getter;

@Getter
public class GameJoinTeamCreationDTO {

    private Long gameJoinTeamSeq;       /* 게임참가팀Seq */
    private Long gameSeq;               /* 게임Seq */
    private String gameTypeCode;        /* 게임유형코드 */

    private Long opponentTeamSeq;       /* 상태팀Seq */

    private Long homeTeamSeq;           /* 홈팀Seq */
    private Long awayteamSeq;           /* 어웨이팀Seq */

    public GameJoinTeamCreationDTO gameJoinTeamSeq(Long gameJoinTeamSeq) {
        this.gameJoinTeamSeq = gameJoinTeamSeq;
        return this;
    }

    public GameJoinTeamCreationDTO gameSeq(Long gameSeq) {
        this.gameSeq = gameSeq;
        return this;
    }

}