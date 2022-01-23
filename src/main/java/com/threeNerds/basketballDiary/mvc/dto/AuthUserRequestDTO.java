package com.threeNerds.basketballDiary.mvc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthUserRequestDTO {
    private String teamSeq;
    private String teamAuthCode;
}
