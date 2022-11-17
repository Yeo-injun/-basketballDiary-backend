package com.threeNerds.basketballDiary.mvc.myTeam.dto;

import com.threeNerds.basketballDiary.constant.code.GameRecordStateCode;
import com.threeNerds.basketballDiary.constant.code.GameTypeCode;
import lombok.Getter;

import java.util.List;

@Getter
public class GameRecordDTO {

    private Long gameSeq;
    private String gameRecordStateCode;
    private String gameRecordStateCodeName;
    private String gameYmd;
    private String gamePlaceAddress;
    private String gamePlaceName;
    private String gameTypeCode;
    private String gameTypeCodeName;

    private GameJoinTeamRecordDTO homeTeam;
    private GameJoinTeamRecordDTO awayTeam;

    public GameRecordDTO gameRecordStateCodeName(String gameRecordStateCode) {
        this.gameRecordStateCodeName = GameRecordStateCode.nameOf(gameRecordStateCode);
        return this;
    }
    public GameRecordDTO gameTypeCodeName(String gameTypeCode) {
        this.gameTypeCodeName = GameTypeCode.nameOf(gameTypeCode);
        return this;
    }
    public GameRecordDTO homeTeam(GameJoinTeamRecordDTO homeTeam) {
        this.homeTeam = homeTeam;
        return this;
    }
    public GameRecordDTO awayTeam(GameJoinTeamRecordDTO awayTeam) {
        this.awayTeam = awayTeam;
        return this;
    }
}