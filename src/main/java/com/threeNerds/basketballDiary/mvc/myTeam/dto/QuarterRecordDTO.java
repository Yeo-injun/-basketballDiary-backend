package com.threeNerds.basketballDiary.mvc.myTeam.dto;

import lombok.Getter;

@Getter
public class QuarterRecordDTO {
    private Long quarterTeamRecordsSeq;
    private String quarterCode;
    private String quarterCodeName;
    private Integer quarterScore;


    public QuarterRecordDTO quarterTeamRecordsSeq(Long quarterTeamRecordsSeq) {
        this.quarterTeamRecordsSeq = quarterTeamRecordsSeq;
        return this;
    }

    public QuarterRecordDTO quarterCode(String quarterCode) {
        this.quarterCode = quarterCode;
        return this;
    }

    public QuarterRecordDTO quarterCodeName(String quarterCodeName) {
        this.quarterCodeName = quarterCodeName;
        return this;
    }

    public QuarterRecordDTO quarterScore(Integer quarterScore) {
        this.quarterScore = quarterScore;
        return this;
    }

}
