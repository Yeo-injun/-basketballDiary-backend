package com.threeNerds.basketballDiary.mvc.game.dto.getGameQuarterRecords.response;

import com.threeNerds.basketballDiary.constant.code.QuarterCode;
import com.threeNerds.basketballDiary.mvc.game.domain.Game;
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

    private TeamQuarterRecordsDTO homeTeamRecords;
    private TeamQuarterRecordsDTO awayTeamRecords;

    public GetGameQuarterRecordsResponse(
            Game gameInfo,
            String quarterCode,
            TeamQuarterRecordsDTO homeTeamRecords,
            TeamQuarterRecordsDTO awayTeamRecords
    ) {
        this.gameSeq = gameInfo.getGameSeq();
        this.quarterCode = quarterCode;
        this.quarterCodeName = QuarterCode.nameOf( quarterCode );
        this.gameYmd = gameInfo.getGameYmd();
        this.gameStartTime = gameInfo.getGameStartTime();
        this.gameEndTime = gameInfo.getGameEndTime();
        this.homeTeamRecords = homeTeamRecords;
        this.awayTeamRecords = awayTeamRecords;
    }
//    public GetGameQuarterRecordsResponse gameSeq(Long gameSeq) {
//        this.gameSeq = gameSeq;
//        return this;
//    }
//
//    public GetGameQuarterRecordsResponse quarterCode(String quarterCode) {
//        this.quarterCode = quarterCode;
//        return this;
//    }
//    public GetGameQuarterRecordsResponse quarterCodeName(String quarterCodeName) {
//        this.quarterCodeName = quarterCodeName;
//        return this;
//    }
//    public GetGameQuarterRecordsResponse gameYmd(String gameYmd) {
//        this.gameYmd = gameYmd;
//        return this;
//    }
//    public GetGameQuarterRecordsResponse gameStartTime(String gameStartTime) {
//        this.gameStartTime = gameStartTime;
//        return this;
//    }
//    public GetGameQuarterRecordsResponse gameEndTime(String gameEndTime) {
//        this.gameEndTime = gameEndTime;
//        return this;
//    }
//    public GetGameQuarterRecordsResponse quarterTime(String quarterTime) {
//        this.quarterTime = quarterTime;
//        return this;
//    }
//
//    public GetGameQuarterRecordsResponse homeTeamRecords(TeamQuarterRecordsDTO homeTeamRecords) {
//        this.homeTeamRecords = homeTeamRecords;
//        return this;
//    }
//    public GetGameQuarterRecordsResponse awayTeamRecords(TeamQuarterRecordsDTO awayTeamRecords) {
//        this.awayTeamRecords = awayTeamRecords;
//        return this;
//    }
}