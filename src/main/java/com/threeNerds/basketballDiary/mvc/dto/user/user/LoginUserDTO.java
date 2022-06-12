package com.threeNerds.basketballDiary.mvc.dto.user.user;

import com.threeNerds.basketballDiary.mvc.domain.User;
import lombok.Getter;

@Getter
public class LoginUserDTO {

    private String userId;
    private String password;

    public LoginUserDTO userId(String userId){
        this.userId = userId;
        return this;
    }
    public LoginUserDTO password(String password){
        this.password = password;
        return this;
    }

    static public LoginUserDTO createLoginUserDTO(User user){
        return new LoginUserDTO()
                .userId(user.getUserId())
                .password(user.getPassword());
    }
}
