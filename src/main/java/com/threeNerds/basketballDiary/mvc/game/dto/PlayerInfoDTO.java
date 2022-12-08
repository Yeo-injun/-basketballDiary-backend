package com.threeNerds.basketballDiary.mvc.game.dto;

import lombok.Getter;

@Getter
public class PlayerInfoDTO {

    private Long gameSeq;   // TODO 삭제검토
    private Long teamSeq;   // TODO 삭제검토

    private Long gameJoinPlayerSeq;
    private Long userSeq;

    private String playerTypeCode;
    private String playerTypeCodeName;
    private String homeAwayCode;   // TODO 삭제검토

    private String teamName;
    private String playerName;

    private String backNumber;
    private String positionCode;
    private String positionCodeName;

    private String email;

}
