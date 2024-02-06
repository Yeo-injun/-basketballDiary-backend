package com.threeNerds.basketballDiary.mvc.game.dto.getGameEntry.request;

import com.threeNerds.basketballDiary.constant.code.HomeAwayCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.exception.error.SystemErrorType;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public class GetGameEntryRequest {

    private Long gameSeq;
    private String quarterCode;
    private String homeAwayCode;

    public GetGameEntryRequest ( Long gameSeq, String quarterCode, String homeAwayCode ) {
        this.gameSeq = gameSeq;
        this.quarterCode = quarterCode;
        this.homeAwayCode = homeAwayCode;
        this.validate();
    }

    public boolean validate() {
        // 필수값 체크
        Object[] notNullValues = new Object[] { gameSeq, quarterCode, homeAwayCode };
        if ( Arrays.stream(notNullValues).anyMatch( Objects::isNull ) ) {
            throw new CustomException( SystemErrorType.NOT_NULLALLBE_VALUE );
        }

        if ( !HomeAwayCode.isCodeDomain( homeAwayCode ) ) {
            throw new CustomException( DomainErrorType.INVALID_HOME_AWAY_CODE_DOMAIN );
        }

        // TODO 쿼터코드 도에인 체크
//        if ( ) {
//
//        }

        return true;
    }
}
