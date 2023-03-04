package com.threeNerds.basketballDiary.mvc.game.dto.getGameQuarterRecords.request;

import com.threeNerds.basketballDiary.mvc.game.dto.getGameQuarterRecords.response.TeamQuarterRecordsDTO;
import lombok.Getter;

@Getter
public class GetGameQuarterRecordsRequest {

    private Long gameSeq;
    private String quarterCode;

    public GetGameQuarterRecordsRequest gameSeq(Long gameSeq) {
        this.gameSeq = gameSeq;
        return this;
    }

    public GetGameQuarterRecordsRequest quarterCode(String quarterCode) {
        this.quarterCode = quarterCode;
        return this;
    }
}
