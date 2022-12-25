package com.threeNerds.basketballDiary.mvc.auth.dto;

import lombok.Getter;

@Getter
public class CheckDuplicateUserIdDTO {
    /** 아이디 **/
    private String userId;

    public CheckDuplicateUserIdDTO userId(String userId){
        this.userId=userId;
        return this;
    }

}
