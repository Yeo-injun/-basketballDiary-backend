package com.threeNerds.basketballDiary.mvc.game.mapper.dto;

import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import com.threeNerds.basketballDiary.constant.code.type.PlayerTypeCode;
import com.threeNerds.basketballDiary.constant.code.type.PositionCode;
import lombok.Getter;

@Getter
public class PlayerInfoDTO {

    private Long gameSeq;
    private Long gameJoinTeamSeq;
    private Long teamSeq;

    private Long gameJoinPlayerSeq;
    private Long userSeq;

    private String playerTypeCode;
    private String playerTypeCodeName;
    private String homeAwayCode;
    private String homeAwayCodeName;

    private String teamName;
    private String userName;

    private String backNumber;
    private String positionCode;
    private String positionCodeName;

    private String email;


    private void setPlayerTypeCode(String playerTypeCode) {
        this.playerTypeCode = playerTypeCode;
        this.playerTypeCodeName = PlayerTypeCode.nameOf(playerTypeCode);
    }

    private void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
        this.positionCodeName = PositionCode.nameOf(positionCode);
    }

    private void setHomeAwayCode(String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        this.homeAwayCodeName = HomeAwayCode.nameOf(homeAwayCode);
    }
}
