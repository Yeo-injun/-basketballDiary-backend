package com.threeNerds.basketballDiary.mvc.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class User {
    /**
     * PK : id
     */
    private Long userSeq;
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
     * 주소
     */
    private Address address;
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
    private double height;
    /**
     * 몸무게
     */
    private double weight;
    /**
     * 등록일자
     */
    private LocalDate regDate;
    /**
     * 업데이트 일자
     */
    private LocalDate updateDate;
    /**
     * 회원/비회원
     */
    private String userRegYn;
}
