package com.threeNerds.basketballDiary.mvc.game.service;

import com.threeNerds.basketballDiary.constant.code.HomeAwayCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.Error;
import com.threeNerds.basketballDiary.mvc.game.dto.PlayerRecordDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.SearchGameDTO;
import com.threeNerds.basketballDiary.mvc.game.repository.GameJoinTeamRepository;
import com.threeNerds.basketballDiary.mvc.game.repository.GameRecordManagerRepository;
import com.threeNerds.basketballDiary.mvc.game.repository.GameRepository;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.GameCondDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.GameJoinTeamRecordDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.GameRecordDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.QuarterRecordDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GameRecordManagerService {

    private final GameRepository gameRepository;
    private final GameJoinTeamRepository gameJoinTeamRepository;
    private final TeamMemberRepository teamMemberRepository;

    private final GameRecordManagerRepository gameRecordManagerRepository;

    /**
     * 특정쿼터의 선수별 기록조회
     * @author 강창기
     */
    public List<PlayerRecordDTO> getListPlayerRecordsByQuarter(SearchGameDTO searchGameDTO) {
        if(ObjectUtils.isEmpty(searchGameDTO))
            throw new CustomException(Error.NO_PARAMETER);

        // gameSeq에 해당하는 게임내역이 존재하는지 확인.
        Long gameSeq = searchGameDTO.getGameSeq();
        if(ObjectUtils.isEmpty(gameRepository.getGameInfo(gameSeq)))
            throw new CustomException(Error.NOT_FOUND_GAME);  // 게임 정보가 존재하지 않습니다.

        List<PlayerRecordDTO> resultList = gameRecordManagerRepository.findAllPlayerRecordsByQuarter(searchGameDTO);
        return resultList;
    }

    /** 22.11.06
     * 소속팀의 게임기록조회
     * @author 여인준
     **/
    public List<GameRecordDTO> searchMyTeamGames(GameCondDTO gc)
    {
        /** TODO
         *  1. 소속팀을 기준으로 게임정보 조회 - List<GameRecordDTO>를 받아서 이후 for순회
         *  2. 조회된 게임정보목록을 순회
         *      - 참가팀 조회하여 GameRecordDTO안에 필드채우기
         *  3. 참가팀 조회시 쿼터별기록을 조회해서 GameJoinTeamRecord필드에 할당해주기
         **/
        // 게임참가팀 테이블에서 TEAM_SEQ를 조회
        List<GameRecordDTO> games = gameRecordManagerRepository.findGamesByTeamSeq(gc);

        // TODO 추가 구현 및 테스트 필요!!
        for (GameRecordDTO gr : games)
        {
            Long gameSeq = gr.getGameSeq();
            List<GameJoinTeamRecordDTO> joinTeams = gameRecordManagerRepository.findGameJoinTeamRecordsByGameSeq(gameSeq);
            // 게임은 생성되었으나 게임 참가팀이 없는경우에 게임참가팀의 기록정보를 조회하지 않음.
            if (joinTeams.isEmpty()) {
                continue;
            }
            GameJoinTeamRecordDTO homeTeam = filterGameJoinTeamByHomeAwayCode(joinTeams, HomeAwayCode.HOME_TEAM);
            GameJoinTeamRecordDTO awayTeam = filterGameJoinTeamByHomeAwayCode(joinTeams, HomeAwayCode.AWAY_TEAM);

            gr.homeTeam(homeTeam)
                    .awayTeam(awayTeam);
        }

        return games;
    }

    private GameJoinTeamRecordDTO filterGameJoinTeamByHomeAwayCode(List<GameJoinTeamRecordDTO> joinTeams, HomeAwayCode homeAwayCode)
    {
        GameJoinTeamRecordDTO joinTeam = joinTeams.stream()
                                .filter(t -> homeAwayCode.getCode().equals(t.getHomeAwayCode()))
                                .findFirst()
                                // TODO 에러메세지 동적으로 처리하기 homeAwayCode.getName();
                                .orElseThrow(() -> new CustomException(Error.NOT_FOUND_HOME_TEAM));

        // TODO 쿼리 구현 필요
        Long gameJoinTeamSeq = joinTeam.getGameJoinTeamSeq();
        List<QuarterRecordDTO> joinTeamQuarterRecords = gameRecordManagerRepository.findJoinTeamQuarterRecords(gameJoinTeamSeq);

        joinTeam.quarters(joinTeamQuarterRecords);
        return joinTeam;
    }
}
