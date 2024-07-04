package com.threeNerds.basketballDiary.mvc.game.controller.response;

import com.threeNerds.basketballDiary.mvc.game.dto.TeamQuarterRecordsDTO;
import com.threeNerds.basketballDiary.mvc.game.service.dto.GameQuarterQuery;
import lombok.Getter;

@Getter
public class GetGameQuarterRecordsResponse {

    private Long gameSeq;
    private String quarterCode;
    private String quarterCodeName;

    private String gameYmd;
    private String gameStartTime;       /* 게임시작시간 */
    private String gameEndTime;         /* 게임종료시간 */
    private String quarterTime;

    private TeamQuarterRecordsDTO homeTeamRecord;
    private TeamQuarterRecordsDTO awayTeamRecord;

    public GetGameQuarterRecordsResponse( GameQuarterQuery.Result result ) {
        this.gameSeq            = result.getGameSeq();
        this.quarterCode        = result.getQuarterCode();
        this.quarterCodeName    = result.getQuarterCodeName();

        this.gameYmd            = result.getGameYmd();
        this.gameStartTime      = result.getGameStartTime();
        this.gameEndTime        = result.getGameEndTime();
        this.quarterTime        = result.getQuarterTime();

        this.homeTeamRecord = result.getHomeTeamRecord();
        this.awayTeamRecord = result.getAwayTeamRecord();
    }
}
