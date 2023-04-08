package com.threeNerds.basketballDiary.mvc.game.dto.getGameJoinPlayerRecordsByQuarter.response;

import com.threeNerds.basketballDiary.mvc.game.dto.PlayerRecordDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class GetGameJoinPlayerRecordsByQuarterResponse {
    private Long gameSeq;
    private String quarterCode;

    private List<PlayerRecordDTO> homeTeamPlayers;
    private List<PlayerRecordDTO> awayTeamPlayers;

    public GetGameJoinPlayerRecordsByQuarterResponse gameSeq(Long gameSeq ) {
        this.gameSeq = gameSeq;
        return this;
    }

    public GetGameJoinPlayerRecordsByQuarterResponse quarterCode(String quarterCode ) {
        this.quarterCode = quarterCode;
        return this;
    }

    public GetGameJoinPlayerRecordsByQuarterResponse homeTeamPlayers( List<PlayerRecordDTO> homeTeamPlayers ) {
        this.homeTeamPlayers = homeTeamPlayers;
        return this;
    }

    public GetGameJoinPlayerRecordsByQuarterResponse awayTeamPlayers( List<PlayerRecordDTO> awayTeamPlayers ) {
        this.awayTeamPlayers = awayTeamPlayers;
        return this;
    }
}
