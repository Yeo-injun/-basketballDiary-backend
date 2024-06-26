package com.threeNerds.basketballDiary.mvc.game.dto.getGameQuarterRecords.response;

import com.threeNerds.basketballDiary.constant.code.type.QuarterCode;
import com.threeNerds.basketballDiary.mvc.game.domain.Game;
import lombok.Getter;

@Getter
public class GetGameQuarterRecordsResponse {

    private Long gameSeq;
    private String gameYmd;
    private String gameStartTime;       /* 게임시작시간 */
    private String gameEndTime;         /* 게임종료시간 */

    private String quarterCode;
    private String quarterCodeName;
    private String quarterTime;

    private TeamQuarterRecordsDTO homeTeamRecord;
    private TeamQuarterRecordsDTO awayTeamRecord;

    public GetGameQuarterRecordsResponse(
            Game gameInfo,
            String quarterCode,
            TeamQuarterRecordsDTO homeTeamRecord,
            TeamQuarterRecordsDTO awayTeamRecord
    ) {
        this.gameSeq = gameInfo.getGameSeq();
        this.gameYmd = gameInfo.getGameYmd();
        this.gameStartTime = gameInfo.getGameStartTime();
        this.gameEndTime = gameInfo.getGameEndTime();

        this.quarterCode        = quarterCode;
        this.quarterCodeName    = QuarterCode.nameOf( quarterCode );
        this.quarterTime        = gameInfo.getQuarterTime( QuarterCode.getType( quarterCode ) );

        this.homeTeamRecord = homeTeamRecord;
        this.awayTeamRecord = awayTeamRecord;
    }
}
