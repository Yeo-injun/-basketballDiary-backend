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
     * 시도코드
     */
    private Long sidoCod;
    /**
     * 시군구코드
     */
    private Long sggCod;

    public Address(String city, Long sidoCod, Long sggCod) {
        this.city = city;
        this.sidoCod = sidoCod;
        this.sggCod = sggCod;
    }
}
