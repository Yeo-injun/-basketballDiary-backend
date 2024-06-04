package com.threeNerds.basketballDiary.mvc.game.service.dto;

import com.threeNerds.basketballDiary.mvc.game.dto.GameDetailDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinTeamInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameQuery {

    private Long gameSeq;
    @Getter
    public class Result {

        private final GameDetailDTO gameInfo;

        Result( GameDetailDTO gameInfo ) {
            this.gameInfo           = gameInfo;
        }
    }

    public Result buildResult( GameDetailDTO gameInfo ) {
        return new Result( gameInfo );
    }
}
