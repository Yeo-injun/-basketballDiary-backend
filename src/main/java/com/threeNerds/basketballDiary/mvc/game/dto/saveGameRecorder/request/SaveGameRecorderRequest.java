package com.threeNerds.basketballDiary.mvc.game.dto.saveGameRecorder.request;

import lombok.Getter;

import java.util.Map;


@Getter
public class SaveGameRecorderRequest {

    private Long gameSeq;
    private Long userSeq;
    private Map<Long, Long> userTeamAuth;

    public SaveGameRecorderRequest gameSeq(Long gameSeq ) {
        this.gameSeq = gameSeq;
        return this;
    }

    public SaveGameRecorderRequest userSeq( Long userSeq ) {
        this.userSeq = userSeq;
        return this;
    }

    public SaveGameRecorderRequest userTeamAuth( Map<Long, Long> userTeamAuth ) {
        this.userTeamAuth = userTeamAuth;
        return this;
    }

}
