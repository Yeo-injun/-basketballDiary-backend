package com.threeNerds.basketballDiary.mvc.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Member {
    /**
     * PK : id
     */
    private Long id;
    /**
     * 아이디
     */
    private String memberId;
    /**
     * 패스워드
     */
    private String password;
    /**
     * 주소
     */
    private Address address;
    /**
     * 핸드폰 번호
     */
    private String phoneNumber;
}
