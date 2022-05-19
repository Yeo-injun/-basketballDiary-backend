package com.threeNerds.basketballDiary.mvc.dto.loginUser;

import lombok.Getter;

@Getter
public class PasswordDTO {

    private Long userSeq;
    private String prevPassword;
    private String curPassword;

    public PasswordDTO userSeq(Long userSeq){
        this.userSeq=userSeq;
        return this;
    }

    public PasswordDTO prevPassword(String prevPassword){
        this.prevPassword = prevPassword;
        return this;
    }

    public PasswordDTO curPassword(String curPassword){
        this.curPassword = curPassword;
        return this;
    }
}
