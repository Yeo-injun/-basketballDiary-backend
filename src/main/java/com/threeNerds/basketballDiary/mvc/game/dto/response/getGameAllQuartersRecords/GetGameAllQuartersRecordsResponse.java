package com.threeNerds.basketballDiary.mvc.game.dto.response.getGameAllQuartersRecords;

import lombok.Getter;

@Getter
public class GetGameAllQuartersRecordsResponse {

    private Long gameSeq;
    private QuarterAllTeamsRecordsDTO teamsRecords1stQuarter;
    private QuarterAllTeamsRecordsDTO teamsRecords2ndQuarter;
    private QuarterAllTeamsRecordsDTO teamsRecords3rdQuarter;
    private QuarterAllTeamsRecordsDTO teamsRecords4thQuarter;
}
