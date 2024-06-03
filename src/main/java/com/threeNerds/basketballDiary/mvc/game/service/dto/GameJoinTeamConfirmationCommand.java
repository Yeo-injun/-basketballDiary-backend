package com.threeNerds.basketballDiary.mvc.game.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameJoinTeamConfirmationCommand {
    private Long gameSeq;               /* 경기Seq */
    private String gameTypeCode;        /* 경기유형코드 */
    private Long opponentTeamSeq;       /* 상대팀Seq - 경기유형코드가 02(교류전)인 경우에만 존재 */
}