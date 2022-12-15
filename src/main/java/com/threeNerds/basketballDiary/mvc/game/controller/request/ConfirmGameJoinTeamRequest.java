package com.threeNerds.basketballDiary.mvc.game.controller.request;

import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinTeamCreationDTO;
import lombok.Getter;

@Getter
public class ConfirmGameJoinTeamRequest {

    private Long gameSeq;               /* 게임Seq */
    private String gameTypeCode;        /* 게임유형코드 */

    private Long gameJoinTeamSeq;       /* 게임참가팀Seq */
    private Long opponentTeamSeq;       /* 상태팀Seq */

    private Long homeTeamSeq;           /* 홈팀Seq */
    private Long awayteamSeq;           /* 어웨이팀Seq */
}
