package com.threeNerds.basketballDiary.mvc.game.dto.response.getGameAllQuartersRecords;

import com.threeNerds.basketballDiary.constant.code.QuarterCode;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;

@Getter
public class GetGameAllQuartersRecordsResponse {

    private Long gameSeq;
    private QuarterAllTeamsRecordsDTO teamsRecords1stQuarter;
    private QuarterAllTeamsRecordsDTO teamsRecords2ndQuarter;
    private QuarterAllTeamsRecordsDTO teamsRecords3rdQuarter;
    private QuarterAllTeamsRecordsDTO teamsRecords4thQuarter;

    public GetGameAllQuartersRecordsResponse (
            Long gameSeq,
            Map<QuarterCode, QuarterAllTeamsRecordsDTO> allQuartersRecordsMap
    ) {

        this.gameSeq = gameSeq;
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
