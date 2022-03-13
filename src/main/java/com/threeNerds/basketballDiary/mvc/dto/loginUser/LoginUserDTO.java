package com.threeNerds.basketballDiary.mvc.dto.loginUser;

import lombok.Getter;

@Getter
public class LoginUserDTO {
    /** 회원아이디 **/
    private String userId;
    /** 비밀번호 **/
    private String password;

    public LoginUserDTO userId(String userId){
        this.userId = userId;
        return this;
    }
    public LoginUserDTO password(String password){
        this.password=password;
        return this;
    }
}
