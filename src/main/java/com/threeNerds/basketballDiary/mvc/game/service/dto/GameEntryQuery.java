package com.threeNerds.basketballDiary.mvc.game.service.dto;

import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.mvc.game.dto.QuarterTeamEntryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameEntryQuery {

    private Long gameSeq;
    private String quarterCode;
    private String homeAwayCode;

    @Getter
    public class Result {
        private final QuarterTeamEntryDTO homeTeamEntry;
        private final QuarterTeamEntryDTO awayTeamEntry;

        Result( QuarterTeamEntryDTO homeTeamEntry, QuarterTeamEntryDTO awayTeamEntry ) {
            this.homeTeamEntry  = homeTeamEntry;
            this.awayTeamEntry  = awayTeamEntry;
        }
    }

    public Result buildResult( QuarterTeamEntryDTO homeTeamEntry, QuarterTeamEntryDTO awayTeamEntry ) {
        return new Result( homeTeamEntry, awayTeamEntry );
    }

    public Result buildResult( HomeAwayCode homeAwayType, QuarterTeamEntryDTO teamEntry ) {
        switch ( homeAwayType ) {
            case HOME_TEAM : return new Result( teamEntry, null );
            case AWAY_TEAM : return new Result( null, teamEntry );
            default :
                throw new CustomException( DomainErrorType.INVALID_HOME_AWAY_CODE );
        }
    }

}
