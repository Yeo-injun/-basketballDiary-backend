package com.threeNerds.basketballDiary.mvc.game.dto.saveQuarterRecords.request;

import lombok.Getter;

import java.util.List;


@Getter
public class SaveQuarterRecordsRequest {

    private Long gameSeq;
    private String quarterCode;
    private List<SavePlayerRecordDTO> homeTeamPlayerRecords;
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
