package com.threeNerds.basketballDiary.mvc.game.dto;

import com.threeNerds.basketballDiary.constant.code.HomeAwayCode;
import com.threeNerds.basketballDiary.constant.code.PlayerTypeCode;
import com.threeNerds.basketballDiary.constant.code.PositionCode;
import lombok.Getter;

@Getter
public class GameJoinTeamMemberDTO {

    private Long gameJoinPlayerSeq;
    private Long gameSeq;
    private String homeAwayCode;
    private String homeAwayCodeName;
    private Long teamSeq;
    private Long userSeq;

    private String playerTypeCode;
    private String playerTypeCodeName;
    private String positionCode;
    private String positionCodeName;

    private String teamName;
    private String userName;
    private String backNumber;
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
