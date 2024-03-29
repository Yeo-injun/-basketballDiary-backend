package com.threeNerds.basketballDiary.mvc.user.controller.response;

import lombok.Getter;

@Getter
public class CheckUserIdAvailableResponse {
    private final Boolean isAvailable;

    public CheckUserIdAvailableResponse( Boolean isAvailable ) {
        this.isAvailable = isAvailable;
    }
}
