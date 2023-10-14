package com.threeNerds.basketballDiary.mvc.authUser.controller.response;

import com.threeNerds.basketballDiary.http.response.BooleanResponse;
import lombok.Getter;

@Getter
public class CheckDuplicationUserIdResponse implements BooleanResponse {
    private Boolean isDuplicated;

    public CheckDuplicationUserIdResponse(Boolean isDuplicated ) {
        this.isDuplicated = isDuplicated;
    }
}
