package com.threeNerds.basketballDiary.mvc.game.dto.getGameAllQuartersRecords;

import com.threeNerds.basketballDiary.constant.code.GameRecordStateCode;
import com.threeNerds.basketballDiary.constant.code.QuarterCode;
import com.threeNerds.basketballDiary.mvc.game.domain.Game;
import lombok.Getter;

import java.util.Map;

@Getter
public class GetGameAllQuartersRecordsResponse {

    private Long gameSeq;
    private String gameRecordStateCode;
    private String gameRecordStateCodeName;
    private QuarterAllTeamsRecordsDTO teamsRecords1stQuarter;
    private QuarterAllTeamsRecordsDTO teamsRecords2ndQuarter;
    private QuarterAllTeamsRecordsDTO teamsRecords3rdQuarter;
    private QuarterAllTeamsRecordsDTO teamsRecords4thQuarter;

    public GetGameAllQuartersRecordsResponse (
            Game game,
            Map<QuarterCode, QuarterAllTeamsRecordsDTO> allQuartersRecordsMap
    ) {
        String gameRecordStateCode = game.getGameRecordStateCode();

        this.gameSeq = game.getGameSeq();
        this.gameRecordStateCode = gameRecordStateCode;
        this.gameRecordStateCodeName = GameRecordStateCode.nameOf(gameRecordStateCode);
        if ( allQuartersRecordsMap == null
             || allQuartersRecordsMap.isEmpty() ) {
            return;
        }

        this.teamsRecords1stQuarter = allQuartersRecordsMap.get(QuarterCode.FIRST);
        this.teamsRecords2ndQuarter = allQuartersRecordsMap.get(QuarterCode.SECOND);
        this.teamsRecords3rdQuarter = allQuartersRecordsMap.get(QuarterCode.THIRD);
        this.teamsRecords4thQuarter = allQuartersRecordsMap.get(QuarterCode.FOURTH);
    }
}
