package com.threeNerds.basketballDiary.mvc.game.dto.saveGameRecorder.request;

import com.threeNerds.basketballDiary.mvc.game.dto.getGameRecorders.response.GameRecorderDTO;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;


@Getter
public class SaveGameRecordersRequest {

    @NotNull
    private Long gameSeq;
    @NotNull
    private List<GameRecorderDTO> gameRecorders;

    public SaveGameRecordersRequest gameSeq(Long gameSeq ) {
        this.gameSeq = gameSeq;
        return this;
    }


}
