package com.threeNerds.basketballDiary.mvc.authUser.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class UpdateUserDTO {

    @NotNull
    private Long userSeq;
    @NotEmpty
    private String userName;
    private String gender;
    @Email
    private String email;
    private Double height;
    private Double weight;
    @NotEmpty
    private String sidoCode;
    @NotEmpty
    private String sigunguCode;
    private String positionCode;
    private String roadAddress;

    public UpdateUserDTO userSeq(Long userSeq){
        this.userSeq = userSeq;
        return this;
    }
    public UpdateUserDTO userName(String userName){
        this.userName = userName;
        return this;
    }
    public UpdateUserDTO gender(String gender){
        this.gender = gender;
        return this;
    }
    public UpdateUserDTO email(String email){
        this.email = email;
        return this;
    }
    public UpdateUserDTO height(Double height){
        this.height = height;
        return this;
    }
    public UpdateUserDTO weight(Double weight){
        this.weight = weight;
        return this;
    }
    public UpdateUserDTO sidoCode(String sidoCode){
        this.sidoCode = sidoCode;
        return this;
    }
    public UpdateUserDTO sigunguCode(String sigunguCode){
        this.sigunguCode = sigunguCode;
        return this;
    }
    public UpdateUserDTO positionCode(String positionCode){
        this.positionCode = positionCode;
        return this;
    }
    public UpdateUserDTO roadAddress(String roadAddress){
        this.roadAddress = roadAddress;
        return this;
    }
}
