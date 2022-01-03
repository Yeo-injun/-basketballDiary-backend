package com.threeNerds.basketballDiary.mvc.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Address {
    /**
     * 도시
     */
    private String city;
    /**
     * 거리
     */
    private String street;
    /**
     * 우편번호
     */
    private String zipCode;
}
