package com.threeNerds.basketballDiary.mvc.auth.controller.request;

import lombok.Getter;

@Getter
public class LoginRequest {

    private String userId;
    private String password;

    public LoginRequest userId(String userId) {
        this.userId = userId;
        return this;
    }
    public LoginRequest password(String password) {
        this.password = password;
        return this;
    }
}
