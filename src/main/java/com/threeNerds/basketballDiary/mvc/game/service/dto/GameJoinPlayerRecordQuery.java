package com.threeNerds.basketballDiary.mvc.game.service.dto;

import com.threeNerds.basketballDiary.mvc.game.dto.PlayerQuarterRecordDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameJoinPlayerRecordQuery {

    private Long gameSeq;
    private String quarterCode;
    private String homeAwayCode;

    @Getter
    public class Result {
        private final Long gameSeq;
        private final String quarterCode;
        private final List<PlayerQuarterRecordDTO> homeTeamPlayers;
        private final List<PlayerQuarterRecordDTO> awayTeamPlayers;

        Result(
            GameJoinPlayerRecordQuery query,
            List<PlayerQuarterRecordDTO> homeTeamPlayers,
            List<PlayerQuarterRecordDTO> awayTeamPlayers
        ) {
            this.gameSeq            = query.getGameSeq();
            this.quarterCode        = query.getQuarterCode();
            this.homeTeamPlayers    = homeTeamPlayers;
            this.awayTeamPlayers    = awayTeamPlayers;
        }
    }

    public Result buildResult(
        List<PlayerQuarterRecordDTO> homeTeamPlayers,
        List<PlayerQuarterRecordDTO> awayTeamPlayers
    ) {
        return new Result( this, homeTeamPlayers, awayTeamPlayers );
    }
}
