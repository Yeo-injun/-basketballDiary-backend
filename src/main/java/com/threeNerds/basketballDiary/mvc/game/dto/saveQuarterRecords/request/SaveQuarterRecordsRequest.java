package com.threeNerds.basketballDiary.mvc.game.dto.saveQuarterRecords.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
public class SaveQuarterRecordsRequest {

    private Long gameSeq;
    private String quarterCode;

    @NotEmpty
    private String quarterTime;
    @NotNull
    private List<SavePlayerRecordDTO> homeTeamPlayerRecords;
    @NotNull
    private List<SavePlayerRecordDTO> awayTeamPlayerRecords;

    public SaveQuarterRecordsRequest() {

    }

    public SaveQuarterRecordsRequest (
        Long gameSeq,
        String quarterCode,
        SaveQuarterRecordsRequest request
   ) {
        this.gameSeq = gameSeq;
        this.quarterCode = quarterCode;
        this.quarterTime = request.getQuarterTime();
        this.homeTeamPlayerRecords = request.getHomeTeamPlayerRecords();
        this.awayTeamPlayerRecords = request.getAwayTeamPlayerRecords();
    }
}
