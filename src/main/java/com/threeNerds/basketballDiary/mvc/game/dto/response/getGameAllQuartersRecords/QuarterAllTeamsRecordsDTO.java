package com.threeNerds.basketballDiary.mvc.game.dto.response.getGameAllQuartersRecords;

import lombok.Getter;

@Getter
public class QuarterAllTeamsRecordsDTO {

    private String quarterCode;
    private String quarterCodeName;
    private String quarterTime;

    private QuarterTeamRecordsDTO homeTeamRecords;
    private QuarterTeamRecordsDTO awayTeamRecords;

}
