package com.threeNerds.basketballDiary.mvc.game.controller.response;

import com.threeNerds.basketballDiary.mvc.game.dto.QuarterTeamEntryDTO;
import com.threeNerds.basketballDiary.mvc.game.service.dto.GameEntryQuery;
import lombok.Getter;

@Getter
public class GetGameEntryResponse {

    private final QuarterTeamEntryDTO homeTeamEntry;
    private final QuarterTeamEntryDTO awayTeamEntry;

    public GetGameEntryResponse ( GameEntryQuery.Result result ) {
        this.homeTeamEntry = result.getHomeTeamEntry();
        this.awayTeamEntry = result.getAwayTeamEntry();
    }
}
