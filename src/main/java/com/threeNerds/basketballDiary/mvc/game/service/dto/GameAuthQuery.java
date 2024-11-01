package com.threeNerds.basketballDiary.mvc.game.service.dto;

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
    Map< String, String > authGames;

    @Getter
    public class Result {
        private final Long userSeq;
        private final Map< String, String > authGames;

        Result( Long userSeq, Map< String, String > authGames ) {
            this.userSeq    = userSeq;
            this.authGames  = authGames;
        }
    }

    public GameAuthQuery.Result buildResult( Map< String, String > authGames ) {
        return new GameAuthQuery.Result( this.userSeq, authGames );
    }
}
