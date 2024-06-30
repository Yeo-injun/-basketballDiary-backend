package com.threeNerds.basketballDiary.mvc.game.controller.response;

import com.threeNerds.basketballDiary.mvc.game.dto.QuarterAllTeamsRecordsDTO;
import com.threeNerds.basketballDiary.mvc.game.service.dto.GameAllRecordsQuery;
import lombok.Getter;

@Getter
public class GetGameAllRecordsResponse {

    private Long gameSeq;
    private String gameRecordStateCode;
    private String gameRecordStateCodeName;
    private QuarterAllTeamsRecordsDTO teamsRecords1stQuarter;
    private QuarterAllTeamsRecordsDTO teamsRecords2ndQuarter;
    private QuarterAllTeamsRecordsDTO teamsRecords3rdQuarter;
    private QuarterAllTeamsRecordsDTO teamsRecords4thQuarter;

    public GetGameAllRecordsResponse ( GameAllRecordsQuery.Result result ) {
        this.gameSeq                    = result.getGameSeq();
        this.gameRecordStateCode        = result.getGameRecordStateCode();
        this.gameRecordStateCodeName    = result.getGameRecordStateCodeName();
        this.teamsRecords1stQuarter     = result.getFirstQuarterRecord();
        this.teamsRecords2ndQuarter     = result.getSecondQuarterRecord();
        this.teamsRecords3rdQuarter     = result.getThirdQuarterRecord();
        this.teamsRecords4thQuarter     = result.getFourthQuarterRecord();
    }
}
