package com.threeNerds.basketballDiary.mvc.auth.dto;

import lombok.Getter;

@Getter
public class CreateUserDTO {
    /** 아이디 **/
    private String userId;
    /** 패스워드 **/
    private String password;
    /** 이름 **/
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
    /** 도로명주소 **/
    private String roadAddress;

    public CreateUserDTO userId(String userId){
        this.userId=userId;
        return this;
    }
    public CreateUserDTO password(String password){
        this.password = password;
        return this;
    }
    public CreateUserDTO name(String name){
        this.name=name;
        return this;
    }
    public CreateUserDTO email(String email){
        this.email=email;
        return this;
    }
    public CreateUserDTO gender(String gender){
        this.gender = gender;
        return this;
    }
    public CreateUserDTO birthYmd(String birthYmd){
        this.birthYmd = birthYmd;
        return this;
    }
    public CreateUserDTO height(Double height){
        this.height=height;
        return this;
    }
    public CreateUserDTO weight(Double weight){
        this.weight =weight;
        return this;
    }
    public CreateUserDTO sidoCode(String sidoCode){
        this.sidoCode = sidoCode;
        return this;
    }
    public CreateUserDTO sigunguCode(String sigunguCode){
        this.sigunguCode = sigunguCode;
        return this;
    }
    public CreateUserDTO positionCode(String positionCode){
        this.positionCode = positionCode;
        return this;
    }
    public CreateUserDTO roadAddress(String roadAddress){
        this.roadAddress = roadAddress;
        return this;
    }
}
