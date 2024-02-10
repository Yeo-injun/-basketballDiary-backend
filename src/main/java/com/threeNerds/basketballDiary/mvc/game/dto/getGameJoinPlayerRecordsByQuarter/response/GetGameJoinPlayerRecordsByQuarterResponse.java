package com.threeNerds.basketballDiary.mvc.game.dto.getGameJoinPlayerRecordsByQuarter.response;

import com.threeNerds.basketballDiary.constant.code.type.QuarterCode;
import com.threeNerds.basketballDiary.http.ResponseJsonBody;
import lombok.Getter;

import java.util.List;

@Getter
public class GetGameJoinPlayerRecordsByQuarterResponse extends ResponseJsonBody {

    private Long gameSeq;
    private String quarterCode;
    private String quarterCodeName;

    private List<PlayerQuarterRecordDTO> homeTeamPlayers;
    private List<PlayerQuarterRecordDTO> awayTeamPlayers;

    public GetGameJoinPlayerRecordsByQuarterResponse (
            Long gameSeq,
            String quarterCode,
            List<PlayerQuarterRecordDTO> homeTeamPlayers,
            List<PlayerQuarterRecordDTO> awayTeamPlayers
    ) {
        this.gameSeq            = gameSeq;
        this.quarterCode        = quarterCode;
        this.quarterCodeName    = QuarterCode.nameOf( quarterCode );
        this.homeTeamPlayers    = homeTeamPlayers;
        this.awayTeamPlayers    = awayTeamPlayers;
    }
}
