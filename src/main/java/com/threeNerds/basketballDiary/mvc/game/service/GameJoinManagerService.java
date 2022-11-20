package com.threeNerds.basketballDiary.mvc.game.service;

import com.threeNerds.basketballDiary.constant.code.GameTypeCode;
import com.threeNerds.basketballDiary.constant.code.HomeAwayCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.Error;
import com.threeNerds.basketballDiary.mvc.domain.Team;
import com.threeNerds.basketballDiary.mvc.dto.team.team.TeamDTO;
import com.threeNerds.basketballDiary.mvc.game.domain.GameJoinTeam;
import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinTeamCreationDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.SearchGameHomeAwayDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.SearchOppenentsDTO;
import com.threeNerds.basketballDiary.mvc.game.repository.GameJoinTeamRepository;
import com.threeNerds.basketballDiary.mvc.game.repository.GameRepository;
import com.threeNerds.basketballDiary.mvc.game.repository.dto.GameJoinManagerRepository;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.FindGameHomeAwayDTO;
import com.threeNerds.basketballDiary.mvc.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GameJoinManagerService {

    private final TeamRepository teamRepository;
    private final GameRepository gameRepository;
    private final GameJoinTeamRepository gameJoinTeamRepository;

    private final GameJoinManagerRepository gameJoinManagerRepository;


    /** 게임참가팀 확정 */
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

            GameJoinTeam homeTeamInSelfGame = GameJoinTeam.createHomeTeam(gameSeq, gameCreatorTeam);
            gameJoinTeamRepository.saveGameJoinTeam(homeTeamInSelfGame);

            GameJoinTeam awayTeamInSelfGame = GameJoinTeam.createAwayTeam(gameSeq, gameCreatorTeam);
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
                GameJoinTeam homeTeam = new GameJoinTeam().builder()
                                            .gameSeq(gameSeq)
                                            .teamSeq(gameCreatorTeam.getTeamSeq())
                                            .teamName(gameCreatorTeam.getTeamName())
                                            .homeAwayCode(HomeAwayCode.HOME_TEAM.getCode())
                                            .build();
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
            GameJoinTeam awayTeam = new GameJoinTeam().builder()
                                        .gameSeq(gameSeq)
                                        .teamSeq(opponentTeam.getTeamSeq())
                                        .teamName(opponentTeam.getTeamName())
                                        .homeAwayCode(HomeAwayCode.AWAY_TEAM.getCode())
                                        .build();
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
}
