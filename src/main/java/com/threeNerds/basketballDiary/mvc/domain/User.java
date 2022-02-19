package com.threeNerds.basketballDiary.mvc.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import javax.swing.plaf.basic.BasicViewportUI;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class User {
    /* PK : id */
    private Long userSeq;
    /* 아이디 */
    private final String userId;
    /* 패스워드 */
    private final String password;
    /* 이름 */
    private final String userName;
    /* 포지션 코드 */
    private final String positionCode;
    /* 이메일 */
    private final String email;
    /* 성별 */
    private final String gender;
    /* 키 */
    private final double height;
    /* 몸무게 */
    private final double weight;
    /* 등록일자 */
    private final LocalDate regDate;
    /* 업데이트 일자 */
    private final LocalDate updateDate;
    /* 회원/비회원 */
    private final String userRegYn;
    /* 시도코드 */
    private final String sidoCode;
    /* 시군구코드 */
    private final String sigunguCode;

    public User(Builder builder) {
        this.userId = builder.userId;
        this.password = builder.password;
        this.userName = builder.userName;
        this.email=builder.email;
        this.userRegYn = builder.userRegYn;
        this.gender = builder.gender;
        this.height = builder.height;
        this.weight = builder.weight;
        this.positionCode = builder.positionCode;
        this.regDate = builder.regDate;
        this.updateDate = builder.updateDate;
        this.sidoCode = builder.sidoCode;
        this.sigunguCode=builder.sigunguCode;
    }

    public static class Builder{

        private final String userId;
        private final String password;
        private final String userName;
        private final String email;
        private final String userRegYn;
        private final String gender;
        private final Double height;
        private final Double weight;
        private String positionCode;
        private LocalDate regDate;
        private LocalDate updateDate;
        private String sidoCode;
        private String sigunguCode;

        public Builder(String userId, String password, String userName, String email, String userRegYn, String gender, Double height, Double weight) {
            if(userId==null || password==null | userName==null || email==null || userRegYn==null || gender==null || height==null || weight==null){
                throw new IllegalArgumentException("userId,password,userName,email,userRegYn,gender,height,weight are not null!! ");
            }
            this.userId = userId;
            this.password = password;
            this.userName = userName;
            this.email = email;
            this.userRegYn = userRegYn;
            this.gender = gender;
            this.height = height;
            this.weight = weight;
        }

        public Builder withPositionCode(String positionCode) {
            this.positionCode = positionCode;
            return this;
        }

        public Builder withRegDate(LocalDate regDate) {
            this.regDate = regDate;
            return this;
        }
        public Builder withUpdateDate(LocalDate updateDate){
            this.updateDate = updateDate;
            return this;
        }
        public Builder withSidoCode(String sidoCode){
            this.sidoCode=sidoCode;
            return this;
        }
        public Builder withSigunduCode(String sigunguCode){
            this.sigunguCode=sigunguCode;
            return this;
        }
        public User build(){
            return new User(this);
        }
    }
}
