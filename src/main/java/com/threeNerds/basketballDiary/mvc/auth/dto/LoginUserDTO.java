package com.threeNerds.basketballDiary.mvc.auth.dto;

import lombok.Getter;

@Getter
public class LoginUserDTO {

    private String userId;
    private String password;

    private Long userSeq;
    public LoginUserDTO userId(String userId) {
        this.userId = userId;
        return this;
    }
    public LoginUserDTO password(String password) {
        this.password = password;
        return this;
    }

    public static LoginUserDTO ofSuccess( String userId, Long userSeq ) {
        LoginUserDTO loginUser = new LoginUserDTO();
        loginUser.userId     = userId;
        loginUser.userSeq    = userSeq;
        return loginUser;
    }
}
