package com.threeNerds.basketballDiary.mvc.game.controller.request;

import com.threeNerds.basketballDiary.mvc.game.mapper.dto.GameRecorderDTO;
import com.threeNerds.basketballDiary.mvc.game.service.dto.GameRecorderCommand;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
public class SaveGameRecordersRequest {

    @NotNull
    private List<GameRecorderDTO> gameRecorders;

    public GameRecorderCommand toCommand(Long gameSeq) {
        return GameRecorderCommand.builder()
                .gameSeq(       gameSeq )
                .gameRecorders( this.gameRecorders )
                .build();
    }

}
