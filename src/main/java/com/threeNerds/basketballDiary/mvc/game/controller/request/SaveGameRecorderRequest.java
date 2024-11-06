package com.threeNerds.basketballDiary.mvc.game.controller.request;

import com.threeNerds.basketballDiary.mvc.game.mapper.dto.GameRecorderDTO;
import com.threeNerds.basketballDiary.mvc.game.service.dto.GameRecorderCommand;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
public class SaveGameRecorderRequest {

    @NotNull
    private GameRecorderDTO gameRecorder;

    public GameRecorderCommand toCommand( Long gameSeq ) {
        return GameRecorderCommand.builder()
                .gameSeq(       gameSeq )
                .gameRecorder(  this.gameRecorder )
                .build();
    }

}
