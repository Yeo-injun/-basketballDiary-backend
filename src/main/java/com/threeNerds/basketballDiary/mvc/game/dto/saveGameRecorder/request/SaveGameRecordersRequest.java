package com.threeNerds.basketballDiary.mvc.game.dto.saveGameRecorder.request;

import com.threeNerds.basketballDiary.mvc.game.dto.getGameRecorders.response.GameRecorderDTO;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
public class SaveGameRecordersRequest {

    @NotNull
    private List<GameRecorderDTO> gameRecorders;

}
