package com.threeNerds.basketballDiary.mvc.game.dto.getGameJoinPlayers.request;

import lombok.Getter;

@Getter
public class GetGameJoinPlayersRequest {

    private Long gameSeq;
    private String homeAwayCode;

    public GetGameJoinPlayersRequest gameSeq(Long gameSeq) {
        this.gameSeq = gameSeq;
        return this;
    }

    public GetGameJoinPlayersRequest homeAwayCode(String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        return this;
    }

}
