package com.threeNerds.basketballDiary.mvc.game.controller.request;

import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinTeamCreationDTO;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class ConfirmGameJoinTeamRequest {

    @NotNull
    private Long gameSeq;               /* 게임Seq */
    @NotNull
    private String gameTypeCode;        /* 게임유형코드 */

    @NotNull
    private Long gameJoinTeamSeq;       /* 게임참가팀Seq */
    @NotNull
    private Long opponentTeamSeq;       /* 상태팀Seq */

    @NotNull
    private Long homeTeamSeq;           /* 홈팀Seq */
    @NotNull
    private Long awayteamSeq;           /* 어웨이팀Seq */
}
