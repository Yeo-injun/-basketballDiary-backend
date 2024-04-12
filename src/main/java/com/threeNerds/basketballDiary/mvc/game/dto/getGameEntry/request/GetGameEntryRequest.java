package com.threeNerds.basketballDiary.mvc.game.dto.getGameEntry.request;

import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import com.threeNerds.basketballDiary.constant.code.type.QuarterCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.SystemErrorType;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;

@Getter
public class GetGameEntryRequest {

    private final Long gameSeq;
    private final String quarterCode;
    private final String homeAwayCode;

    public GetGameEntryRequest ( Long gameSeq, String quarterCode, String homeAwayCode ) {
        this.gameSeq = gameSeq;
        this.quarterCode = quarterCode;
        this.homeAwayCode = homeAwayCode;
        this.validate();
    }

    private void validate() {
        // 필수값 체크
        Object[] notNullValues = new Object[] { gameSeq, quarterCode };
        if ( Arrays.stream(notNullValues).anyMatch( Objects::isNull ) ) {
            throw new CustomException( SystemErrorType.NOT_NULLABLE_VALUE );
        }
        QuarterCode.checkDomain( quarterCode );

        if ( StringUtils.hasText( homeAwayCode ) ) {
            HomeAwayCode.checkDomain( homeAwayCode );
        }
    }
}
