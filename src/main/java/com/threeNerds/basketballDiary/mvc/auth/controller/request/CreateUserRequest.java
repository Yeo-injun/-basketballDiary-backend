package com.threeNerds.basketballDiary.mvc.auth.controller.request;

import lombok.Getter;

@Getter
public class CreateUserRequest {
    /** 아이디 **/
    private String userId;
    /** 패스워드 **/
    private String password;
    /** 이름 **/
    private String userName;    // TODO userName 필드명 name으로 바꾸기...! (프론트에서도 name으로 넘겨주기)
    private String name;
    /** 이메일 **/
    private String email;
    /** 성별 **/
    private String gender;
    /** 생년월일 **/
    private String birthYmd;
    /** 키 **/
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
    public CreateUserRequest userName(String userName){
        this.userName=userName;
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
