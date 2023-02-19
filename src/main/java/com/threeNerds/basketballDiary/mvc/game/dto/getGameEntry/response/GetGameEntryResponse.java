package com.threeNerds.basketballDiary.mvc.game.dto.getGameEntry.response;

import com.threeNerds.basketballDiary.mvc.game.dto.QuarterPlayerRecordDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class GetGameEntryResponse {

    private List<QuarterPlayerRecordDTO> homeTeamEntry;
    private List<QuarterPlayerRecordDTO> awayTeamEntry;

    public GetGameEntryResponse homeTeamEntry(List<QuarterPlayerRecordDTO> homeTeamEntry) {
        this.homeTeamEntry = homeTeamEntry;
        return this;
    }

    public GetGameEntryResponse awayTeamEntry(List<QuarterPlayerRecordDTO> awayTeamEntry) {
        this.awayTeamEntry = awayTeamEntry;
        return this;
    }
}
