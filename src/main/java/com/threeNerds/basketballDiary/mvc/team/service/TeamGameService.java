package com.threeNerds.basketballDiary.mvc.team.service;

import com.threeNerds.basketballDiary.constant.code.type.GameRecordStateCode;

import com.threeNerds.basketballDiary.mvc.game.domain.*;
import com.threeNerds.basketballDiary.mvc.game.repository.*;
import com.threeNerds.basketballDiary.mvc.team.dto.SearchTeamGameDTO;
import com.threeNerds.basketballDiary.mvc.team.dto.TeamGameDTO;
import com.threeNerds.basketballDiary.mvc.team.dto.TeamGameRecordDTO;
import com.threeNerds.basketballDiary.mvc.team.repository.dto.TeamGameRepository;
import com.threeNerds.basketballDiary.mvc.team.service.dto.TeamGameQuery;
import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TeamGameService {

    /**---------------------------
     * 도메인 Entity 레포지토리
     **---------------------------*/
    private final GameJoinTeamRepository gameJoinTeamRepository;
    private final QuarterTeamRecordsRepository quarterTeamRecordsRepository;

    /**---------------------------
     * 레포지토리
     **---------------------------*/
    private final TeamGameRepository teamGameRepository;

    /** 22.11.06
     * 소속팀의 게임기록조회
     * @author 여인준
     **/
    public TeamGameQuery.Result searchGames( TeamGameQuery query ) {

        /** 경기목록 조회 */
        Pagination pagination   = Pagination.of( query.getPageNo(), 5 );
        SearchTeamGameDTO cond  = new SearchTeamGameDTO()
                .pagination(    pagination )
                .setSearchSpan( query.getGameBgngYmd(), query.getGameEndYmd() )
                .teamSeq( 		query.getTeamSeq() )
                .gameTypeCode( 	query.getGameTypeCode() )
                .homeAwayCode( 	query.getHomeAwayCode() )
                .gamePlaceName( query.getGamePlaceName() );
        List<TeamGameDTO> teamGames = teamGameRepository.findPagingTeamGamesByTeamSeq( cond );

        if ( teamGames.isEmpty() ) {
            return query.buildResult( Collections.emptyList(), pagination.empty() );
        }

        /** 경기별 팀 기록 정보 조회 */
        for ( TeamGameDTO teamGame : teamGames ) {
            if ( GameRecordStateCode.CREATION.getCode().equals( teamGame.getGameRecordStateCode() ) ) {
                continue;
            }
            List<GameJoinTeam> joinTeams = gameJoinTeamRepository.findAllGameJoinTeam( teamGame.getGameSeq() );

            for ( GameJoinTeam team : joinTeams ) {
                List<QuarterTeamRecords> teamGameAllQuarterRecords = quarterTeamRecordsRepository.findQuarterRecordsByJoinTeamSeq( team.getGameJoinTeamSeq() );
                TeamGameRecordDTO recordDTO = new TeamGameRecordDTO( team, teamGameAllQuarterRecords );
                if ( team.isHomeTeam() ) {
                    teamGame.homeTeamRecord( recordDTO );
                } else {
                    teamGame.awayTeamRecord( recordDTO );
                }
            }
        }

        return query.buildResult( teamGames, pagination.getPages( teamGameRepository.findTotalCountTeamGamesByTeamSeq( cond ) ) );
    }


}