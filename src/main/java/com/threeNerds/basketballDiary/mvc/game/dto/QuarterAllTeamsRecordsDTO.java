package com.threeNerds.basketballDiary.mvc.game.dto;

import com.threeNerds.basketballDiary.constant.code.type.QuarterCode;
import lombok.Getter;

@Getter
public class QuarterAllTeamsRecordsDTO {

    private String quarterCode;
    private String quarterCodeName;
    private String quarterTime;

    private QuarterTeamRecordsDTO homeTeamRecords;
    private QuarterTeamRecordsDTO awayTeamRecords;

    public QuarterAllTeamsRecordsDTO quarterCode(String quarterCode) {
        this.quarterCode = quarterCode;
        this.quarterCodeName = QuarterCode.nameOf(quarterCode);
        return this;
    }
    public QuarterAllTeamsRecordsDTO quarterTime(String quarterTime) {
        this.quarterTime = quarterTime;
        return this;
    }


    public QuarterAllTeamsRecordsDTO homeTeamRecords(QuarterTeamRecordsDTO homeTeamRecords) {
        this.homeTeamRecords = homeTeamRecords;
        return this;
    }

    public QuarterAllTeamsRecordsDTO awayTeamRecords(QuarterTeamRecordsDTO awayTeamRecords) {
        this.awayTeamRecords = awayTeamRecords;
        return this;
    }

}
