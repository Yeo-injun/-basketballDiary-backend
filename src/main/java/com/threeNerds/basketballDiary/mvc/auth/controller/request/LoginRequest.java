package com.threeNerds.basketballDiary.mvc.auth.controller.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class LoginRequest {

    @NotEmpty
    private String userId;
    @NotEmpty
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
