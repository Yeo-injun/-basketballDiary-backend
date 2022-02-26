package com.threeNerds.basketballDiary.mvc.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class UserDTO {
    /**
     * 아이디
     */
    private String userId;
    /**
     * 패스워드
     */
    private String password;
    /**
     * 이름
     */
    private String userName;
    /**
     * 이메일
     */
    private String email;
    /**
     * 성별
     */
    private String gender;
    /**
     * 키
     */
    private Double height;
    /**
     * 몸무게
     */
    private Double weight;
    /**
     * 시도코드
     */
    private String sidoCode;
    /**
     * 시군구코드
     */
    private String sigunguCode;
    /**
     * 포지션 코드
     */
    private String positionCode;
}
