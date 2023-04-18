package com.threeNerds.basketballDiary.http;

import com.threeNerds.basketballDiary.mvc.game.dto.getGameJoinPlayerRecordsByQuarter.request.GetGameJoinPlayerRecordsByQuarterRequest;

public class RequestJsonBody implements IRequestBody {

    public <T extends RequestJsonBody> T castSelfType( Object targetObj ) {
        return (T) targetObj;
    }
}
