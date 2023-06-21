package com.threeNerds.basketballDiary.mvc.game.service;

import com.threeNerds.basketballDiary.constant.code.GameRecordStateCode;
import com.threeNerds.basketballDiary.constant.code.GameTypeCode;
import com.threeNerds.basketballDiary.constant.code.HomeAwayCode;
import com.threeNerds.basketballDiary.constant.code.PlayerTypeCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.Error;
import com.threeNerds.basketballDiary.http.ResponseJsonBody;
import com.threeNerds.basketballDiary.mvc.game.controller.request.RegisterGameJoinPlayersRequest;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameEntry.response.GetGameEntryResponse;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameEntry.request.GetGameEntryRequest;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameEntry.response.QuarterTeamEntryDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameJoinPlayers.response.GetGameJoinPlayersResponse;
import com.threeNerds.basketballDiary.mvc.game.domain.Game;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameJoinPlayers.request.GetGameJoinPlayersRequest;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameJoinTeamMembers.request.GetGameJoinTeamMembersRequest;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameJoinTeamMembers.response.GetGameJoinTeamMembersResponse;
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

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final GameRepository gameRepository;
    private final GameJoinTeamRepository gameJoinTeamRepository;
    private final GameJoinPlayerRepository gameJoinPlayerRepository;
    private final QuarterPlayerRecordsRepository quarterPlayerRecordsRepo;

    private final GameJoinManagerRepository gameJoinManagerRepo;


    /** 게임참가팀 확정  */
    // TODO 리팩토링하기...
    public void confirmJoinTeam(GameJoinTeamCreationDTO joinTeamCreationDTO) {
        final Long gameSeq          = joinTeamCreationDTO.getGameSeq();
        final String gameTypeCode   = joinTeamCreationDTO.getGameTypeCode();

        /** 게임기록상태코드 변경 - 게임생성(01) >> 게임참가팀확정(02) */
        Game joinTeamConfirm = Game.builder()
                .gameSeq( gameSeq )
                .gameRecordStateCode(GameRecordStateCode.JOIN_TEAM_CONFIRMATION.getCode())
                .build();
        gameRepository.updateGameRecordState(joinTeamConfirm);

        /** 게임유형코드별 처리 */
        if (GameTypeCode.SELF_GAME.getCode().equals(gameTypeCode)) {
            boolean hasJoinTeam = hasGameJoinTeam(gameSeq, HomeAwayCode.HOME_TEAM)
                                  || hasGameJoinTeam(gameSeq, HomeAwayCode.AWAY_TEAM);
            if (hasJoinTeam) {
                throw new CustomException(Error.ALREADY_EXIST_JOIN_TEAM);
            }
            Team gameCreatorTeam = gameJoinManagerRepo.findGameCreatorTeam(gameSeq);

            GameJoinTeam homeTeamInSelfGame = GameJoinTeam.createHomeTeamForSelfGame(gameSeq, gameCreatorTeam);
            gameJoinTeamRepository.saveGameJoinTeam(homeTeamInSelfGame);

            GameJoinTeam awayTeamInSelfGame = GameJoinTeam.createAwayTeamForSelfGame(gameSeq, gameCreatorTeam);
            gameJoinTeamRepository.saveGameJoinTeam(awayTeamInSelfGame);
            return;
        }

        if (GameTypeCode.MATCH_UP_GAME.getCode().equals(gameTypeCode)) {
            // 해당 게임에 홈팀이 등록되어 있는지 확인
            // 홈팀이 없으면 홈팀등록
            if (!hasGameJoinTeam(gameSeq, HomeAwayCode.HOME_TEAM)) {
                Team gameCreatorTeam = gameJoinManagerRepo.findGameCreatorTeam(gameSeq);
                GameJoinTeam homeTeam = GameJoinTeam.create(gameSeq, HomeAwayCode.HOME_TEAM, gameCreatorTeam);
                gameJoinTeamRepository.saveGameJoinTeam(homeTeam);
            }

            boolean hasOpponent = hasGameJoinTeam(gameSeq, HomeAwayCode.AWAY_TEAM);
            if (hasOpponent) {
                throw new CustomException(Error.ALREADY_EXIST_JOIN_TEAM);
            }

            Long opponentTeamSeq = Optional.ofNullable(joinTeamCreationDTO.getOpponentTeamSeq())
                                            .orElseThrow(() -> new CustomException(Error.NO_PARAMETER));

            Team opponentTeam = Optional.ofNullable(teamRepository.findByTeamSeq(opponentTeamSeq))
                                        .orElseThrow(()-> new CustomException(Error.TEAM_NOT_FOUND));
            GameJoinTeam awayTeam = GameJoinTeam.create(gameSeq, HomeAwayCode.AWAY_TEAM, opponentTeam);
            gameJoinTeamRepository.saveGameJoinTeam(awayTeam);
            return;
        }

        // TODO 구현예정
//        if (GameTypeCode.COMPETITION.getCode().equals(gameTypeCode))
//        {
//            // 홈팀 등록
//            GameJoinTeam newHomeTeam = GameJoinTeam.createHomeTeam(joinTeamCreationDTO);
//            gameJoinTeamRepository.saveGameJoinTeam(newHomeTeam);
//            // 어웨이팀 등록
//            GameJoinTeam newAwayTeam = GameJoinTeam.createAwayTeam(joinTeamCreationDTO);
//            gameJoinTeamRepository.saveGameJoinTeam(newAwayTeam);
//            return;
//        }
    }

    // TODO 해당게임의 참가팀으로 등록되어있는지 여부를 공통함수로 작성 false / true로 반환
    private boolean hasGameJoinTeam(Long gameSeq, HomeAwayCode homeAwayCode)
    {
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

    public List<GameOpponentDTO> searchOpponents(SearchOppenentsDTO searchCond)
    {
        //TODO 상대팀 목록 조회 - 페이징 처리 / 검색조건 >> Like조건
        return gameJoinManagerRepo.findOpponents(searchCond);
    }

    public Map<HomeAwayCode, GameJoinTeamInfoDTO> getGameJoinTeams(SearchGameJoinTeamDTO searchParam)
    {
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
    public void registerGameJoinPlayers( RegisterGameJoinPlayersRequest reqBody ) {

        Long gameSeq                                    = reqBody.getGameSeq();
        String homeAwayCode                             = reqBody.getHomeAwayCode();
        List<GameJoinPlayerDTO> gameJoinPlayers   = reqBody.getGameJoinPlayers();

        /** 게임참가팀이 존재하는지 확인 */
        GameJoinTeam joinTeamParam = GameJoinTeam.createInqCond( gameSeq, homeAwayCode );
        GameJoinTeam gameJoinTeam = gameJoinTeamRepository.findGameJoinTeam( joinTeamParam );

        /** 해당 게임의 쿼터선수기록 존재여부 확인 - 쿼터기록이 존재할 경우 수정 불가 */
        // TODO 에러메세지 수정
        List<QuarterPlayerRecords> playersRecord = quarterPlayerRecordsRepo.findAllInGame(gameSeq);
        boolean hasPlayerRecord = !playersRecord.isEmpty();
        if (hasPlayerRecord) {
            throw new CustomException(Error.INVALID_PARAMETER);
        }

        /** 게임참가선수 데이터 존재여부 확인 - 기존 데이터 존재시 삭제 */
        GameJoinPlayer joinPlayerParam = GameJoinPlayer.createInqParam( gameSeq, homeAwayCode );
        List<GameJoinPlayer> registeredJoinPlayers = gameJoinPlayerRepository.findPlayers( joinPlayerParam );
        boolean hasJoinPlayers = !registeredJoinPlayers.isEmpty();
        if (hasJoinPlayers) {
            gameJoinPlayerRepository.deletePlayers( joinPlayerParam );
        }

        /** 중복된 등번호가 있는지 체크하기 */
        Set<String> backNumberSet = new HashSet<>();
        for (GameJoinPlayerDTO player : gameJoinPlayers)
        {
            String backNumber = player.getBackNumber();
            boolean isDuplicatedBackNumber = !backNumberSet.add(backNumber);
            if (isDuplicatedBackNumber) {
                throw new CustomException(Error.DUPLICATE_BACK_NUMBER);
            }
        }
        // TODO 중복된 회원이 있는지 체크하기 - userSeq의 중복이 있는지 stream으로 확인

        /** 게임참가선수 데이터 저장 - 선수유형에 따라서 처리하기 */
        for (GameJoinPlayerDTO joinPlayerDTO : gameJoinPlayers)
        {
            String playerTypeCode = joinPlayerDTO.getPlayerTypeCode();
            boolean isUnauthGuest = PlayerTypeCode.UNAUTH_GUEST.getCode().equals(playerTypeCode);
            /** 회원이 아닌 선수는 입력값을 직접 DB에 insert */
            if (isUnauthGuest)
            {
                GameJoinPlayer unauthGuest = GameJoinPlayer.createUnauthPlayer( gameJoinTeam, joinPlayerDTO );
                gameJoinPlayerRepository.save(unauthGuest);
                continue;
            }

            /** 회원인 선수는 User테이블에서 데이터를 조회하여 insert */
            String backNumber = joinPlayerDTO.getBackNumber();
            User user = userRepository.findUser(joinPlayerDTO.getUserSeq());
            GameJoinPlayer authJoinPlayer = GameJoinPlayer.createAuthPlayer( gameJoinTeam, playerTypeCode, backNumber, user);
            gameJoinPlayerRepository.save(authJoinPlayer);
        }
    }

    /**
     * 22.12.18
     * 경기참가선수 조회
     * @author 이성주
     * @updator 여인준
     * - homeAwayCode가 없을때는 홈,어웨이 모두 조회
     * - homeAwayCode가 있을때는 해당하는 팀의 팀원 조회
     * @return enum을 key값으로 사용하여 홈/어웨이팀 구분
     */
    public GetGameJoinPlayersResponse getGameJoinPlayers(GetGameJoinPlayersRequest request) {
        /** 게임에 참가한 팀 및 팀원을 모두 조회해서 필터링 */
        final Long gameSeq = request.getGameSeq();
        List<GameJoinTeam> allGameJoinTeams = gameJoinTeamRepository.findAllGameJoinTeam(gameSeq);

        SearchPlayersDTO searchCond = new SearchPlayersDTO()
                                            .gameSeq(gameSeq);
        List<PlayerInfoDTO> allGameJoinPlayers = gameJoinManagerRepo.findAllGameJoinPlayers(searchCond);

        /** 한개팀 선수 조회일 경우 */
        String homeAwayCode = request.getHomeAwayCode();
        boolean isSearchOneTeamPlayers = StringUtils.hasText(homeAwayCode);
        if (isSearchOneTeamPlayers)
        {
            Map<HomeAwayCode, GameJoinTeamDTO> teamMap = new HashMap<>();
            if (HomeAwayCode.HOME_TEAM.getCode().equals(homeAwayCode)) {
                return new GetGameJoinPlayersResponse(
                            gameSeq,
                            createTeamWithPlayers(HomeAwayCode.HOME_TEAM, allGameJoinTeams, allGameJoinPlayers),
                            null
                );
            }
            return new GetGameJoinPlayersResponse(
                        gameSeq,
                        null,
                        createTeamWithPlayers(HomeAwayCode.AWAY_TEAM, allGameJoinTeams, allGameJoinPlayers)
            );
        }

        /** 양팀 선수 조회일 경우 */
        return new GetGameJoinPlayersResponse(
                gameSeq,
                createTeamWithPlayers(HomeAwayCode.HOME_TEAM, allGameJoinTeams, allGameJoinPlayers),
                createTeamWithPlayers(HomeAwayCode.AWAY_TEAM, allGameJoinTeams, allGameJoinPlayers)
        );
    }

    private GameJoinTeamDTO createTeamWithPlayers(HomeAwayCode code, List<GameJoinTeam> gameJoinTeams, List<PlayerInfoDTO> players)
    {
        // TODO 에러메세지 채워넣기
        GameJoinTeam filteredTeam = gameJoinTeams.stream()
                                                .filter(p -> p.getHomeAwayCode().equals(code.getCode()))
                                                .findAny()
                                                .orElseThrow();
        
        List<PlayerInfoDTO> filteredPlayers = players.stream()
                                                .filter(p -> p.getHomeAwayCode().equals(code.getCode()))
                                                .collect(Collectors.toList());

        GameJoinTeamDTO gameJoinTeam = new GameJoinTeamDTO()
                .gameJoinTeamSeq(filteredTeam.getGameJoinTeamSeq())
                .teamSeq(filteredTeam.getTeamSeq())
                .homeAwayCode(code.getCode())
                .homeAwayCodeName(code.getName())
                .players(filteredPlayers);

        return gameJoinTeam;
    }

    /**
     * 쿼터 엔트리 목록 저장
     * @param quarterEntryInfoDTO
     */
    public void saveQuarterEntryInfo(QuarterEntryInfoDTO quarterEntryInfoDTO)
    {
        /** TODO 게임생성자 권한 체크 - 게임기록권한T 조회해서 권한체크 */

        /** 쿼터 엔트리 길이 체크 5명이 되는지 */
        List<PlayerInfoDTO> entry = quarterEntryInfoDTO.getPlayerList();
        boolean hasNotValidEntry = entry.size() != 5;
        if (hasNotValidEntry) {
            throw new CustomException(Error.INSUFFICIENT_PLAYERS_ON_ENTRY);
        }

        /** 기존에 엔트리가 등록되어 있는지 조회 */
        Long gameSeq         = quarterEntryInfoDTO.getGameSeq();
        String homeAwayCode  = quarterEntryInfoDTO.getHomeAwayCode();
        Long gameJoinTeamSeq = quarterEntryInfoDTO.getGameJoinTeamSeq();
        String quarterCode   = quarterEntryInfoDTO.getQuarterCode();

        SearchEntryDTO params = new SearchEntryDTO()
                .gameSeq( gameSeq )
                .homeAwayCode( homeAwayCode )
                .quarterCode( quarterCode );

        List<QuarterPlayerRecordDTO> findExistedEntry = gameJoinManagerRepo.findOneTeamEntry(params);
        boolean hasNoFullEntry = findExistedEntry.size() != 5;
        if ( hasNoFullEntry )
        {
            // TODO 기존 엔트리 지우기
            /** 엔트리가 등록되어 있지 않으면 새로 등록 - insert */
            for (PlayerInfoDTO playerInfoDTO : entry)
            {
                /** 게임참가선수 테이블에 존재하는 선수인지 확인 - 게임참가선수Seq로만 조회 */
                Long gameJoinPlayerSeq = playerInfoDTO.getGameJoinPlayerSeq();
                GameJoinPlayer gameJoinPlayerParam = GameJoinPlayer.builder()
                                .gameJoinPlayerSeq( gameJoinPlayerSeq )
                                .build();
                Optional.ofNullable(gameJoinPlayerRepository.findPlayer(gameJoinPlayerParam))
                        .orElseThrow(() -> new CustomException(Error.INVALID_PARAMETER));

                QuarterPlayerRecords paramForCreation = QuarterPlayerRecords.builder()
                        .gameSeq( gameSeq )
                        .homeAwayCode( homeAwayCode )
                        .gameJoinTeamSeq( playerInfoDTO.getGameJoinTeamSeq() )
                        .gameJoinPlayerSeq( gameJoinPlayerSeq )
                        .quarterCode(quarterCode)
                        .inGameYn("Y")
                        .build();
                quarterPlayerRecordsRepo.save(paramForCreation);
            }
            return;
        }

        /** 엔트리가 등록되어 있다면 InGameYn "N"으로 초기화 후 대상들만 "Y"으로 변경 */
        QuarterPlayerRecords paramForInitEntry = QuarterPlayerRecords.builder()
                .gameJoinTeamSeq( gameJoinTeamSeq )
                .quarterCode(quarterCode)
                .inGameYn("N")
                .build();

        quarterPlayerRecordsRepo.updateInGameYn( paramForInitEntry );

        for (PlayerInfoDTO playerInfoDTO : entry)
        {
            Long gameJoinPlayerSeq = playerInfoDTO.getGameJoinPlayerSeq();
            QuarterPlayerRecords paramForEntry = QuarterPlayerRecords.builder()
                    .gameJoinPlayerSeq( gameJoinPlayerSeq )
                    .quarterCode(quarterCode)
                    .inGameYn("Y")
                    .build();

            quarterPlayerRecordsRepo.updateInGameYn(paramForEntry);
        }
        return;
    }

    /**
     * 게임엔트리 조회하기
     * @param request
     * @return GetGameEntryResponse
     */
    public GetGameEntryResponse getGameEntry(GetGameEntryRequest request)
    {
        String HOME_TEAM_CODE = HomeAwayCode.HOME_TEAM.getCode();
        String AWAY_TEAM_CODE = HomeAwayCode.AWAY_TEAM.getCode();

        /** 한 게임의 모든 게임참가팀 조회 */
        List<GameJoinTeam> gameJoinTeams = gameJoinTeamRepository.findAllGameJoinTeam( request.getGameSeq() );

        String quarterCode = request.getQuarterCode();
        String homeAwayCode = request.getHomeAwayCode();

        /** 홈어웨이코드 존재 여부확인 - 코드값이 존재하면 팀의 엔트리만 조회 */
        boolean isOnlyOneTeamInquery = StringUtils.hasText(homeAwayCode);
        if ( isOnlyOneTeamInquery ) {
            if ( HOME_TEAM_CODE.equals( homeAwayCode ) ) {
                return new GetGameEntryResponse()
                                .homeTeamEntry( getQuarterTeamEntryInfo( quarterCode, HOME_TEAM_CODE, gameJoinTeams ) );
            }
            if ( AWAY_TEAM_CODE.equals( homeAwayCode ) ) {
                 return new GetGameEntryResponse()
                               .awayTeamEntry( getQuarterTeamEntryInfo( quarterCode, AWAY_TEAM_CODE, gameJoinTeams ) );
            }
            // TODO 에러 던지기 - 부적절한 코드값이 존재합니다. 코드값을 확인해주시기 바랍니다.
        }

        /** 홈/어웨이팀 전체 엔트리 조회 */
        QuarterTeamEntryDTO homeTeamEntryInfo = getQuarterTeamEntryInfo( quarterCode, HOME_TEAM_CODE, gameJoinTeams );
        QuarterTeamEntryDTO awayTeamEntryInfo = getQuarterTeamEntryInfo( quarterCode, AWAY_TEAM_CODE, gameJoinTeams );

        return new GetGameEntryResponse()
                .homeTeamEntry( homeTeamEntryInfo )
                .awayTeamEntry( awayTeamEntryInfo );
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

    /**
     * 23.01.28
     * 게임참가팀 팀원조회
     * 게임 입력권한을 부여하기 위해 경기에 참여한 팀원을 조회한다.
     * (이미 권한을 부여받은 선수는 제외한다.) TODO 기능 추가 예정
     * @author 강창기
     * @update 여인준 / 소스코드 이전 ( 기존 GameRecordManagerService에서 )
     */
    public ResponseJsonBody getGameJoinTeamMembers( GetGameJoinTeamMembersRequest reqBody ) {

        SearchGameJoinTeamMemberDTO searchCond = new SearchGameJoinTeamMemberDTO()
                                        .gameSeq( reqBody.getGameSeq() )
                                        .homeAwayCode( reqBody.getHomeAwayCode() );

        List<GameJoinTeamMemberDTO> gameJoinPlayers = gameJoinManagerRepo.findAllGameJoinTeamMembers( searchCond );

        return new GetGameJoinTeamMembersResponse( gameJoinPlayers );
    }
}
