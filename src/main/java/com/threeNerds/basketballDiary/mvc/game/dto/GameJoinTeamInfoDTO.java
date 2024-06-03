package com.threeNerds.basketballDiary.mvc.game.dto;

import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import lombok.Getter;

@Getter
public class GameJoinTeamInfoDTO {

    private Long gameSeq;
    private Long gameJoinTeamSeq;
    private String homeAwayCode;
    private String homeAwayCodeName;

    private String teamName;
    private String hometown;
    private String teamImagePath;
    private String leaderName;

    public GameJoinTeamInfoDTO setHomeAwayCode(String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        this.homeAwayCodeName = HomeAwayCode.nameOf(homeAwayCode);
        return this;
    }
}
