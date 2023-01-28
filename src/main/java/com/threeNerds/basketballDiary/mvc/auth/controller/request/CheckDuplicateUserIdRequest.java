package com.threeNerds.basketballDiary.mvc.auth.controller.request;

import lombok.Getter;

@Getter
public class CheckDuplicateUserIdRequest {
    /** 아이디 **/
    private String userId;

    public CheckDuplicateUserIdRequest userId(String userId){
        this.userId=userId;
        return this;
    }

}
