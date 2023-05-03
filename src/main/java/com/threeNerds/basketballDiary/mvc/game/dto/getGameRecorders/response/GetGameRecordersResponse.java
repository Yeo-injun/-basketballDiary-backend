package com.threeNerds.basketballDiary.mvc.game.dto.getGameRecorders.response;

import com.threeNerds.basketballDiary.http.ResponseJsonBody;
import lombok.Getter;

import java.util.List;

@Getter
public class GetGameRecordersResponse extends ResponseJsonBody {

    private List<GameRecorderDTO> gameRecorders;

    public GetGameRecordersResponse( List<GameRecorderDTO> gameRecorders ) {
        this.gameRecorders = gameRecorders;
    }

}
