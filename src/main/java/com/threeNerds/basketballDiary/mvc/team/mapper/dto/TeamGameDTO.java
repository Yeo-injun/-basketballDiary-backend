package com.threeNerds.basketballDiary.mvc.team.mapper.dto;

import com.threeNerds.basketballDiary.constant.code.type.GameRecordStateCode;
import com.threeNerds.basketballDiary.constant.code.type.GameTypeCode;
import lombok.Getter;

@Getter
public class TeamGameDTO {

    /** 데이터 속성 */
    private Long gameSeq;
    private String gameRecordStateCode;
    private String gameRecordStateCodeName;
    private String gameYmd;
    private String gamePlaceAddress;
    private String gamePlaceName;
    private String gameTypeCode;
    private String gameTypeCodeName;

    private TeamGameRecordDTO homeTeam;
    private TeamGameRecordDTO awayTeam;

    public void setGameRecordStateCode( String gameRecordStateCode ) {
        this.gameRecordStateCode        = gameRecordStateCode;
        this.gameRecordStateCodeName    = GameRecordStateCode.nameOf( gameRecordStateCode );
    }
    public void setGameTypeCodeName( String gameTypeCode ) {
        this.gameTypeCode       = gameTypeCode;
        this.gameTypeCodeName   = GameTypeCode.nameOf( gameTypeCode );
    }
    public TeamGameDTO homeTeamRecord( TeamGameRecordDTO homeTeamRecord ) {
        this.homeTeam = homeTeamRecord;
        return this;
    }
    public TeamGameDTO awayTeamRecord( TeamGameRecordDTO awayTeamRecord ) {
        this.awayTeam = awayTeamRecord;
        return this;
    }
}
