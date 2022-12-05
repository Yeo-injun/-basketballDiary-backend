package com.threeNerds.basketballDiary.mvc.game.dto;

import lombok.Getter;

@Getter
public class PlayerInfoDTO {

    private Long gameSeq;
    private Long teamSeq;
    private Long userSeq;

    private String homeAwayCode;

    private String userName;
    private String email;
    private String positionCode;
    private String backNumber;

}
