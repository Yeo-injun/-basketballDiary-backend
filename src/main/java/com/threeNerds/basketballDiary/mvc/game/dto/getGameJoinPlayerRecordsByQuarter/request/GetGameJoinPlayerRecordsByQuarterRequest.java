package com.threeNerds.basketballDiary.mvc.game.dto.getGameJoinPlayerRecordsByQuarter.request;

import com.threeNerds.basketballDiary.http.RequestJsonBody;
import lombok.Getter;

@Getter
public class GetGameJoinPlayerRecordsByQuarterRequest extends RequestJsonBody {
    private Long gameSeq;
    private String quarterCode;
    private String homeAwayCode;

    public GetGameJoinPlayerRecordsByQuarterRequest gameSeq( Long gameSeq ) {
        this.gameSeq = gameSeq;
        return this;
    }

    public GetGameJoinPlayerRecordsByQuarterRequest quarterCode( String quarterCode ) {
        this.quarterCode = quarterCode;
        return this;
    }

    public GetGameJoinPlayerRecordsByQuarterRequest homeAwayCode( String homeAwayCode ) {
        this.homeAwayCode = homeAwayCode;
        return this;
    }
}
