package com.threeNerds.basketballDiary.mvc.game.service.dto;

import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinTeamInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameJoinTeamQuery {

    private Long gameSeq;
    private String homeAwayCode;

    @Getter
    public class Result {
        private GameJoinTeamInfoDTO homeTeamInfo;
        private GameJoinTeamInfoDTO awayTeamInfo;

        Result( GameJoinTeamInfoDTO homeTeamInfo, GameJoinTeamInfoDTO awayTeamInfo ) {
            this.homeTeamInfo = homeTeamInfo;
            this.awayTeamInfo = awayTeamInfo;
        }
    }

    public Result buildResult( GameJoinTeamInfoDTO homeTeamInfo, GameJoinTeamInfoDTO awayTeamInfo ) {
        return new Result( homeTeamInfo, awayTeamInfo );
    }
}
