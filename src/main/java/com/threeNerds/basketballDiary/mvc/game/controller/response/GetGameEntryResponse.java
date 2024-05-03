package com.threeNerds.basketballDiary.mvc.game.controller.response;

import com.threeNerds.basketballDiary.mvc.game.dto.QuarterTeamEntryDTO;
import lombok.Getter;

@Getter
public class GetGameEntryResponse {

    private final QuarterTeamEntryDTO homeTeamEntry;
    private final QuarterTeamEntryDTO awayTeamEntry;

    public GetGameEntryResponse ( QuarterTeamEntryDTO homeTeamEntry, QuarterTeamEntryDTO awayTeamEntry ) {
        this.homeTeamEntry = homeTeamEntry;
        this.awayTeamEntry = awayTeamEntry;
    }
}
