package com.threeNerds.basketballDiary.mvc.game.dto.confirmGameJoinTeam.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class ConfirmGameJoinTeamRequest {

    private Long gameSeq;               /* 게임Seq */
    @NotEmpty
    private String gameTypeCode;        /* 게임유형코드 */
    private Long opponentTeamSeq;       /* 상태팀Seq */

}
