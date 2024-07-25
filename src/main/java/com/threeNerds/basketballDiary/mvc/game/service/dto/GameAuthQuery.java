package com.threeNerds.basketballDiary.mvc.game.service.dto;

import com.threeNerds.basketballDiary.auth.constant.AuthLevel;
import com.threeNerds.basketballDiary.mvc.game.dto.QuarterTeamEntryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameAuthQuery {
    Long userSeq;
    Map< Long, AuthLevel > authGames;

    @Getter
    public class Result {
        private final Long userSeq;
        private final Map< Long, AuthLevel > authGames;

        Result( Long userSeq, Map< Long, AuthLevel > authGames ) {
            this.userSeq    = userSeq;
            this.authGames  = authGames;
        }
    }

    public GameAuthQuery.Result buildResult( Map< Long, AuthLevel > authGames ) {
        return new GameAuthQuery.Result( this.userSeq, authGames );
    }
}
