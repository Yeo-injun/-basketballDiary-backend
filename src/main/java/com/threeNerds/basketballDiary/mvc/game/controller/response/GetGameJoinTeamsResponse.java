package com.threeNerds.basketballDiary.mvc.game.controller.response;

import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinTeamInfoDTO;
import lombok.Getter;

@Getter
public class GetGameJoinTeamsResponse {

    private GameJoinTeamInfoDTO homeTeamInfo;
    private GameJoinTeamInfoDTO awayTeamInfo;

    public GetGameJoinTeamsResponse homeTeamInfo(GameJoinTeamInfoDTO homeTeamInfo) {
        this.homeTeamInfo = homeTeamInfo;
        return this;
    }

    public GetGameJoinTeamsResponse awayTeamInfo(GameJoinTeamInfoDTO awayTeamInfo) {
        this.awayTeamInfo = awayTeamInfo;
        return this;
    }
}
