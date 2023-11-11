package com.threeNerds.basketballDiary.mvc.auth.controller.request;

import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class CreateUserRequest {
    /** 아이디 **/
    @NotEmpty(message = "userId 는 필수입니다.")
    private String userId;
    /** 패스워드 **/
    @NotEmpty(message = "password 는 필수입니다")
    private String password;
    /** 이름 **/
    @NotEmpty(message = "name 은 필수입니다")
    private String name;
    /** 이메일 **/
    @Email(message = "email 은 필수입니다.")
    private String email;
    /** 성별 **/
    @NotEmpty(message = "gender 는 필수입니다")
    private String gender;
    /** 생년월일 **/
    private String birthYmd;
    /** 키 **/
    @NotNull(message = "height 는 필수입니다.")
    private Double height;
    /** 몸무게 **/
    private Double weight;
    /** 시도코드 **/
    private String sidoCode;
    /** 시군구코드 **/
    private String sigunguCode;
    /** 포지션 코드 **/
    private String positionCode;

    private String roadAddress;

    public CreateUserRequest userId(String userId){
        this.userId=userId;
        return this;
    }
    public CreateUserRequest password(String password){
        this.password = password;
        return this;
    }
    public CreateUserRequest name(String name){
        this.name=name;
        return this;
    }
    public CreateUserRequest email(String email){
        this.email=email;
        return this;
    }
    public CreateUserRequest gender(String gender){
        this.gender = gender;
        return this;
    }
    public CreateUserRequest birthYmd(String birthYmd){
        this.birthYmd = birthYmd;
        return this;
    }
    public CreateUserRequest height(Double height){
        this.height=height;
        return this;
    }
    public CreateUserRequest weight(Double weight){
        this.weight =weight;
        return this;
    }
    public CreateUserRequest sidoCode(String sidoCode){
        this.sidoCode = sidoCode;
        return this;
    }
    public CreateUserRequest sigunguCode(String sigunguCode){
        this.sigunguCode = sigunguCode;
        return this;
    }
    public CreateUserRequest positionCode(String positionCode){
        this.positionCode = positionCode;
        return this;
    }
    public CreateUserRequest roadAddress(String roadAddress){
        this.roadAddress = roadAddress;
        return this;
    }
}
