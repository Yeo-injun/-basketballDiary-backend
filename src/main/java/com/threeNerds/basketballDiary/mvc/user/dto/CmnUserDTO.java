package com.threeNerds.basketballDiary.mvc.user.dto;

import lombok.Getter;

@Getter
public class CmnUserDTO {
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

    public CmnUserDTO userId(String userId){
        this.userId=userId;
        return this;
    }
    public CmnUserDTO password(String password){
        this.password = password;
        return this;
    }
    public CmnUserDTO userName(String userName){
        this.userName=userName;
        return this;
    }
    public CmnUserDTO name(String name){
        this.name=name;
        return this;
    }
    public CmnUserDTO email(String email){
        this.email=email;
        return this;
    }
    public CmnUserDTO gender(String gender){
        this.gender = gender;
        return this;
    }
    public CmnUserDTO birthYmd(String birthYmd){
        this.birthYmd = birthYmd;
        return this;
    }
    public CmnUserDTO height(Double height){
        this.height=height;
        return this;
    }
    public CmnUserDTO weight(Double weight){
        this.weight =weight;
        return this;
    }
    public CmnUserDTO sidoCode(String sidoCode){
        this.sidoCode = sidoCode;
        return this;
    }
    public CmnUserDTO sigunguCode(String sigunguCode){
        this.sigunguCode = sigunguCode;
        return this;
    }
    public CmnUserDTO positionCode(String positionCode){
        this.positionCode = positionCode;
        return this;
    }
    public CmnUserDTO roadAddress(String roadAddress){
        this.roadAddress = roadAddress;
        return this;
    }
}
