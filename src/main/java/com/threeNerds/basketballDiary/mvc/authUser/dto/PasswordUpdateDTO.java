package com.threeNerds.basketballDiary.mvc.authUser.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class PasswordUpdateDTO {

    @NotNull
    private Long userSeq;
    @NotEmpty
    private String prevPassword;
    @NotEmpty
    private String newPassword;

    public PasswordUpdateDTO userSeq(Long userSeq){
        this.userSeq=userSeq;
        return this;
    }

    public PasswordUpdateDTO prevPassword(String prevPassword){
        this.prevPassword = prevPassword;
        return this;
    }

    public PasswordUpdateDTO newPassword(String newPassword){
        this.newPassword = newPassword;
        return this;
    }
}
