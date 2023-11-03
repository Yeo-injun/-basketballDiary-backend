package com.threeNerds.basketballDiary.mvc.authUser.controller.response;

import lombok.Getter;

@Getter
public class CheckDuplicationUserIdResponse {
    private Boolean isDuplicated;

    public CheckDuplicationUserIdResponse( Boolean isDuplicated ) {
        this.isDuplicated = isDuplicated;
    }
}
