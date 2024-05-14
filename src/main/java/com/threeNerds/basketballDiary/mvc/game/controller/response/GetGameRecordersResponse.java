package com.threeNerds.basketballDiary.mvc.game.controller.response;

import com.threeNerds.basketballDiary.http.ResponseJsonBody;
import com.threeNerds.basketballDiary.mvc.game.dto.GameRecorderDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class GetGameRecordersResponse extends ResponseJsonBody {

    private List<GameRecorderDTO> gameRecorders;

    public GetGameRecordersResponse(List<GameRecorderDTO> gameRecorders) {
        this.gameRecorders = gameRecorders;
    }

}
