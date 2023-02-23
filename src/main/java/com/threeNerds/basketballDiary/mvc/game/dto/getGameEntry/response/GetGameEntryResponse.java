package com.threeNerds.basketballDiary.mvc.game.dto.getGameEntry.response;

import com.threeNerds.basketballDiary.mvc.game.dto.QuarterPlayerRecordDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class GetGameEntryResponse {

    private QuarterTeamEntryDTO homeTeamEntry;
    private QuarterTeamEntryDTO awayTeamEntry;

    public GetGameEntryResponse homeTeamEntry( QuarterTeamEntryDTO homeTeamEntry ) {
        this.homeTeamEntry = homeTeamEntry;
        return this;
    }

    public GetGameEntryResponse awayTeamEntry( QuarterTeamEntryDTO awayTeamEntry ) {
        this.awayTeamEntry = awayTeamEntry;
        return this;
    }
}
