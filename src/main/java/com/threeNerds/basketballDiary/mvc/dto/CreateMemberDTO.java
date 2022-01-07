package com.threeNerds.basketballDiary.mvc.dto;

import com.threeNerds.basketballDiary.mvc.domain.Address;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateMemberDTO {
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
