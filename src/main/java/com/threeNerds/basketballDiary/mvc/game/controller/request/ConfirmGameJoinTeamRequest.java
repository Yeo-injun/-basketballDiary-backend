package com.threeNerds.basketballDiary.mvc.game.controller.request;

import com.threeNerds.basketballDiary.mvc.game.service.dto.GameJoinTeamConfirmationCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class ConfirmGameJoinTeamRequest {

    private Long gameSeq;               /* 게임Seq */
    @NotEmpty
    private String gameTypeCode;        /* 게임유형코드 */
    private Long opponentTeamSeq;       /* 상태팀Seq */

    public GameJoinTeamConfirmationCommand toCommand( Long gameSeq ) {
        return GameJoinTeamConfirmationCommand.builder()
                .gameSeq(           gameSeq )
                .gameTypeCode(      this.gameTypeCode )
                .opponentTeamSeq(   this.opponentTeamSeq )
                .build();
    }
}
