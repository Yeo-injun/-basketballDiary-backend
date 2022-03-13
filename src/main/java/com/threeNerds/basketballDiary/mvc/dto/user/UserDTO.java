package com.threeNerds.basketballDiary.mvc.dto.user;

import lombok.Getter;

@Getter
public class UserDTO {
    /** 아이디 **/
    private String userId;
    /** 패스워드 **/
    private String password;
    /** 이름 **/
    private String userName;
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

    public UserDTO userId(String userId){
        this.userId=userId;
        return this;
    }
    public UserDTO password(String password){
        this.password = password;
        return this;
    }
    public UserDTO userName(String userName){
        this.userName=userName;
        return this;
    }
    public UserDTO email(String email){
        this.email=email;
        return this;
    }
    public UserDTO gender(String gender){
        this.gender = gender;
        return this;
    }
    public UserDTO birthYmd(String birthYmd){
        this.birthYmd = birthYmd;
        return this;
    }
    public UserDTO height(Double height){
        this.height=height;
        return this;
    }
    public UserDTO weight(Double weight){
        this.weight =weight;
        return this;
    }
    public UserDTO sidoCode(String sidoCode){
        this.sidoCode = sidoCode;
        return this;
    }
    public UserDTO sigunguCode(String sigunguCode){
        this.sigunguCode = sigunguCode;
        return this;
    }
    public UserDTO positionCode(String positionCode){
        this.positionCode = positionCode;
        return this;
    }

}
