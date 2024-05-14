package com.threeNerds.basketballDiary.mvc.game.service;

import com.threeNerds.basketballDiary.constant.code.type.GameRecordStateCode;
import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import com.threeNerds.basketballDiary.constant.code.type.QuarterCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.mvc.game.domain.*;

import com.threeNerds.basketballDiary.mvc.game.dto.*;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameAllQuartersRecords.GetGameAllQuartersRecordsResponse;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameAllQuartersRecords.QuarterAllTeamsRecordsDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameAllQuartersRecords.QuarterTeamRecordsDTO;


import com.threeNerds.basketballDiary.mvc.game.dto.PlayerQuarterRecordDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameQuarterRecords.request.GetGameQuarterRecordsRequest;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameQuarterRecords.response.GetGameQuarterRecordsResponse;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameQuarterRecords.response.TeamQuarterRecordsDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.SavePlayerRecordDTO;
import com.threeNerds.basketballDiary.mvc.game.repository.*;
import com.threeNerds.basketballDiary.mvc.game.repository.dto.GameRecordManagerRepository;
import com.threeNerds.basketballDiary.mvc.game.service.dto.GameJoinPlayerRecordQuery;
import com.threeNerds.basketballDiary.mvc.game.service.dto.GameQuarterCommand;
import com.threeNerds.basketballDiary.mvc.game.service.dto.QuarterRecordCommand;
import com.threeNerds.basketballDiary.mvc.myTeam.controller.request.SearchMyTeamGamesRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.controller.response.SearchMyTeamGamesResponse;
import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.GameJoinTeamRecordDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.GameRecordDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.GameSearchCriteriaDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.TeamMemberRepository;
import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

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

    private final GameRecordManagerRepository gameRecordManagerRepository;

    private final TeamMemberRepository teamMemberRepo;

    private final String QUARTER_1ST_CODE = QuarterCode.FIRST.getCode();
    private final String QUARTER_2ND_CODE = QuarterCode.SECOND.getCode();
    private final String QUARTER_3RD_CODE = QuarterCode.THIRD.getCode();
    private final String QUARTER_4TH_CODE = QuarterCode.FOURTH.getCode();

    /** TODO 23.04.09 테스트 필요
     * 22.11.06
     * 특정쿼터의 선수별 기록조회(목록)
     * homeAwayCode에 따라 특정쿼터의 선수별 기록 목록을 조회한다.
     * @author 강창기
     * @update 여인준 23.04.08 : 파라미터 및 조회 데이터 변경
     */
    public Map< HomeAwayCode, List<PlayerQuarterRecordDTO> > getGameJoinPlayerQuarterRecords( GameJoinPlayerRecordQuery query ) {
        Long gameSeq        = query.getGameSeq();
        String homeAwayCode = query.getHomeAwayCode();
        String quarterCode  = query.getQuarterCode();

        /** gameSeq에 해당하는 게임내역이 존재하는지 확인. */
        boolean isExistGame = !ObjectUtils.isEmpty( gameRepository.findGame( gameSeq ) );
        if( !isExistGame ) {
            throw new CustomException(DomainErrorType.NOT_FOUND_GAME);
        }

        SearchGameDTO searchParams = new SearchGameDTO()
                .gameSeq( gameSeq )
                .homeAwayCode( homeAwayCode )
                .quarterCode( quarterCode );

        List<PlayerQuarterRecordDTO> players = gameRecordManagerRepository.findAllPlayerRecordsByQuarter( searchParams );

        Map< HomeAwayCode, List<PlayerQuarterRecordDTO> > result = new EnumMap<>( HomeAwayCode.class );
        result.put( HomeAwayCode.HOME_TEAM, filterPlayersByHomeAwayCode( players, HomeAwayCode.HOME_TEAM ) );
        result.put( HomeAwayCode.AWAY_TEAM, filterPlayersByHomeAwayCode( players, HomeAwayCode.AWAY_TEAM ) );
        return result;
    }

    private List<PlayerQuarterRecordDTO> filterPlayersByHomeAwayCode( List<PlayerQuarterRecordDTO> targetPlayers, HomeAwayCode filterCode )
    {
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
    public GetGameQuarterRecordsResponse getGameQuarterRecords(GetGameQuarterRecordsRequest reqBody)
    {
        Long gameSeq = reqBody.getGameSeq();
        Game gameInfo = Optional
                            .ofNullable( gameRepository.findGame( gameSeq ) )
                            .orElseThrow( () -> new CustomException(DomainErrorType.NOT_FOUND_GAME) );

        String quarterCode = reqBody.getQuarterCode();
        SearchGameDTO gameSearchCond = new SearchGameDTO()
                .gameSeq( gameSeq )
                .quarterCode( quarterCode );
        List<TeamQuarterRecordsDTO> allTeamsQuarterRecords = gameRecordManagerRepository.findAllTeamsQuarterRecords(gameSearchCond);

        // TODO 메세지 생성 로직 리팩토링 >> 테스트

        /** 쿼터기록이 입력되지 않은 경우 - 초기값 return */
        if ( allTeamsQuarterRecords.isEmpty() ) {
            List<GameJoinTeam> gameJoinTeams = gameJoinTeamRepo.findAllGameJoinTeam( gameSeq );
            return new GetGameQuarterRecordsResponse(
                    gameInfo,
                    quarterCode,
                    initGameJoinTeamByHomeAwayCode( gameJoinTeams, HomeAwayCode.HOME_TEAM ),
                    initGameJoinTeamByHomeAwayCode( gameJoinTeams, HomeAwayCode.AWAY_TEAM )
            );
        }

        return new GetGameQuarterRecordsResponse(
                gameInfo,
                quarterCode,
                filterByHomeAwayCode( allTeamsQuarterRecords, HomeAwayCode.HOME_TEAM ),
                filterByHomeAwayCode( allTeamsQuarterRecords, HomeAwayCode.AWAY_TEAM )
        );
    }

    private TeamQuarterRecordsDTO initGameJoinTeamByHomeAwayCode( List<GameJoinTeam> gameJoinTeams, HomeAwayCode homeAwayCode )
    {
        /** 홈/어웨이팀 구분에 따른 처리 */
        GameJoinTeam gameJoinTeam = gameJoinTeams.stream()
                                        .filter( gjt -> gjt.getHomeAwayCode().equals( homeAwayCode.getCode() ))
                                        .findFirst()
                                        .get();

        return new TeamQuarterRecordsDTO()
                .gameJoinTeamSeq( gameJoinTeam.getGameJoinTeamSeq() )
                .teamName( gameJoinTeam.getTeamName() )
                .homeAwayCode( homeAwayCode.getCode() );
    }

    private TeamQuarterRecordsDTO filterByHomeAwayCode( List<TeamQuarterRecordsDTO> gameJoinTeams, HomeAwayCode homeAwayCode )
    {
        /** 홈/어웨이팀 구분에 따른 처리 */
        return gameJoinTeams.stream()
                .filter( gjt -> gjt.getHomeAwayCode().equals( homeAwayCode.getCode() ))
                .findFirst()
                .get();
    }



    /**
     * 22.12.25
     * 전체쿼터의 홈·어웨이 팀기록조회
     * 홈 & 어웨이의 쿼터별 팀 합산기록을 조회한다.
     * @param searchGameDTO 게임조회용 DTO
     * @author 강창기
     */
    public GetGameAllQuartersRecordsResponse getGameAllQuartersRecords(SearchGameDTO searchGameDTO)
    {
        Game game = Optional
                        .ofNullable( gameRepository.findGame( searchGameDTO.getGameSeq() ) )
                        .orElseThrow( () -> new CustomException(DomainErrorType.NOT_FOUND_GAME) );

        List<QuarterTeamRecordsDTO> allQuarterRecords = gameRecordManagerRepository.findAllQuarterRecords(searchGameDTO);
        if (allQuarterRecords.isEmpty()) {
            return new GetGameAllQuartersRecordsResponse(game, new HashMap<>());
        }

        Map<QuarterCode, QuarterAllTeamsRecordsDTO> allQuarterRecordsMap = new HashMap<>();
        QuarterCode[] quarterCodes = QuarterCode.values();
        for (QuarterCode quarterCode : quarterCodes)
        {
            QuarterTeamRecordsDTO homeTeamRecords = filterTeamRecords(quarterCode, HomeAwayCode.HOME_TEAM, allQuarterRecords);
            QuarterTeamRecordsDTO awayTeamRecords = filterTeamRecords(quarterCode, HomeAwayCode.AWAY_TEAM, allQuarterRecords);

            if ( homeTeamRecords == null || awayTeamRecords == null )
            {
                allQuarterRecordsMap.put(quarterCode, null);
                continue;
            }

            QuarterAllTeamsRecordsDTO quarterAllTeamsRecordsDTO = new QuarterAllTeamsRecordsDTO()
                    .quarterCode(homeTeamRecords.getQuarterCode())
                    .quarterTime(homeTeamRecords.getQuarterTime())
                    .homeTeamRecords(homeTeamRecords)
                    .awayTeamRecords(awayTeamRecords);
            allQuarterRecordsMap.put(quarterCode, quarterAllTeamsRecordsDTO);
        }

        GetGameAllQuartersRecordsResponse resBody = new GetGameAllQuartersRecordsResponse(game, allQuarterRecordsMap);

        return resBody;
    }

    private QuarterTeamRecordsDTO filterTeamRecords (QuarterCode quarterCode, HomeAwayCode homeAwayCode, List<QuarterTeamRecordsDTO> allQuarterRecords) {
        return allQuarterRecords
                    .stream()
                    .filter( r -> quarterCode.getCode().equals(r.getQuarterCode())
                            && homeAwayCode.getCode().equals(r.getHomeAwayCode()))
                    .findAny()
                    .orElse(null);
    }

    /** 22.11.06
     * 소속팀의 게임기록조회
     * @author 여인준
     **/
    public SearchMyTeamGamesResponse searchMyTeamGames( SearchMyTeamGamesRequest message ) {

        /** 소속 팀원인지 확인하기 ( userSeq, teamSeq로 ) */
        TeamMember teamMemberParam = TeamMember.builder()
                                        .teamSeq( message.getTeamSeq() )
                                        .userSeq( message.getUserSeq() )
                                        .build();
        TeamMember teamMember = teamMemberRepo.findTeamMember( teamMemberParam );
        if ( null == teamMember ) {
            throw new CustomException( DomainErrorType.ONLY_TEAM_MEMBER_QUERY );
        }

        /** 경기목록 조회 */
        // TODO 검색기간 조건 쿼리 반영 필요
        Pagination pagination = Pagination.of( message.getPageNo(), 5 );
        List<GameRecordDTO> games = gameRecordManagerRepository.findPagingGamesByTeamSeq( new GameSearchCriteriaDTO()
                .pagination(    pagination )
                .setSearchSpan( message.getGameBgngYmd(), message.getGameEndYmd() )
                .teamSeq( 		message.getTeamSeq() )
                .gameTypeCode( 	message.getGameTypeCode() )
                .homeAwayCode( 	message.getHomeAwayCode() )
                .gamePlaceName( message.getGamePlaceName() )
        );

        if ( games.isEmpty() ) {
            return new SearchMyTeamGamesResponse( pagination.empty(), Collections.emptyList() );
        }

        /** 경기별 팀 기록 정보 조회 */
        for ( GameRecordDTO gr : games ) {
            gr.gameRecordStateCodeName(gr.getGameRecordStateCode());
            gr.gameTypeCodeName(gr.getGameTypeCode());
            if (GameRecordStateCode.CREATION.getCode().equals(gr.getGameRecordStateCode())) {
                continue;
            }
            Long gameSeq = gr.getGameSeq();
            List<GameJoinTeamRecordDTO> joinTeams = gameRecordManagerRepository.findGameJoinTeamRecordsByGameSeq(gameSeq);

            GameJoinTeamRecordDTO homeTeam = filterGameJoinTeamByHomeAwayCodeTODO(joinTeams, HomeAwayCode.HOME_TEAM);
            GameJoinTeamRecordDTO awayTeam = filterGameJoinTeamByHomeAwayCodeTODO(joinTeams, HomeAwayCode.AWAY_TEAM);

            gr.homeTeam(homeTeam)
                    .awayTeam(awayTeam);
        }

        return new SearchMyTeamGamesResponse( pagination.getPages( games.get( 0 ).getTotalCount() ), games );
    }

    // TODO 메소드 쪼개기... 함수명과 다른 처리를 하는 동작이 존재...
    private GameJoinTeamRecordDTO filterGameJoinTeamByHomeAwayCodeTODO(List<GameJoinTeamRecordDTO> joinTeams, HomeAwayCode homeAwayCode) {
        /** 참가팀 구분 - 홈/어웨이팀 */
        GameJoinTeamRecordDTO joinTeam = joinTeams.stream()
                                            .filter(t -> homeAwayCode.getCode().equals(t.getHomeAwayCode()))
                                            .findFirst()
                                            // TODO 에러메세지 동적으로 처리하기 homeAwayCode.getName();
                                            .orElseThrow(() -> new CustomException(DomainErrorType.NOT_FOUND_HOME_TEAM));
        joinTeam.homeAwayCodeName(joinTeam.getHomeAwayCode());

        /** 참가팀의 기록 조회 */
        Long gameJoinTeamSeq = joinTeam.getGameJoinTeamSeq();
        List<QuarterTeamRecords> quarterRecords = quarterTeamRecordsRepository.findQuarterRecordsByJoinTeamSeq(gameJoinTeamSeq);

        /** 조회한 기록으로 게임총점수 계산 */
        int gameTotalScore = 0;
        if (quarterRecords.isEmpty()) {
            joinTeam.gameTotalScore(gameTotalScore);
            joinTeam.quarterScore1st(0);
            joinTeam.quarterScore2nd(0);
            joinTeam.quarterScore3rd(0);
            joinTeam.quarterScore4th(0);
            return joinTeam;
        }

        for (QuarterTeamRecords qtr : quarterRecords)
        {
            qtr.calculateQuarterTotalScore();
            int quarterScore = qtr.getScore();
            gameTotalScore += quarterScore;

            String quarterCode = qtr.getQuarterCode();
            if ( QUARTER_1ST_CODE.equals( quarterCode ) ) {
                joinTeam.quarterScore1st( quarterScore );
            } else if ( QUARTER_2ND_CODE.equals( quarterCode ) ) {
                joinTeam.quarterScore2nd( quarterScore );
            } else if ( QUARTER_3RD_CODE.equals( quarterCode ) ) {
                joinTeam.quarterScore3rd( quarterScore );
            } else if ( QUARTER_4TH_CODE.equals( quarterCode ) ) {
                joinTeam.quarterScore4th( quarterScore );
            }
        }

        joinTeam.gameTotalScore( gameTotalScore );
        return joinTeam;
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
     * 23.01.28
     * 게임참가팀 팀원조회
     * 게임 입력권한을 부여하기 위해 게임참가팀원을 조회한다.
     * (이미 권한을 부여받은 선수는 제외한다.)
     * @param searchGameDTO 게임조회용 DTO
     * @author 강창기
     */
    public List<PlayerInfoDTO> getListTeamMembers(SearchGameDTO searchGameDTO) {

        List<PlayerInfoDTO> resultDVOList = gameRecordManagerRepository.findTeamMembersByGameSeq(searchGameDTO);

        return resultDVOList;
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