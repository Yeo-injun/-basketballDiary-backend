package com.threeNerds.basketballDiary.mvc.game.dto.saveGameRecorder.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;


@Getter
public class SaveGameRecorderRequest {

    @NotEmpty
    private Long gameSeq;
    @NotEmpty
    private Long userSeq;
    @NotNull
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
