package com.threeNerds.basketballDiary.mvc.game.controller.response;

import com.threeNerds.basketballDiary.mvc.game.mapper.dto.GameRecorderDTO;
import com.threeNerds.basketballDiary.mvc.game.service.dto.GameRecorderQuery;
import lombok.Getter;

import java.util.List;

@Getter
public class GetGameRecordersResponse {

    private List<GameRecorderDTO> gameRecorders;

    public GetGameRecordersResponse( GameRecorderQuery.Result result ) {
        this.gameRecorders = result.getRecorders();
    }

}
