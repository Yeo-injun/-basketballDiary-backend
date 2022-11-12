package com.threeNerds.basketballDiary.mvc.myTeam.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class GameRecordDTO {

    private Long gameSeq;
    private String gameYmd;
    private String gamePlaceAddress;
    private String gamePlaceName;
    private String gameTypeCode;
    private String gameTypeCodeName;

    private GameJoinTeamRecordDTO homeTeam;
    private GameJoinTeamRecordDTO awayTeam;

    public GameRecordDTO homeTeam(GameJoinTeamRecordDTO homeTeam) {
        this.homeTeam = homeTeam;
        return this;
    }
    public GameRecordDTO awayTeam(GameJoinTeamRecordDTO awayTeam) {
        this.awayTeam = awayTeam;
        return this;
    }
}
