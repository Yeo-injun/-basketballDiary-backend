package com.threeNerds.basketballDiary.mvc.game.controller.response;

import com.threeNerds.basketballDiary.mvc.game.mapper.dto.GameJoinTeamInfoDTO;
import com.threeNerds.basketballDiary.mvc.game.service.dto.GameJoinTeamQuery;
import lombok.Getter;

@Getter
public class GetGameJoinTeamsResponse {

    private final GameJoinTeamInfoDTO homeTeamInfo;
    private final GameJoinTeamInfoDTO awayTeamInfo;

    public GetGameJoinTeamsResponse( GameJoinTeamQuery.Result result ) {
        this.homeTeamInfo = result.getHomeTeamInfo();
        this.awayTeamInfo = result.getAwayTeamInfo();
    }
}
