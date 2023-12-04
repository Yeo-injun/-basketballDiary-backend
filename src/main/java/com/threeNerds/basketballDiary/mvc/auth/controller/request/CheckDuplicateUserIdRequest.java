package com.threeNerds.basketballDiary.mvc.auth.controller.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class CheckDuplicateUserIdRequest {
    /** 아이디 **/
    @NotEmpty(message = "userId 값은 필수입니다.")
    private String userId;

    public CheckDuplicateUserIdRequest userId(String userId){
        this.userId=userId;
        return this;
    }

}
