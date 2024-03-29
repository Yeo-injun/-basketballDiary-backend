package com.threeNerds.basketballDiary.mvc.user.controller.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class CreateMembershipRequest {
    /** 아이디 **/
    @NotEmpty(message = "사용자ID는 필수 입력값입니다.")
    private String userId;
    /** 패스워드 **/
    @NotEmpty(message = "비밀번호는 필수 입력값입니다")
    private String password;
    /** 이름 **/
    @NotEmpty(message = "이름은 필수 입력값입니다")
    private String name;
    /** 이메일 **/
    @Email(message = "이메일 형식이 맞지 않습니다.")
    private String email;
    /** 성별 **/
    @NotEmpty(message = "성별은 필수 입력값입니다.")
    private String gender;
    /** 생년월일 **/
    private String birthYmd;
    /** 키 **/
    @NotNull(message = "키는 필수 입력값입니다.")
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


}
