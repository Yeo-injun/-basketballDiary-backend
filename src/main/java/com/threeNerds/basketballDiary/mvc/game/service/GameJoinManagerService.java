package com.threeNerds.basketballDiary.mvc.game.service;

import com.threeNerds.basketballDiary.constant.code.type.GameRecordStateCode;
import com.threeNerds.basketballDiary.constant.code.type.GameTypeCode;
import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import com.threeNerds.basketballDiary.constant.code.type.PlayerTypeCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.exception.error.SystemErrorType;
import com.threeNerds.basketballDiary.mvc.game.controller.response.GetAllGameJoinPlayersResponse;
import com.threeNerds.basketballDiary.mvc.game.dto.QuarterTeamEntryDTO;
import com.threeNerds.basketballDiary.mvc.game.domain.Game;
import com.threeNerds.basketballDiary.mvc.game.service.dto.*;
import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.TeamMemberRepository;
import com.threeNerds.basketballDiary.mvc.team.domain.Team;
import com.threeNerds.basketballDiary.mvc.user.domain.User;
import com.threeNerds.basketballDiary.mvc.game.domain.GameJoinPlayer;
import com.threeNerds.basketballDiary.mvc.game.domain.GameJoinTeam;
import com.threeNerds.basketballDiary.mvc.game.domain.QuarterPlayerRecords;
import com.threeNerds.basketballDiary.mvc.game.dto.*;
import com.threeNerds.basketballDiary.mvc.game.repository.GameJoinPlayerRepository;
import com.threeNerds.basketballDiary.mvc.game.repository.GameJoinTeamRepository;
import com.threeNerds.basketballDiary.mvc.game.repository.GameRepository;
import com.threeNerds.basketballDiary.mvc.game.repository.QuarterPlayerRecordsRepository;
import com.threeNerds.basketballDiary.mvc.game.repository.dto.GameJoinManagerRepository;
import com.threeNerds.basketballDiary.mvc.team.repository.TeamRepository;
import com.threeNerds.basketballDiary.mvc.user.repository.UserRepository;


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

    private final GameJoinManagerRepository gameJoinManagerRepo;

    /**
     * 경기 생성시 최초 경기 참가정보 생성
     * - 홈팀 및 경기생성자의 팀/선수정보 생성
     */
    public Long createGameJoin( GameJoinCommand command ) {
        Long gameSeq = command.getGameSeq();
        Long teamSeq = command.getTeamSeq();
        Long userSeq = command.getUserSeq();

        /** 경기참가팀 생성 - HOME팀 */
        Team team = Optional
                        .ofNullable( teamRepository.findByTeamSeq( teamSeq ) )
                        .orElseThrow( ()-> new CustomException( DomainErrorType.TEAM_NOT_FOUND ) );
        GameJoinTeam homeTeam = team.joinGameAsHome( gameSeq );
        homeTeam.inSelfGame();
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

    /** 게임참가팀 확정  */
    // TODO 해당 교류전인데 자체전으로 생성되는 오류 잡기
    public void confirmJoinTeam(GameJoinTeamCreationDTO joinTeamCreationDTO) {
        final Long gameSeq          = joinTeamCreationDTO.getGameSeq();
        final String gameTypeCode   = joinTeamCreationDTO.getGameTypeCode();
        final Long opponentTeamSeq  = joinTeamCreationDTO.getOpponentTeamSeq();

        /** 어웨이팀 등록 여부 확인 - HOME팀은 Game create단계에서 이미 생성됨 */
        boolean hasAlreadyConfirmAwayTeam = hasGameJoinTeam(gameSeq, HomeAwayCode.AWAY_TEAM);
        if ( hasAlreadyConfirmAwayTeam ) {
            throw new CustomException(DomainErrorType.ALREADY_EXIST_JOIN_TEAM);
        }

        /** 게임기록상태코드 변경 - 게임생성(01) >> 게임참가팀확정(02) */
        Game joinTeamConfirm = Game.builder()
                .gameSeq( gameSeq )
                .gameRecordStateCode( GameRecordStateCode.JOIN_TEAM_CONFIRMATION.getCode() )
                .build();
        gameRepository.updateGameRecordState(joinTeamConfirm);

        /** 게임 유형에 따라 HOME_TEAM 이름 update하기 */
        // TODO 구현 예정 
        
        /** AWAY팀 정보 생성 - 게임 유형에 따라서 */
        gameJoinTeamRepository.saveGameJoinTeam( generateAwayTeamByGameType( gameSeq, gameTypeCode, opponentTeamSeq ) );
    }

    private boolean hasGameJoinTeam(Long gameSeq, HomeAwayCode homeAwayCode) {
        GameJoinTeam paramGameJoinTeam = new GameJoinTeam().builder()
                .gameSeq(gameSeq)
                .homeAwayCode(homeAwayCode.getCode())
                .build();

        GameJoinTeam joinTeam = gameJoinTeamRepository.findGameJoinTeam(paramGameJoinTeam);
        if (joinTeam == null) {
            return false;
        }
        return true;
    }

    private GameJoinTeam generateAwayTeamByGameType( Long gameSeq, String gameTypeCode, Long opponentTeamSeq ) {
        if (GameTypeCode.SELF_GAME.getCode().equals(gameTypeCode)) {
            Team gameCreatorTeam = gameJoinManagerRepo.findGameCreatorTeam( gameSeq );
            GameJoinTeam awayTeam = gameCreatorTeam.joinGameAsAway( gameSeq );
            awayTeam.inSelfGame();
            return awayTeam;
        }

        if (GameTypeCode.MATCH_UP_GAME.getCode().equals(gameTypeCode)) {
            Team opponentTeam = Optional
                    .ofNullable(teamRepository.findByTeamSeq(opponentTeamSeq))
                    .orElseThrow(()-> new CustomException(DomainErrorType.TEAM_NOT_FOUND));
            return opponentTeam.joinGameAsAway( gameSeq );
        }

        return new GameJoinTeam(); // TODO SQL INSERT 오류나지 않도록 임시처리 ( null을 반환하거나 throw Error를 던지거나... )
    }

    public List<GameOpponentDTO> searchOpponents(SearchOppenentsDTO searchCond)
    {
        //TODO 상대팀 목록 조회 - 페이징 처리 / 검색조건 >> Like조건
        return gameJoinManagerRepo.findOpponents(searchCond);
    }

    public Map<HomeAwayCode, GameJoinTeamInfoDTO> getGameJoinTeams(SearchGameJoinTeamDTO searchParam) {
        List<GameJoinTeamInfoDTO> joinTeams = gameJoinManagerRepo.findGameJoinTeams(searchParam);

        Map<HomeAwayCode, GameJoinTeamInfoDTO> joinTeamsMap = new HashMap<>();

        // TODO home / away 중 한팀만 존재할 수 있음. 따라서 Null일 경우를 허용해야함!! Optional 처리를 어떻게 할 것인지 고민 필요
        GameJoinTeamInfoDTO homeTeam = joinTeams.stream()
                .filter( team -> { return HomeAwayCode.HOME_TEAM.getCode().equals(team.getHomeAwayCode()); })
                .findFirst().orElseGet(() -> null);

        GameJoinTeamInfoDTO awayTeam = joinTeams.stream()
                .filter( team -> { return HomeAwayCode.AWAY_TEAM.getCode().equals(team.getHomeAwayCode()); })
                .findFirst().orElseGet(() -> null);


        joinTeamsMap.put(HomeAwayCode.HOME_TEAM, homeTeam);
        joinTeamsMap.put(HomeAwayCode.AWAY_TEAM, awayTeam);
        return joinTeamsMap;
    }

    /**
     * 게임참가선수 등록
     **/
    public void registerGameJoinPlayers( GameJoinPlayerCommand command ) {

        Long gameSeq                              = command.getGameSeq();
        String homeAwayCode                       = command.getHomeAwayCode();
        List<GameJoinPlayerDTO> gameJoinPlayers   = command.getGameJoinPlayers();

        /** 게임참가팀이 존재하는지 확인 */
        GameJoinTeam joinTeamParam = GameJoinTeam.builder()
                                        .gameSeq( gameSeq )
                                        .homeAwayCode( homeAwayCode )
                                        .build();
        GameJoinTeam gameJoinTeam = Optional
                                        .ofNullable( gameJoinTeamRepository.findGameJoinTeam( joinTeamParam ) )
                                        .orElseThrow( () -> new CustomException( DomainErrorType.NOT_FOUND_GAME_JOIN_TEAM ) );
        /** 게임기록상태 확인 */
        Game game = gameRepository.findGame( gameSeq );
        if ( !game.canUpdateRecord() ) {
            throw new CustomException( DomainErrorType.CANT_ADD_GAME_JOIN_PLAYER );
        }

        /** 해당 게임의 쿼터선수기록 존재여부 확인 - 쿼터기록이 존재할 경우 수정 불가 */
        List<QuarterPlayerRecords> playersRecord = quarterPlayerRecordsRepo.findAllInGame(gameSeq);
        boolean hasPlayerRecord = !playersRecord.isEmpty();
        if (hasPlayerRecord) {
            throw new CustomException( DomainErrorType.INVALID_REGISTER_PLAYERS_FOR_ALREADY_HAS_RECORDS );
        }

        /** 게임참가선수 데이터 존재여부 확인 - 기존 데이터 존재시 삭제 TODO 경기생성자는 삭제에서 제외시켜야 함 / 상대팀에 등록되어 있는지 확인 */
        GameJoinPlayer joinPlayerParam = GameJoinPlayer.builder()
                                            .gameSeq( gameSeq )
                                            .homeAwayCode( homeAwayCode )
                                            .build();
        List<GameJoinPlayer> registeredJoinPlayers = gameJoinPlayerRepository.findAllPlayersOnOneSideTeam( joinPlayerParam );
        boolean hasJoinPlayers = !registeredJoinPlayers.isEmpty();
        if (hasJoinPlayers) {
            gameJoinPlayerRepository.deletePlayers( joinPlayerParam );
        }

        /** 중복된 등번호가 있는지 체크하기 */
        Set<String> backNumberSet = new HashSet<>();
        for (GameJoinPlayerDTO player : gameJoinPlayers) {
            String backNumber = player.getBackNumber();
            boolean isDuplicatedBackNumber = !backNumberSet.add(backNumber);
            if (isDuplicatedBackNumber) {
                throw new CustomException(DomainErrorType.DUPLICATE_BACK_NUMBER);
            }
        }
        // TODO 중복된 회원이 있는지 체크하기 - userSeq의 중복이 있는지 stream으로 확인

        /** 게임참가선수 데이터 저장 - 선수유형에 따라서 처리하기 */
        for (GameJoinPlayerDTO joinPlayerDTO : gameJoinPlayers) {
            String playerTypeCode = joinPlayerDTO.getPlayerTypeCode();
            boolean isUnauthGuest = PlayerTypeCode.UNAUTH_GUEST.getCode().equals(playerTypeCode);
            /** 회원이 아닌 선수는 입력값을 직접 DB에 insert */
            if (isUnauthGuest) {
                GameJoinPlayer unauthGuest = GameJoinPlayer.createUnauthPlayer( gameJoinTeam, joinPlayerDTO );
                gameJoinPlayerRepository.save(unauthGuest);
                continue;
            }

            /** 회원인 선수는 User테이블에서 데이터를 조회하여 insert */
            String backNumber = joinPlayerDTO.getBackNumber();
            User user = userRepo.findUser(joinPlayerDTO.getUserSeq());
            GameJoinPlayer authJoinPlayer = GameJoinPlayer.createAuthPlayer( gameJoinTeam, playerTypeCode, backNumber, user);
            gameJoinPlayerRepository.save(authJoinPlayer);
        }
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
            throw new CustomException( DomainErrorType.INVALID_REGISTER_PLAYERS_FOR_ALREADY_HAS_RECORDS );
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
        String homeAwayCode     = command.getHomeAwayCode();
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
            throw new CustomException( DomainErrorType.INVALID_REGISTER_PLAYERS_FOR_ALREADY_HAS_RECORDS );
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


    public GameJoinPlayerResult getGameJoinPlayers( GameJoinPlayerQuery query ) {
        /** 게임에 참가한 팀 및 팀원을 모두 조회해서 필터링 */
        final Long gameSeq          = query.getGameSeq();
        final String homeAwayCode   = query.getHomeAwayCode();
        final Integer pageNo        = query.getPageNo();

        GameJoinTeam gameJoinTeam = gameJoinTeamRepository.findGameJoinTeam(
            GameJoinTeam.builder()
                .gameSeq(       gameSeq )
                .homeAwayCode(  homeAwayCode )
                .build()
        );
        Long teamSeq = gameJoinTeam.getTeamSeq();

        Pagination pagination   = Pagination.of( pageNo, 5 );
        boolean isNoPagination  = 0 == pageNo;
        if ( isNoPagination ) {
            SearchPlayersDTO searchCond = new SearchPlayersDTO()
                    .gameSeq(       gameSeq )
                    .homeAwayCode(  homeAwayCode );
            return GameJoinPlayerResult.builder()
                    .gameSeq(       gameSeq )
                    .teamSeq(       teamSeq )
                    .pagination(    pagination.getPages( gameJoinManagerRepo.findTotalCountGameJoinPlayers( searchCond ) ) )
                    .players(       gameJoinManagerRepo.findGameJoinPlayers( searchCond ) )
                    .build();
        }
        SearchPlayersDTO searchCond = new SearchPlayersDTO()
                .gameSeq(       gameSeq )
                .homeAwayCode(  homeAwayCode )
                .pagination(    pagination );
        return GameJoinPlayerResult.builder()
                .gameSeq(       gameSeq )
                .teamSeq(       teamSeq )
                .pagination(    pagination.getPages( gameJoinManagerRepo.findTotalCountGameJoinPlayers( searchCond ) ) )
                .players(       gameJoinManagerRepo.findPaginationGameJoinPlayers( searchCond ) )
                .build();
    }

    /**
     * 쿼터 엔트리 목록 저장
     * @param quarterEntryInfoDTO
     */
    public void saveQuarterEntryInfo(QuarterEntryInfoDTO quarterEntryInfoDTO) {
        /** TODO 게임생성자 권한 체크 - 게임기록권한T 조회해서 권한체크 */

        /**--------------------------------
         * 쿼터 엔트리 길이 체크 - 5명이 되는지 TODO entry로 속성명 수정 필요
         *---------------------------------*/
        List<PlayerInfoDTO> entryInput = quarterEntryInfoDTO.getPlayerList();
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
        Long gameSeq         = quarterEntryInfoDTO.getGameSeq();
        String homeAwayCode  = quarterEntryInfoDTO.getHomeAwayCode();
        String quarterCode   = quarterEntryInfoDTO.getQuarterCode();

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
            quarterPlayerRecordsRepo.save(paramForCreation);
        }
    }

    /**
     * 게임엔트리 조회하기
     * @param query
     * @return GetGameEntryResponse
     */
    public Map< HomeAwayCode, QuarterTeamEntryDTO > getGameEntry( GameEntryQuery query ) {
        /** 한 게임의 모든 게임참가팀 조회 */
        List<GameJoinTeam> gameJoinTeams = gameJoinTeamRepository.findAllGameJoinTeam( query.getGameSeq() );

        String quarterCode  = query.getQuarterCode();
        String homeAwayCode = query.getHomeAwayCode();

        /** 홈어웨이코드 존재 여부확인 - 코드값이 존재하면 팀의 엔트리만 조회 */
        Map< HomeAwayCode, QuarterTeamEntryDTO > result = new EnumMap<>( HomeAwayCode.class );

        boolean isAllTeamEntryInq = !StringUtils.hasText( homeAwayCode );
        if ( isAllTeamEntryInq ) {
            /** 홈/어웨이팀 전체 엔트리 조회 */
            result.put( HomeAwayCode.HOME_TEAM, getQuarterTeamEntryInfo( quarterCode, HomeAwayCode.HOME_TEAM.getCode(), gameJoinTeams ) );
            result.put( HomeAwayCode.AWAY_TEAM, getQuarterTeamEntryInfo( quarterCode, HomeAwayCode.AWAY_TEAM.getCode(), gameJoinTeams ) );
            return result;
        }

        switch ( HomeAwayCode.typeOf( homeAwayCode ) ) {
            case HOME_TEAM:
                result.put( HomeAwayCode.HOME_TEAM, getQuarterTeamEntryInfo( quarterCode, homeAwayCode, gameJoinTeams ) );
                return result;
            case AWAY_TEAM:
                result.put( HomeAwayCode.AWAY_TEAM, getQuarterTeamEntryInfo( quarterCode, homeAwayCode, gameJoinTeams ) );
                return result;
            default:
                throw new CustomException( SystemErrorType.INVALID_CODE_DOMAIN_FOR_HOME_AWAY_CODE );
        }
    }

    private QuarterTeamEntryDTO getQuarterTeamEntryInfo(
            String quarterCode,
            String homeAwayCode,
            List<GameJoinTeam> gameJoinTeams
    ) {
        GameJoinTeam gameJoinTeam = gameJoinTeams.stream()
                .filter( t -> homeAwayCode.equals( t.getHomeAwayCode() ))
                .findAny()
                .get();

        SearchEntryDTO searchCond = new SearchEntryDTO()
                .gameJoinTeamSeq( gameJoinTeam.getGameJoinTeamSeq() )
                .quarterCode( quarterCode );
        List<QuarterPlayerRecordDTO> entry = gameJoinManagerRepo.findOneTeamEntry( searchCond );

        return new QuarterTeamEntryDTO()
                    .teamName( gameJoinTeam.getTeamName() )
                    .homeAwayCode( homeAwayCode )
                    .entry( entry );
    }
}
