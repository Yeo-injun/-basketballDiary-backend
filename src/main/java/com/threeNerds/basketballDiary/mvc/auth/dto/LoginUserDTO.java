package com.threeNerds.basketballDiary.mvc.auth.dto;

import lombok.Getter;

@Getter
public class LoginUserDTO {

    private String userId;
    private String password;

    public LoginUserDTO userId(String userId) {
        this.userId = userId;
        return this;
    }
    public LoginUserDTO password(String password) {
        this.password = password;
        return this;
    }
}