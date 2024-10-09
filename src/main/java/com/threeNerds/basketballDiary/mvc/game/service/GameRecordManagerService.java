package com.threeNerds.basketballDiary.mvc.game.service;

import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import com.threeNerds.basketballDiary.constant.code.type.QuarterCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.mvc.game.domain.*;

import com.threeNerds.basketballDiary.mvc.game.domain.repository.*;
import com.threeNerds.basketballDiary.mvc.game.mapper.dto.*;


import com.threeNerds.basketballDiary.mvc.game.mapper.GameRecordMapper;
import com.threeNerds.basketballDiary.mvc.game.service.dto.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GameRecordManagerService {

    private final GameRepository gameRepository;
    private final GameJoinTeamRepository gameJoinTeamRepo;
    private final GameJoinPlayerRepository gameJoinPlayerRepo;
    private final QuarterTeamRecordsRepository quarterTeamRecordsRepository;
    private final QuarterPlayerRecordsRepository quarterPlayerRecordsRepository;

    private final GameRecordMapper gameRecordManagerRepository;

    /**
     * 22.11.06
     * 특정쿼터의 선수별 기록조회(목록)
     * homeAwayCode에 따라 특정쿼터의 선수별 기록 목록을 조회한다.
     * @author 강창기
     * @update 여인준 23.04.08 : 파라미터 및 조회 데이터 변경
     */
    public GameJoinPlayerRecordQuery.Result getGameJoinPlayerQuarterRecords( GameJoinPlayerRecordQuery query ) {
        Long gameSeq        = query.getGameSeq();
        String homeAwayCode = query.getHomeAwayCode();
        String quarterCode  = query.getQuarterCode();

        /** gameSeq에 해당하는 게임내역이 존재하는지 확인. */
        boolean isNotExistGame = null == gameRepository.findGame( gameSeq );
        if( isNotExistGame ) {
            throw new CustomException( DomainErrorType.NOT_FOUND_GAME );
        }

        SearchGameDTO searchParams = new SearchGameDTO()
                .gameSeq( gameSeq )
                .homeAwayCode( homeAwayCode )
                .quarterCode( quarterCode );

        List<PlayerQuarterRecordDTO> players = gameRecordManagerRepository.findAllPlayerRecordsByQuarter( searchParams );
        return query.buildResult(
            filterPlayersByHomeAwayCode( players, HomeAwayCode.HOME_TEAM ),
            filterPlayersByHomeAwayCode( players, HomeAwayCode.AWAY_TEAM )
        );
    }

    private List<PlayerQuarterRecordDTO> filterPlayersByHomeAwayCode( List<PlayerQuarterRecordDTO> targetPlayers, HomeAwayCode filterCode ) {
        /** 홈/어웨이팀 구분에 따른 처리 */
        return targetPlayers.stream()
                .filter( t -> filterCode.getCode().equals( t.getHomeAwayCode() ) )
                .collect(Collectors.toList());
    }

    /**
     * 22.11.22
     * 특정쿼터의 홈·어웨이 팀기록조회
     * 홈 & 어웨이의 쿼터별 팀 합산기록을 조회한다.
     * @author 강창기
     * - 23.03.05 여인준 : API수정에 따른 파라미터 및 return 클래스 변경
     */
    public GameQuarterQuery.Result getGameQuarterRecords( GameQuarterQuery query ) {
        Long gameSeq        = query.getGameSeq();
        String quarterCode  = query.getQuarterCode();
        Game gameInfo       = gameRepository.findGame( gameSeq );
        if ( null == gameInfo ) {
            throw new CustomException( DomainErrorType.NOT_FOUND_GAME );
        }

        SearchGameDTO gameSearchCond = new SearchGameDTO()
                .gameSeq( gameSeq )
                .quarterCode( quarterCode );
        List<TeamQuarterRecordsDTO> allTeamsQuarterRecords = gameRecordManagerRepository.findAllTeamsQuarterRecords(gameSearchCond);

        if ( !allTeamsQuarterRecords.isEmpty() ) {
            return query.buildResult( gameInfo, allTeamsQuarterRecords );
        }

        /** 쿼터기록이 입력되지 않은 경우 - 초기값 return */
        List<GameJoinTeam> gameJoinTeams = gameJoinTeamRepo.findAllGameJoinTeam( gameSeq );
        return query.buildResult( gameInfo, toTeamQuarterRecordsList( gameJoinTeams ) );
    }

    private List<TeamQuarterRecordsDTO> toTeamQuarterRecordsList( List<GameJoinTeam> gameJoinTeams ) {
        return gameJoinTeams.stream()
                .map( gjt -> new TeamQuarterRecordsDTO()
                        .gameJoinTeamSeq(   gjt.getGameJoinTeamSeq() )
                        .teamName(          gjt.getTeamName() )
                        .homeAwayCode(      gjt.getHomeAwayCode() ) )
                .collect( Collectors.toList() );
    }


    /**
     * 22.12.25
     * 전체쿼터의 홈·어웨이 팀기록조회
     * 홈 & 어웨이의 쿼터별 팀 합산기록을 조회한다.
     * @author 강창기
     */

    public GameAllRecordsQuery.Result getGameAllRecords( GameAllRecordsQuery query ) {
        Long gameSeq = query.getGameSeq();
        Game game = Optional
                .ofNullable( gameRepository.findGame( gameSeq ) )
                .orElseThrow( () -> new CustomException(DomainErrorType.NOT_FOUND_GAME) );

        QuarterAllTeamsRecordsDTO firstQuarterAllRecords    = null;
        QuarterAllTeamsRecordsDTO secondQuarterAllRecords   = null;
        QuarterAllTeamsRecordsDTO thirdQuarterAllRecords    = null;
        QuarterAllTeamsRecordsDTO fourthQuarterAllRecords   = null;
        List<QuarterTeamRecordsDTO> allQuarterRecords = gameRecordManagerRepository.findAllQuarterRecords( gameSeq );
        if ( !allQuarterRecords.isEmpty() ) {
            QuarterCode[] quarterCodes = QuarterCode.values();
            for ( QuarterCode quarterCode : quarterCodes ) {
                List<QuarterTeamRecordsDTO> quarterRecords = allQuarterRecords.stream()
                        .filter( r -> quarterCode.getCode().equals( r.getQuarterCode() ) )
                        .collect( Collectors.toList() );
                switch ( quarterCode ) {
                    case FIRST  : firstQuarterAllRecords = buildQuarterAllTeamsRecords( quarterRecords );
                    case SECOND : secondQuarterAllRecords = buildQuarterAllTeamsRecords( quarterRecords );
                    case THIRD  : thirdQuarterAllRecords = buildQuarterAllTeamsRecords( quarterRecords );
                    case FOURTH : fourthQuarterAllRecords = buildQuarterAllTeamsRecords( quarterRecords );
                }
            }
        }
        return query.buildResult( game.getGameRecordStateCode(), firstQuarterAllRecords, secondQuarterAllRecords, thirdQuarterAllRecords, fourthQuarterAllRecords );
    }

    private QuarterAllTeamsRecordsDTO buildQuarterAllTeamsRecords( List<QuarterTeamRecordsDTO> quarterRecords ) {
        if ( quarterRecords.isEmpty() ) {
            return null;
        }
        QuarterTeamRecordsDTO homeTeamRecords = filterTeamRecords( HomeAwayCode.HOME_TEAM, quarterRecords );
        QuarterTeamRecordsDTO awayTeamRecords = filterTeamRecords( HomeAwayCode.AWAY_TEAM, quarterRecords );
        return new QuarterAllTeamsRecordsDTO()
                        .quarterCode(       homeTeamRecords.getQuarterCode() )
                        .quarterTime(       homeTeamRecords.getQuarterTime() )
                        .homeTeamRecords(   homeTeamRecords )
                        .awayTeamRecords(   awayTeamRecords );
    }

    private QuarterTeamRecordsDTO filterTeamRecords( HomeAwayCode homeAwayCode, List<QuarterTeamRecordsDTO> quarterRecords ) {
        return quarterRecords.stream()
                .filter( r -> homeAwayCode.getCode().equals( r.getHomeAwayCode() ) )
                .findAny()
                .orElse( new QuarterTeamRecordsDTO() );
    }

    /**
     * 2022.12.05
     * 쿼터 삭제
     * @author 이성주
     */
    public void deleteGameQuarter( GameQuarterCommand command ) {
        Long gameSeq        = command.getGameSeq();
        String quarterCode  = command.getQuarterCode();
        quarterTeamRecordsRepository.deleteGameQuarter( QuarterTeamRecords.builder()
                                                            .gameSeq(gameSeq)
                                                            .quarterCode(quarterCode)
                                                            .build() );
        quarterPlayerRecordsRepository.deleteGameQuarter( QuarterPlayerRecords.builder()
                                                            .gameSeq( gameSeq )
                                                            .quarterCode( quarterCode )
                                                            .build() );
    }

    /**
     * 23.03.10
     * 게임쿼터 기초정보 생성
     *      - gameSeq로 게임참가팀을 조회해서
     *      - quarterCode로 팀의 쿼터 기록 테이블을 insert친다
     * @author 여인준
     */
    public void createGameQuarterBasicInfo( GameQuarterCommand command ) {

        Long gameSeq        = command.getGameSeq();
        String quarterCode  = command.getQuarterCode();

        /** 게임기록 수정가능여부 확인 */
        Game game = gameRepository.findGame( gameSeq );
        if ( !game.canUpdateRecord() ) {
            throw new CustomException( DomainErrorType.CANT_ADD_QUARTER_RECORD );
        }

        /** 게임참가팀 지정유무 검증 */
        List<GameJoinTeam> gameJoinTeams = gameJoinTeamRepo.findAllGameJoinTeam( gameSeq );
        if ( 2 != gameJoinTeams.size() ) {
            throw new CustomException( DomainErrorType.INSUFFICIENT_GAME_JOIN_TEAMS );
        }

        /** 게임참가선수 검증 - 홈팀과 어웨이팀 게임참가선수 각가 5명 이상이어야 한다. */
        List<GameJoinPlayer> gameJoinPlayers = gameJoinPlayerRepo.findAllPlayersOnGame( gameSeq );
        if ( !hasEnoughPlayersOnGame( gameJoinPlayers ) ) {
            throw new CustomException( DomainErrorType.INSUFFICIENT_PLAYERS_ON_GAME );
        }

        for ( GameJoinTeam joinTeam : gameJoinTeams ) {
            quarterTeamRecordsRepository.save( new QuarterTeamRecords(
                    gameSeq ,                           joinTeam.getHomeAwayCode() ,
                    joinTeam.getGameJoinTeamSeq() ,     quarterCode
            ) );
        }
    }

    private boolean hasEnoughPlayersOnGame( List<GameJoinPlayer> allPlayersOnGame ) {
        int homeTeamPlayerCnt = 0;
        int awayTeamPlayerCnt = 0;

        for ( GameJoinPlayer player : allPlayersOnGame ) {
            String homeAwayCode = player.getHomeAwayCode();
            if ( HomeAwayCode.HOME_TEAM.getCode().equals( homeAwayCode ) ) {
                homeTeamPlayerCnt++;
            }
            if ( HomeAwayCode.AWAY_TEAM.getCode().equals( homeAwayCode ) ) {
                awayTeamPlayerCnt++;
            }
            if ( homeTeamPlayerCnt >= 5 && awayTeamPlayerCnt >= 5 ) {
                return true;
            }
        }
        return false;
    }

    /**
     * 23.03.19
     * 게임쿼터 기록 저장 및 수정
     *      - 화면에서 게임쿼터기록을 홈팀과 어웨이팀의 선수기록으로 받아서
     *      - QuarterTeamRecords와 QuarterPlayerRecords테이블을 업데이트 친다.
     *      - 해당 서비스의 전제 조건 : QuarterTeamRecords와 QuarterPlayerRecords에 데이터가 이미 존재해야 한다.
     * @author 여인준
     */
    public void saveQuarterRecord( QuarterRecordCommand command ) {

        Long gameSeq        = command.getGameSeq();
        String quarterCode  = command.getQuarterCode();
        String quarterTime  = command.getQuarterTime();
        /** 경기의 쿼터 시간 update */
        gameRepository.updateQuarterTime( new Game( gameSeq, QuarterCode.getType( quarterCode ), quarterTime ) );

        /** 출전선수(inGamePlayer)의 쿼터기록(스탯) 저장 */
        saveInGamePlayerStat( command.getHomeTeamPlayerRecords() );
        saveInGamePlayerStat( command.getAwayTeamPlayerRecords() );

        /** 모든 선수들의 쿼터기록 전체 조회 */
        QuarterPlayerRecords inqCondAllPlayerQuarterRecords = QuarterPlayerRecords.builder()
                                                                    .gameSeq( gameSeq )
                                                                    .quarterCode( quarterCode )
                                                                    .build();
        List<QuarterPlayerRecords> allPlayerQuarterRecords = quarterPlayerRecordsRepository.findAllPlayerQuarterRecords( inqCondAllPlayerQuarterRecords );

        /**
         * 모든 선수들의 쿼터기록을 팀기록으로 합산하여 팀기록 update
         *  - 화면의 팀기록과 DB에 저장한 팀별 선수들의 쿼터기록 합산 비교로직은 반영하지 않음.
         *    DB에서 관리하고 있는 값을 기준으로 데이터를 관리하는 것이 맞고, 저장한 팀 쿼터기록과 화면에 입력된 데이터가 상이할 경우 그떄 조치하기
         **/
        quarterTeamRecordsRepository.updateQuarterRecords( QuarterTeamRecords.ofHome( gameSeq, QuarterCode.getType( quarterCode ), allPlayerQuarterRecords ) );
        quarterTeamRecordsRepository.updateQuarterRecords( QuarterTeamRecords.ofAway( gameSeq, QuarterCode.getType( quarterCode ), allPlayerQuarterRecords ) );
    }

    private void saveInGamePlayerStat( List<SavePlayerRecordDTO> inGamePlayerRecords ) {
        for ( SavePlayerRecordDTO playerRecord : inGamePlayerRecords ) {
            QuarterPlayerRecords quarterPlayerRecords = QuarterPlayerRecords.builder()
                    .quarterPlayerRecordsSeq(   playerRecord.getQuarterPlayerRecordsSeq() )
                    .gameSeq(                   playerRecord.getGameSeq() )
                    .homeAwayCode(              playerRecord.getHomeAwayCode() )
                    .quarterCode(               playerRecord.getQuarterCode() )
                    .tryFreeThrow(              playerRecord.getTryFreeThrow() )
                    .tryTwoPoint(               playerRecord.getTryTwoPoint() )
                    .tryThreePoint(             playerRecord.getTryThreePoint() )
                    .freeThrow(                 playerRecord.getFreeThrow() )
                    .twoPoint(                  playerRecord.getTwoPoint() )
                    .threePoint(                playerRecord.getThreePoint() )
                    .rebound(                   playerRecord.getRebound() )
                    .assist(                    playerRecord.getAssist() )
                    .steal(                     playerRecord.getSteal() )
                    .block(                     playerRecord.getBlock() )
                    .turnover(                  playerRecord.getTurnover() )
                    .foul(                      playerRecord.getFoul() )
                    .build();
            quarterPlayerRecordsRepository.updateQuarterRecords( quarterPlayerRecords );
        }
    }
}