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


    public GameJoinTeamInfoDTO gameSeq(Long gameSeq) {
        this.gameSeq = gameSeq;
        return this;
    }

    public GameJoinTeamInfoDTO gameJoinTeamSeq(Long gameJoinTeamSeq) {
        this.gameJoinTeamSeq = gameJoinTeamSeq;
        return this;
    }

    public GameJoinTeamInfoDTO setHomeAwayCode(String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        this.homeAwayCodeName = HomeAwayCode.nameOf(homeAwayCode);
        return this;
    }

    public GameJoinTeamInfoDTO leaderName(String leaderName) {
        this.leaderName = leaderName;
        return this;
    }

    public GameJoinTeamInfoDTO teamName(String teamName) {
        this.teamName = teamName;
        return this;
    }

    public GameJoinTeamInfoDTO hometown(String hometown) {
        this.hometown = hometown;
        return this;
    }


}
