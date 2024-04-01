package com.threeNerds.basketballDiary.mvc.user.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MembershipCommand {
    private String userId;
    private String plainPassword;
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
    private String roadAddress;
}
