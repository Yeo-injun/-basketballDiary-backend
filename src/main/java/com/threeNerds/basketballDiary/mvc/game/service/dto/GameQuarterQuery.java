package com.threeNerds.basketballDiary.mvc.game.service.dto;

import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import com.threeNerds.basketballDiary.constant.code.type.QuarterCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.mvc.game.domain.Game;
import com.threeNerds.basketballDiary.mvc.game.domain.GameJoinTeam;
import com.threeNerds.basketballDiary.mvc.game.dto.GameDetailDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameQuarterRecords.response.TeamQuarterRecordsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameQuarterQuery {

    private Long gameSeq;
    private String quarterCode;

    @Getter
    public class Result {

        private final Long gameSeq;
        private final String quarterCode;
        private final String quarterCodeName;
        private final String gameYmd;
        private final String gameStartTime;
        private final String gameEndTime;
        private final String quarterTime;

        private final TeamQuarterRecordsDTO homeTeamRecord;
        private final TeamQuarterRecordsDTO awayTeamRecord;

        protected Result(
            Long gameSeq,
            String quarterCode,
            Game gameInfo,
            TeamQuarterRecordsDTO homeTeamRecord,
            TeamQuarterRecordsDTO awayTeamRecord
        ) {
            this.gameSeq            = gameSeq;
            this.quarterCode        = quarterCode;
            this.quarterCodeName    = QuarterCode.nameOf( quarterCode );
            this.gameYmd            = gameInfo.getGameYmd();
            this.gameStartTime      = gameInfo.getGameStartTime();
            this.gameEndTime        = gameInfo.getGameEndTime();
            this.quarterTime        = gameInfo.getQuarterTime( QuarterCode.getType( this.quarterCode ) );
            this.homeTeamRecord     = homeTeamRecord;
            this.awayTeamRecord     = awayTeamRecord;
        }
    }

    public GameQuarterQuery.Result buildResult( Game gameInfo, List<TeamQuarterRecordsDTO> joinTeamQuarterRecords ) {
        return new GameQuarterQuery.Result(
                    this.gameSeq,
                    this.quarterCode,
                    gameInfo,
                    getTeamRecord( joinTeamQuarterRecords, HomeAwayCode.HOME_TEAM ),
                    getTeamRecord( joinTeamQuarterRecords, HomeAwayCode.AWAY_TEAM )
                );
    }
    private TeamQuarterRecordsDTO getTeamRecord(List<TeamQuarterRecordsDTO> joinTeamQuarterRecords, HomeAwayCode homeAwayType ) {
        for ( TeamQuarterRecordsDTO teamRecord : joinTeamQuarterRecords ) {
            if ( homeAwayType == HomeAwayCode.typeOf( teamRecord.getHomeAwayCode() ) ) {
                return teamRecord;
            }
        }
        throw new CustomException( DomainErrorType.INVALID_HOME_AWAY_CODE );
    }
}
