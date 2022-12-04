package com.threeNerds.basketballDiary.mvc.game.service;

import com.threeNerds.basketballDiary.constant.code.GameTypeCode;
import com.threeNerds.basketballDiary.constant.code.HomeAwayCode;
import com.threeNerds.basketballDiary.constant.code.PlayerTypeCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.Error;
import com.threeNerds.basketballDiary.mvc.domain.Team;
import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.dto.team.team.TeamDTO;
import com.threeNerds.basketballDiary.mvc.game.controller.dto.GameJoinPlayerRegistrationDTO;
import com.threeNerds.basketballDiary.mvc.game.controller.dto.QuarterEntryDTO;
import com.threeNerds.basketballDiary.mvc.game.domain.GameJoinPlayer;
import com.threeNerds.basketballDiary.mvc.game.domain.GameJoinTeam;
import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinPlayerDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinTeamCreationDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.SearchGameHomeAwayDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.SearchOppenentsDTO;
import com.threeNerds.basketballDiary.mvc.game.repository.GameJoinPlayerRepository;
import com.threeNerds.basketballDiary.mvc.game.repository.GameJoinTeamRepository;
import com.threeNerds.basketballDiary.mvc.game.repository.GameRepository;
import com.threeNerds.basketballDiary.mvc.game.repository.dto.GameJoinManagerRepository;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.FindGameHomeAwayDTO;
import com.threeNerds.basketballDiary.mvc.repository.TeamRepository;
import com.threeNerds.basketballDiary.mvc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private final GameJoinManagerRepository gameJoinManagerRepository;


    /** 게임참가팀 확정  TODO 리팩토링하기... */
    public void confirmJoinTeam(GameJoinTeamCreationDTO joinTeamCreationDTO)
    {
        String gameTypeCode = joinTeamCreationDTO.getGameTypeCode();
        if (GameTypeCode.SELF_GAME.getCode().equals(gameTypeCode))
        {
            Long gameSeq = joinTeamCreationDTO.getGameSeq();
            boolean hasJoinTeam = hasGameJoinTeam(gameSeq, HomeAwayCode.HOME_TEAM)
                                  || hasGameJoinTeam(gameSeq, HomeAwayCode.AWAY_TEAM);
            if (hasJoinTeam) {
                throw new CustomException(Error.ALREADY_EXIST_JOIN_TEAM);
            }
            Team gameCreatorTeam = gameJoinManagerRepository.findGameCreatorTeam(gameSeq);

            GameJoinTeam homeTeamInSelfGame = GameJoinTeam.createHomeTeamForSelfGame(gameSeq, gameCreatorTeam);
            gameJoinTeamRepository.saveGameJoinTeam(homeTeamInSelfGame);

            GameJoinTeam awayTeamInSelfGame = GameJoinTeam.createAwayTeamForSelfGame(gameSeq, gameCreatorTeam);
            gameJoinTeamRepository.saveGameJoinTeam(awayTeamInSelfGame);
            return;
        }

        if (GameTypeCode.MATCH_UP_GAME.getCode().equals(gameTypeCode))
        {
            Long gameSeq = joinTeamCreationDTO.getGameSeq();
            // 해당 게임에 홈팀이 등록되어 있는지 확인
            // 홈팀이 없으면 홈팀등록
            if (!hasGameJoinTeam(gameSeq, HomeAwayCode.HOME_TEAM))
            {
                Team gameCreatorTeam = gameJoinManagerRepository.findGameCreatorTeam(gameSeq);
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

    //TODO 상대팀 목록 조회
    public List<TeamDTO> searchOpponents(String sidoCode,String teamName,String leaderName){

        SearchOppenentsDTO searchOppenentsDTO = new SearchOppenentsDTO()
                                                        .sidoCode(sidoCode)
                                                        .teamName(teamName)
                                                        .leaderName(leaderName);
        return gameJoinManagerRepository.searchOpponents(searchOppenentsDTO);
    }

    public List<FindGameHomeAwayDTO> findGameHomeAwayInfo(Long gameSeq,String homeAwayCode){

        SearchGameHomeAwayDTO searchGameHomeAwayDTO = new SearchGameHomeAwayDTO()
                .gameSeq(gameSeq)
                .homeAwayCode(homeAwayCode);
        List<FindGameHomeAwayDTO> gameTeams = gameJoinManagerRepository.findGameTeams(searchGameHomeAwayDTO);
        return gameTeams;
//        return gameJoinManagerRepository.findGameTeams(searchGameHomeAwayDTO);
    }

    /**
     * 게임참가선수 등록
     **/
    public void registerGameJoinPlayers(GameJoinPlayerRegistrationDTO playerRegistrationDTO)
    {
        Long gameJoinTeamSeq = playerRegistrationDTO.getGameJoinTeamSeq();

        /** 게임참가선수 데이터 존재여부 확인 - 기존 데이터 존재시 삭제 */
        List<GameJoinPlayer> registeredJoinPlayers = gameJoinPlayerRepository.findPlayers(gameJoinTeamSeq);
        boolean hasJoinPlayers = !registeredJoinPlayers.isEmpty();
        if (hasJoinPlayers) {
            gameJoinPlayerRepository.deletePlayers(gameJoinTeamSeq);
        }

        /** 중복된 등번호가 있는지 체크하기 */
        List<GameJoinPlayerDTO> gameJoinPlayerDTOList = playerRegistrationDTO.getGameJoinPlayerDTOList();
        Set<String> backNumberSet = new HashSet<>();
        for (GameJoinPlayerDTO player : gameJoinPlayerDTOList)
        {
            String backNumber = player.getBackNumber();
            boolean isDuplicatedBackNumber = !backNumberSet.add(backNumber);
            if (isDuplicatedBackNumber) {
                throw new CustomException(Error.DUPLICATE_BACK_NUMBER);
            }
        }
        // TODO 중복된 회원이 있는지 체크하기 - userSeq의 중복이 있는지 stream으로 확인

        /** 게임참가선수 데이터 저장 - 선수유형에 따라서 처리하기 */
        for (GameJoinPlayerDTO joinPlayerDTO : gameJoinPlayerDTOList)
        {
            String playerTypeCode = joinPlayerDTO.getPlayerTypeCode();
            boolean isUnauthGuest = PlayerTypeCode.UNAUTH_GUEST.getCode().equals(playerTypeCode);
            /** 회원이 아닌 선수는 입력값을 직접 DB에 insert */
            if (isUnauthGuest)
            {
                GameJoinPlayer unauthGuest = GameJoinPlayer.createUnauthPlayer(gameJoinTeamSeq, joinPlayerDTO);
                gameJoinPlayerRepository.save(unauthGuest);
                continue;
            }

            /** 회원인 선수는 User테이블에서 데이터를 조회하여 insert */
            String backNumber = joinPlayerDTO.getBackNumber();
            User user = userRepository.findUser(joinPlayerDTO.getUserSeq());
            GameJoinPlayer authJoinPlayer = GameJoinPlayer.createAuthPlayer(gameJoinTeamSeq, playerTypeCode, backNumber, user);
            gameJoinPlayerRepository.save(authJoinPlayer);
        }
    }

    /**
     * 쿼터 엔트리 목록 저장
     * @param quarterEntryList
     */
    public void saveQuarterEntryInfo(List<QuarterEntryDTO> quarterEntryList)
    {
        // TODO 쿼터 엔트리 길이 체크 5명이 되는지
        // TODO 기존에 엔트리가 등록되어 있는지 조회
        //  (QUARTER_PLAYER_RECORDS 테이블에 row가 5명이상인지 체크)
        //      - QUARTER_PLAYER_RECORDS에 존재하지 않으면 quarterEntry로 초기화 insert
        //      - 존재하면 해당 게임의 해당팀의 모든 선수들의 InGameYn을 N로 일괄 update
        //      - 그리고 quarterEntry에 있는 선수들의 InGameYn(Y)로 udpate후 경기스탯 update
    }
}
