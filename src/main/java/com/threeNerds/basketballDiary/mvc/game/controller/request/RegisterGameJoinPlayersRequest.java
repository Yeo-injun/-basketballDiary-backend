package com.threeNerds.basketballDiary.mvc.game.controller.request;

import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinPlayerDTO;
import com.threeNerds.basketballDiary.mvc.game.service.dto.GameJoinPlayerCommand;
import com.threeNerds.basketballDiary.mvc.game.service.dto.GameJoinTeamConfirmationCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class RegisterGameJoinPlayersRequest {

    @NotNull
    private List<GameJoinPlayerDTO> gameJoinPlayers;

    public GameJoinPlayerCommand toCommand( Long gameSeq, String homeAwayCode ) {
        return GameJoinPlayerCommand.builder()
                .gameSeq(           gameSeq )
                .homeAwayCode(      homeAwayCode )
                .gameJoinPlayers(   this.getGameJoinPlayers() )
                .build();
    }

    @Getter
    public static class ConfirmGameJoinTeamRequest {

        private Long gameSeq;               /* 게임Seq */
        @NotEmpty
        private String gameTypeCode;        /* 게임유형코드 */
        private Long opponentTeamSeq;       /* 상태팀Seq */

        public GameJoinTeamConfirmationCommand toCommand(Long gameSeq ) {
            return GameJoinTeamConfirmationCommand.builder()
                    .gameSeq(           gameSeq )
                    .gameTypeCode(      this.gameTypeCode )
                    .opponentTeamSeq(   this.opponentTeamSeq )
                    .build();
        }

    }
}
