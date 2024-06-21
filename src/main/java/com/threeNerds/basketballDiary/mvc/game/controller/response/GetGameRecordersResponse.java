package com.threeNerds.basketballDiary.mvc.game.controller.response;

import com.threeNerds.basketballDiary.http.ResponseJsonBody;
import com.threeNerds.basketballDiary.mvc.game.dto.GameRecorderDTO;
import com.threeNerds.basketballDiary.mvc.game.service.dto.GameRecorderQuery;
import lombok.Getter;

import java.util.List;

@Getter
public class GetGameRecordersResponse extends ResponseJsonBody {

    private List<GameRecorderDTO> gameRecorders;

    public GetGameRecordersResponse( GameRecorderQuery.Result result ) {
        this.gameRecorders = result.getRecorders();
    }

}
