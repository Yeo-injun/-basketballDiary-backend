package com.threeNerds.basketballDiary.mvc.dto.user.user;

import lombok.Getter;

@Getter
public class FindAllUserDTO {

    private String userName;
    private String email;

    public FindAllUserDTO userName(String userName){
        this.userName=userName;
        return this;
    }
    public FindAllUserDTO email(String email){
        this.email=email;
        return this;
    }
}
