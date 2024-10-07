package com.threeNerds.basketballDiary.mvc.game.service;

import com.threeNerds.basketballDiary.constant.code.type.GameRecordStateCode;
import com.threeNerds.basketballDiary.constant.code.type.GameTypeCode;
import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import com.threeNerds.basketballDiary.constant.code.type.PlayerTypeCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.mvc.game.mapper.dto.*;
import com.threeNerds.basketballDiary.mvc.game.domain.Game;
import com.threeNerds.basketballDiary.mvc.game.service.dto.*;
import com.threeNerds.basketballDiary.mvc.team.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.team.domain.repository.TeamMemberRepository;
import com.threeNerds.basketballDiary.mvc.team.domain.Team;
import com.threeNerds.basketballDiary.mvc.user.domain.User;
import com.threeNerds.basketballDiary.mvc.game.domain.GameJoinPlayer;
import com.threeNerds.basketballDiary.mvc.game.domain.GameJoinTeam;
import com.threeNerds.basketballDiary.mvc.game.domain.QuarterPlayerRecords;
import com.threeNerds.basketballDiary.mvc.game.domain.repository.GameJoinPlayerRepository;
import com.threeNerds.basketballDiary.mvc.game.domain.repository.GameJoinTeamRepository;
import com.threeNerds.basketballDiary.mvc.game.domain.repository.GameRepository;
import com.threeNerds.basketballDiary.mvc.game.domain.repository.QuarterPlayerRecordsRepository;
import com.threeNerds.basketballDiary.mvc.game.mapper.GameJoinMapper;
import com.threeNerds.basketballDiary.mvc.team.domain.repository.TeamRepository;
import com.threeNerds.basketballDiary.mvc.user.domain.repository.UserRepository;


import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GameJoinManagerService {

    private final UserRepository userRepo;
    private final TeamMemberRepository teamMemberRepo;
    private final TeamRepository teamRepository;
    private final GameRepository gameRepository;
    private final GameJoinTeamRepository gameJoinTeamRepository;
    private final GameJoinPlayerRepository gameJoinPlayerRepository;
    private final QuarterPlayerRecordsRepository quarterPlayerRecordsRepo;

    private final GameJoinMapper gameJoinManagerRepo;

    /**
     * 경기 생성시 최초 경기 참가정보 생성
     * - 홈팀 및 경기생성자의 팀/선수정보 생성
     */
    public Long createGameJoin( GameJoinCommand command ) {
        Long gameSeq = command.getGameSeq();
        Long teamSeq = command.getTeamSeq();
        Long userSeq = command.getUserSeq();

        /** 경기참가팀 생성 - HOME팀 */
        Team team = teamRepository.findByTeamSeq( teamSeq );
        if ( null == team ) {
            throw new CustomException( DomainErrorType.NOT_FOUND_TEAM_INFO );
        }
        GameJoinTeam homeTeam = team.joinGameAsHome( gameSeq );
        homeTeam.joinInSelfGame();
        gameJoinTeamRepository.saveGameJoinTeam( homeTeam );

        TeamMember tmParam = TeamMember.builder()
                                .teamSeq( teamSeq )
                                .userSeq( userSeq ).build();
        /** 경기참가선수 생성 - 경기생성자 */
        GameJoinPlayer creatorPlayer = GameJoinPlayer.ofCreator(
                                            gameSeq,
                                            homeTeam.getGameJoinTeamSeq(),
                                            userRepo.findUser( userSeq ),
                                            teamMemberRepo.findTeamMember( tmParam )
                                        );
        gameJoinPlayerRepository.save( creatorPlayer );
        return creatorPlayer.getGameJoinPlayerSeq();
    }

    /** 경기참가팀 확정  */
    public void confirmJoinTeam( GameJoinTeamConfirmationCommand command ) {
        final Long gameSeq          = command.getGameSeq();
        final String gameTypeCode   = command.getGameTypeCode();
        final Long opponentTeamSeq  = command.getOpponentTeamSeq();

        /** 어웨이팀 등록 여부 확인 - HOME팀은 Game create단계에서 이미 생성됨 */
        GameJoinTeam existAwayTeam = getGameJoinTeamByHomeAway( gameSeq, HomeAwayCode.AWAY_TEAM );
        boolean isAlreadyExistAwayTeam = null != existAwayTeam;
        if ( isAlreadyExistAwayTeam ) {
            throw new CustomException( DomainErrorType.ALREADY_EXIST_JOIN_TEAM );
        }

        /** 게임기록상태코드 변경 - 게임생성(01) >> 게임참가팀확정(02) */
        Game confirmJoinTeam = Game.builder()
                .gameSeq( gameSeq )
                .gameRecordStateCode( GameRecordStateCode.JOIN_TEAM_CONFIRMATION.getCode() )
                .build();
        gameRepository.updateGameRecordState( confirmJoinTeam );

        /** 게임 유형에 따라 HOME_TEAM 이름 update하기 */
        GameJoinTeam homeTeam = getGameJoinTeamByHomeAway( gameSeq, HomeAwayCode.HOME_TEAM );
        GameJoinTeam awayTeam = null;
        GameTypeCode gameType = GameTypeCode.typeOf( gameTypeCode );
        switch ( gameType ) {
            case SELF_GAME :
                awayTeam = homeTeam.toAwayInSelfGameType();
                break;
            case MATCH_UP_GAME :
                // HOME팀명 수정
                homeTeam.joinInMatchGame();
                gameJoinTeamRepository.updateTeamName( homeTeam );
                // AWAY팀 세팅
                Team opponentTeam = teamRepository.findByTeamSeq( opponentTeamSeq );
                if ( null == opponentTeam ) {
                    throw new CustomException( DomainErrorType.NOT_FOUND_TEAM_INFO );
                }
                awayTeam = opponentTeam.joinGameAsAway( gameSeq );
                break;
            default :
                throw new CustomException( DomainErrorType.INVALID_GAME_TYPE );
        }
        
        /** AWAY팀 정보 저장 */
        gameJoinTeamRepository.saveGameJoinTeam( awayTeam );
    }

    public GameJoinTeam getGameJoinTeamByHomeAway( Long gameSeq, HomeAwayCode homeAwayCode ) {
        GameJoinTeam joinTeamParam = GameJoinTeam.builder()
                                        .gameSeq(       gameSeq )
                                        .homeAwayCode(  homeAwayCode.getCode() )
                                        .build();
        return gameJoinTeamRepository.findGameJoinTeam( joinTeamParam );
    }

    public OppenentTeamQuery.Result searchOpponents( OppenentTeamQuery query ) {
        
        Pagination pagination = Pagination.of( query.getPageNo() );
        
        SearchOppenentsDTO searchParams = new SearchOppenentsDTO()
                .sidoCode(      query.getSidoCode() )
                .teamName(      query.getTeamName() )
                .leaderName(    query.getLeaderName() )
                .pagination(    pagination );

        return query.setResult(
            pagination.getPages( gameJoinManagerRepo.findTotalCountOpponents( searchParams ) ),
            gameJoinManagerRepo.findPaginationOpponents( searchParams )
        );
    }


    /**
     * 경기참가팀 조회
     **/
    public GameJoinTeamQuery.Result getGameJoinTeams( GameJoinTeamQuery query ) {

        List<GameJoinTeamInfoDTO> joinTeams = gameJoinManagerRepo.findGameJoinTeams( new SearchGameJoinTeamDTO()
                                                                                        .gameSeq( query.getGameSeq() )
                                                                                        .homeAwayCode( query.getHomeAwayCode() ) );

        GameJoinTeamInfoDTO homeTeam = joinTeams.stream()
                .filter( team -> HomeAwayCode.HOME_TEAM.getCode().equals( team.getHomeAwayCode() ) )
                .findFirst().orElseGet(() -> null);

        GameJoinTeamInfoDTO awayTeam = joinTeams.stream()
                .filter( team -> HomeAwayCode.AWAY_TEAM.getCode().equals( team.getHomeAwayCode() ) )
                .findFirst().orElseGet(() -> null);

        return query.buildResult( homeTeam, awayTeam );
    }


    /**
     * 경기참가선수 추가
     **/
    public void addGameJoinPlayer( GameJoinPlayerCommand command ) {

        Long gameSeq                              = command.getGameSeq();
        String homeAwayCode                       = command.getHomeAwayCode();

        /** 게임참가팀이 존재하는지 확인 */
        GameJoinTeam gameJoinTeam = Optional
                .ofNullable( gameJoinTeamRepository.findGameJoinTeam( GameJoinTeam.builder()
                                                        .gameSeq( gameSeq )
                                                        .homeAwayCode( homeAwayCode )
                                                        .build() ) )
                .orElseThrow( () -> new CustomException( DomainErrorType.NOT_FOUND_GAME_JOIN_TEAM ) );
        /** 게임기록상태 확인 */
        Game game = gameRepository.findGame( gameSeq );
        if ( !game.canUpdateRecord() ) {
            throw new CustomException( DomainErrorType.CANT_ADD_GAME_JOIN_PLAYER );
        }
        /** 해당 게임의 쿼터선수기록 존재여부 확인 - 쿼터기록이 존재할 경우 수정 불가 */
        List<QuarterPlayerRecords> playersRecord = quarterPlayerRecordsRepo.findAllInGame(gameSeq);
        boolean hasPlayerRecord = !playersRecord.isEmpty();
        if ( hasPlayerRecord ) {
            throw new CustomException( DomainErrorType.ALREADY_EXIST_QUARTER_RECORDS );
        }

        String backNumber           = command.getBackNumber();
        String playerTypeCode       = command.getPlayerTypeCode();
        boolean isUnauthGuest       = PlayerTypeCode.UNAUTH_GUEST.getCode().equals( playerTypeCode );
        GameJoinPlayer addPlayer    = null;
        if ( isUnauthGuest ) {
            addPlayer = GameJoinPlayer.ofUnauthPlayer( gameJoinTeam, command.getUserName(), backNumber, command.getPositionCode(), command.getEmail() );
        } else {
            /** 회원인 선수는 User테이블에서 데이터를 조회하여 insert */
            User user = userRepo.findUser( command.getUserSeq() );
            addPlayer = GameJoinPlayer.ofAuthPlayer( gameJoinTeam, user, playerTypeCode, backNumber );
        }

        List<GameJoinPlayer> registeredPlayers = gameJoinPlayerRepository.findAllPlayersOnOneSideTeam( GameJoinPlayer.builder()
                .gameSeq( gameSeq )
                .homeAwayCode( homeAwayCode )
                .build() );
        if ( addPlayer.isAppendable( registeredPlayers ) ) {
            gameJoinPlayerRepository.save( addPlayer );
        }
    }


    /**
     * 경기참가선수 삭제
     **/
    public void deleteGameJoinPlayer( GameJoinPlayerCommand command ) {

        Long gameSeq            = command.getGameSeq();
        Long gameJoinPlayerSeq  = command.getGameJoinPlayerSeq();

        /** 게임기록상태 확인 */
        Game game = gameRepository.findGame( gameSeq );
        if ( !game.canUpdateRecord() ) {
            throw new CustomException( DomainErrorType.CANT_ADD_GAME_JOIN_PLAYER );
        }

        /** 해당 게임의 쿼터선수기록 존재여부 확인 - 쿼터기록이 존재할 경우 수정 불가 */
        List<QuarterPlayerRecords> playersRecord = quarterPlayerRecordsRepo.findAllInGame(gameSeq);
        boolean hasPlayerRecord = !playersRecord.isEmpty();
        if (hasPlayerRecord) {
            throw new CustomException( DomainErrorType.ALREADY_EXIST_QUARTER_RECORDS );
        }

        GameJoinPlayer deletePlayer = gameJoinPlayerRepository.findPlayer( GameJoinPlayer.builder()
                                                                            .gameJoinPlayerSeq( gameJoinPlayerSeq )
                                                                            .build() );
        if ( !deletePlayer.isRemovable() ) {
            throw new CustomException( DomainErrorType.CANT_REMOVE_PLAYER_FOR_RECORDER );
        }
        /** 게임참가선수 데이터 존재여부 확인 - 기존 데이터 존재시 삭제 */
        gameJoinPlayerRepository.deletePlayer( deletePlayer );
    }

    /**
     * 경기에 참가한 선수들을 홈/어웨이 팀에 따라 조회
     */
    public GameJoinPlayerQuery.Result getGameJoinPlayers( GameJoinPlayerQuery query ) {
        final Long gameSeq          = query.getGameSeq();
        final String homeAwayCode   = query.getHomeAwayCode();
        GameJoinTeam gameJoinTeam = gameJoinTeamRepository.findGameJoinTeam(
                GameJoinTeam.builder()
                        .gameSeq(       gameSeq )
                        .homeAwayCode(  homeAwayCode )
                        .build()
        );
        if ( null == gameJoinTeam ) {
            throw new CustomException( DomainErrorType.NOT_FOUND_GAME_JOIN_TEAM );
        }
        SearchPlayersDTO searchCond = new SearchPlayersDTO()
                .gameSeq(       gameSeq )
                .homeAwayCode(  homeAwayCode );
        return query.buildResult(
                gameJoinTeam.getTeamSeq(),
                gameJoinManagerRepo.findGameJoinPlayers( searchCond ),
                null
        );
    }


    /**
     * 경기에 참가한 선수들을 홈/어웨이 팀에 따라 조회 ( 페이징 처리 반영 )
     */
    public GameJoinPlayerQuery.Result getGameJoinPlayersWithPaging( GameJoinPlayerQuery query ) {
        final Long gameSeq          = query.getGameSeq();
        final String homeAwayCode   = query.getHomeAwayCode();
        final Integer pageNo        = query.getPageNo();

        GameJoinTeam gameJoinTeam = gameJoinTeamRepository.findGameJoinTeam(
                GameJoinTeam.builder()
                        .gameSeq(       gameSeq )
                        .homeAwayCode(  homeAwayCode )
                        .build()
        );
        if ( null == gameJoinTeam ) {
            throw new CustomException( DomainErrorType.NOT_FOUND_GAME_JOIN_TEAM );
        }
        Pagination pagination   = Pagination.of( pageNo, 5 );
        SearchPlayersDTO searchCond = new SearchPlayersDTO()
                .gameSeq(       gameSeq )
                .homeAwayCode(  homeAwayCode )
                .pagination(    pagination );
        return query.buildResult(
                gameJoinTeam.getTeamSeq(),
                gameJoinManagerRepo.findPaginationGameJoinPlayers( searchCond ),
                pagination.getPages( gameJoinManagerRepo.findTotalCountGameJoinPlayers( searchCond ) )
        );
    }


    /**
     * 쿼터 엔트리 목록 저장
     * @param command
     */
    public void saveQuarterEntryInfo( GameEntryCommand command ) {
        /** TODO 게임생성자 권한 체크 - 게임기록권한T 조회해서 권한체크 */

        /**--------------------------------
         * 쿼터 엔트리 길이 체크 - 5명이 되는지 TODO entry로 속성명 수정 필요
         *---------------------------------*/
        List<PlayerInfoDTO> entryInput = command.getEntry();
        boolean hasNotValidEntry = entryInput.size() != 5;
        if (hasNotValidEntry) {
            throw new CustomException( DomainErrorType.INSUFFICIENT_PLAYERS_ON_ENTRY );
        }

        /**--------------------------------
         * 게임참가선수 테이블에 존재하는 선수인지 확인 - 게임참가선수Seq로만 조회
         *  - 홈 혹은 어웨이팀 게임참가선수목록 조회
         *  - gameJoinPlayerSeq를 Set으로 변경
         *  - 해당 Set으로 존재여부 확인
         *---------------------------------*/
        Long gameSeq         = command.getGameSeq();
        String homeAwayCode  = command.getHomeAwayCode();
        String quarterCode   = command.getQuarterCode();

        QuarterPlayerRecords quarterRecordParam = QuarterPlayerRecords.builder()
                                                    .gameSeq( gameSeq )
                                                    .homeAwayCode( homeAwayCode )
                                                    .quarterCode( quarterCode )
                                                    .build();
        List<QuarterPlayerRecords> savedQuarterPlayerRecords = quarterPlayerRecordsRepo.findOneTeamQuarterRecord( quarterRecordParam );

        /** 해당 팀의 쿼터선수기록 InGameYn "N"으로 초기화 */
        QuarterPlayerRecords paramForInitEntry = QuarterPlayerRecords.builder()
                .gameSeq(       gameSeq )
                .homeAwayCode(  homeAwayCode )
                .quarterCode(   quarterCode )
                .inGameYn(      "N" )
                .build();
        quarterPlayerRecordsRepo.updateInGameYnForAllQuarterPlayer( paramForInitEntry );

        Set<Long> savedRecordPlayerSeqSet = savedQuarterPlayerRecords.stream()
                                                            .map( QuarterPlayerRecords::getGameJoinPlayerSeq )
                                                            .collect( Collectors.toSet() );
        /** 입력받은 Entry 선수의 쿼터기록 저장여부에 따라 처리 */
        for ( PlayerInfoDTO player : entryInput ) {
            boolean hasQuarterRecords = savedRecordPlayerSeqSet.contains( player.getGameJoinPlayerSeq() );
            if ( hasQuarterRecords ) {
                /**-------------------------------------
                 * 쿼터기록이 저장되어 있는 경우 - InGameYn 상태 변경 ( "Y" 으로 )
                 *-------------------------------------*/
                QuarterPlayerRecords paramForEntry = QuarterPlayerRecords.builder()
                        .gameJoinPlayerSeq( player.getGameJoinPlayerSeq() )
                        .quarterCode(quarterCode)
                        .inGameYn("Y")
                        .build();

                quarterPlayerRecordsRepo.updateInGameYn( paramForEntry );
                continue;
            }
            /**-------------------------------------
             * 쿼터기록이 없는 경우 - Insert
             *-------------------------------------*/
            QuarterPlayerRecords paramForCreation = QuarterPlayerRecords.builder()
                    .gameSeq(               gameSeq )
                    .homeAwayCode(          homeAwayCode )
                    .gameJoinTeamSeq(       player.getGameJoinTeamSeq() )
                    .gameJoinPlayerSeq(     player.getGameJoinPlayerSeq() )
                    .quarterCode(           quarterCode )
                    .inGameYn(              "Y" )
                    .build();
            quarterPlayerRecordsRepo.save( paramForCreation );
        }
    }

    /**
     * 경기 엔트리 조회하기
     * - cf. 엔트리 : 경기시 in-court에서 뛰고 있는 선수들
     * @param query GameEntryQuery
     * @return GameEntryQuery.Result
     */
    public GameEntryQuery.Result getGameEntry( GameEntryQuery query ) {
        /** 한 게임의 모든 게임참가팀 조회 */
        List<GameJoinTeam> gameJoinTeams = gameJoinTeamRepository.findAllGameJoinTeam( query.getGameSeq() );
        if ( gameJoinTeams.isEmpty() ) {
            throw new CustomException( DomainErrorType.NOT_FOUND_GAME_JOIN_TEAM );
        }

        String quarterCode  = query.getQuarterCode();
        String homeAwayCode = query.getHomeAwayCode();

        /** 홈어웨이코드 존재 여부확인 - 코드값이 존재하면 팀의 엔트리만 조회 */
        boolean isAllTeamEntryInq = !StringUtils.hasText( homeAwayCode );
        if ( isAllTeamEntryInq ) {
            /** 홈/어웨이팀 전체 엔트리 조회 */
            return query.buildResult(
                getQuarterTeamEntryInfo( quarterCode, HomeAwayCode.HOME_TEAM, gameJoinTeams ),
                getQuarterTeamEntryInfo( quarterCode, HomeAwayCode.AWAY_TEAM, gameJoinTeams )
            );
        }
        /** 홈/어웨이 팀에 맞게 엔트리 조회 */
        HomeAwayCode homeAwayType = HomeAwayCode.typeOf( homeAwayCode );
        return query.buildResult( homeAwayType, getQuarterTeamEntryInfo( quarterCode, homeAwayType, gameJoinTeams ) );
    }


    private QuarterTeamEntryDTO getQuarterTeamEntryInfo(
            String quarterCode,
            HomeAwayCode homeAwayCode,
            List<GameJoinTeam> gameJoinTeams
    ) {
        Optional<GameJoinTeam> gameJoinTeamNullable = gameJoinTeams.stream()
                                                        .filter( t -> homeAwayCode.getCode().equals( t.getHomeAwayCode() ))
                                                        .findAny();
        if ( gameJoinTeamNullable.isEmpty() ) {
            throw new CustomException( DomainErrorType.NOT_FOUND_GAME_JOIN_TEAM );
        }
        GameJoinTeam gameJoinTeam = gameJoinTeamNullable.get();

        SearchEntryDTO searchCond = new SearchEntryDTO()
                .gameJoinTeamSeq( gameJoinTeam.getGameJoinTeamSeq() )
                .quarterCode( quarterCode );
        List<QuarterPlayerRecordDTO> entry = gameJoinManagerRepo.findOneTeamEntry( searchCond );

        return new QuarterTeamEntryDTO()
                .teamName( gameJoinTeam.getTeamName() )
                .homeAwayCode( homeAwayCode.getCode() )
                .entry( entry );
    }

}
