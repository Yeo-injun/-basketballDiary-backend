package com.threeNerds.basketballDiary.mvc.game.dto.saveQuarterRecords.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
public class SaveQuarterRecordsRequest {

    private Long gameSeq;
    @NotEmpty
    private String quarterCode;
    @NotNull
    private List<SavePlayerRecordDTO> homeTeamPlayerRecords;
    @NotNull
    private List<SavePlayerRecordDTO> awayTeamPlayerRecords;

    public SaveQuarterRecordsRequest gameSeq( Long gameSeq ) {
        this.gameSeq = gameSeq;
        return this;
    }

    public SaveQuarterRecordsRequest quarterCode( String quarterCode ) {
        this.quarterCode = quarterCode;
        return this;
    }
}
