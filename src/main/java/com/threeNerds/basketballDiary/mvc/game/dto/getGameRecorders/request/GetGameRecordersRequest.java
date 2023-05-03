package com.threeNerds.basketballDiary.mvc.game.dto.getGameRecorders.request;

import com.threeNerds.basketballDiary.http.RequestJsonBody;
import lombok.Getter;

@Getter
public class GetGameRecordersRequest extends RequestJsonBody {

    public GetGameRecordersRequest( Long gameSeq ) {
        this.gameSeq = gameSeq;
    }

    private Long gameSeq;


}
