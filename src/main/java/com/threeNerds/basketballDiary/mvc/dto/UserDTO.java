package com.threeNerds.basketballDiary.mvc.dto;

import com.threeNerds.basketballDiary.mvc.domain.Address;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
     * 주소
     */
    private String city;
    private Long sidoCod;
    private Long sggCod;
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
}
