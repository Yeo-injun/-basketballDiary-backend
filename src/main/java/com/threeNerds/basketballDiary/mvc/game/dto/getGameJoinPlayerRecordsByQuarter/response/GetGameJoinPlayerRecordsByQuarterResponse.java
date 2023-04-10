package com.threeNerds.basketballDiary.mvc.game.dto.getGameJoinPlayerRecordsByQuarter.response;

import lombok.Getter;

import java.util.List;

@Getter
public class GetGameJoinPlayerRecordsByQuarterResponse {
    private Long gameSeq;
    private String quarterCode;

    private List<PlayerQuarterRecordDTO> homeTeamPlayers;
    private List<PlayerQuarterRecordDTO> awayTeamPlayers;

    public GetGameJoinPlayerRecordsByQuarterResponse gameSeq(Long gameSeq ) {
        this.gameSeq = gameSeq;
        return this;
    }

    public GetGameJoinPlayerRecordsByQuarterResponse quarterCode(String quarterCode ) {
        this.quarterCode = quarterCode;
        return this;
    }

    public GetGameJoinPlayerRecordsByQuarterResponse homeTeamPlayers( List<PlayerQuarterRecordDTO> homeTeamPlayers ) {
        this.homeTeamPlayers = homeTeamPlayers;
        return this;
    }

    public GetGameJoinPlayerRecordsByQuarterResponse awayTeamPlayers( List<PlayerQuarterRecordDTO> awayTeamPlayers ) {
        this.awayTeamPlayers = awayTeamPlayers;
        return this;
    }
}
