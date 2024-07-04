package com.threeNerds.basketballDiary.mvc.game.service.dto;

import com.threeNerds.basketballDiary.constant.code.type.GameRecordStateCode;
import com.threeNerds.basketballDiary.mvc.game.dto.QuarterAllTeamsRecordsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameAllRecordsQuery {

    private Long gameSeq;

    @Getter
    public class Result {

        private Long gameSeq;
        private String gameRecordStateCode;
        private String gameRecordStateCodeName;
        private QuarterAllTeamsRecordsDTO firstQuarterRecord;
        private QuarterAllTeamsRecordsDTO secondQuarterRecord;
        private QuarterAllTeamsRecordsDTO thirdQuarterRecord;
        private QuarterAllTeamsRecordsDTO fourthQuarterRecord;

        protected Result(
                Long gameSeq,
                String gameRecordStateCode,
                QuarterAllTeamsRecordsDTO firstQuarterRecord,
                QuarterAllTeamsRecordsDTO secondQuarterRecord,
                QuarterAllTeamsRecordsDTO thirdQuarterRecord,
                QuarterAllTeamsRecordsDTO fourthQuarterRecord
        ) {
            this.gameSeq                    = gameSeq;
            this.gameRecordStateCode        = gameRecordStateCode;
            this.gameRecordStateCodeName    = GameRecordStateCode.nameOf( gameRecordStateCode );
            this.firstQuarterRecord         = firstQuarterRecord;
            this.secondQuarterRecord        = secondQuarterRecord;
            this.thirdQuarterRecord         = thirdQuarterRecord;
            this.fourthQuarterRecord        = fourthQuarterRecord;
        }
    }

    public GameAllRecordsQuery.Result buildResult(
            String gameRecordStateCode,
            QuarterAllTeamsRecordsDTO firstQuarterRecord,
            QuarterAllTeamsRecordsDTO secondQuarterRecord,
            QuarterAllTeamsRecordsDTO thirdQuarterRecord,
            QuarterAllTeamsRecordsDTO fourthQuarterRecord
    ) {
        return new GameAllRecordsQuery.Result(
                this.gameSeq,
                gameRecordStateCode,
                firstQuarterRecord,
                secondQuarterRecord,
                thirdQuarterRecord,
                fourthQuarterRecord
        );
    }
}
