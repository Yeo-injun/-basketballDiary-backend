package com.threeNerds.basketballDiary.mvc.game.service;

import com.threeNerds.basketballDiary.constant.code.GameRecordStateCode;
import com.threeNerds.basketballDiary.constant.code.GameTypeCode;
import com.threeNerds.basketballDiary.constant.code.HomeAwayCode;
import com.threeNerds.basketballDiary.constant.code.QuarterCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.Error;
import com.threeNerds.basketballDiary.mvc.game.dto.PlayerRecordDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.SearchGameDTO;
import com.threeNerds.basketballDiary.mvc.game.domain.QuarterTeamRecords;
import com.threeNerds.basketballDiary.mvc.game.repository.GameJoinTeamRepository;
import com.threeNerds.basketballDiary.mvc.game.repository.QuarterTeamRecordsRepository;
import com.threeNerds.basketballDiary.mvc.game.repository.dto.GameRecordManagerRepository;
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

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GameRecordManagerService {

    private final GameRepository gameRepository;
    private final GameJoinTeamRepository gameJoinTeamRepository;
    private final QuarterTeamRecordsRepository quarterTeamRecordsRepository;
    private final TeamMemberRepository teamMemberRepository;

    private final GameRecordManagerRepository gameRecordManagerRepository;

    /**
     * 22.11.06
     * 특정쿼터의 선수별 기록조회
     * homeAwayCode에 따라 특정쿼터의 선수별 기록을 조회한다.
     * @param searchGameDTO 게임조회용 DTO
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

    /**
     * 22.11.06
     * 특정쿼터의 팀별 기록조회
     * 홈&어웨이의 쿼터별 팀 합산기록을 조회한다.
     * @param searchGameDTO 게임조회용 DTO
     * @author 강창기
     */
    public List<GameJoinTeamRecordDTO> getTeamRecordByQuarter(SearchGameDTO searchGameDTO) {
        return null;
    }


    /** 22.11.06
     * 소속팀의 게임기록조회
     * @author 여인준
     **/
    public List<GameRecordDTO> searchMyTeamGames(GameCondDTO gc)
    {
        /**
         *  1. 소속팀을 기준으로 게임정보 조회 - List<GameRecordDTO>를 받아서 이후 for순회
         *  2. 조회된 게임정보목록을 순회
         *      - 참가팀 조회하여 GameRecordDTO안에 필드채우기
         *  3. 참가팀 조회시 쿼터별기록을 조회해서 GameJoinTeamRecord필드에 할당해주기
         **/
        // 게임참가팀 테이블에서 TEAM_SEQ를 조회
        // TODO homeAwayCode로 게임 목록을 조회하기 >> 테이블 조인해야 할듯?
        // TODO 페이징 처리 구현하기
        List<GameRecordDTO> games = gameRecordManagerRepository.findGamesByTeamSeq(gc);

        for (GameRecordDTO gr : games)
        {
            gr.gameRecordStateCodeName(gr.getGameRecordStateCode());
            gr.gameTypeCodeName(gr.getGameTypeCode());
            if (GameRecordStateCode.CREATION.getCode().equals(gr.getGameRecordStateCode())) {
                continue;
            }
            Long gameSeq = gr.getGameSeq();
            List<GameJoinTeamRecordDTO> joinTeams = gameRecordManagerRepository.findGameJoinTeamRecordsByGameSeq(gameSeq);

            GameJoinTeamRecordDTO homeTeam = filterGameJoinTeamByHomeAwayCode(joinTeams, HomeAwayCode.HOME_TEAM);
            GameJoinTeamRecordDTO awayTeam = filterGameJoinTeamByHomeAwayCode(joinTeams, HomeAwayCode.AWAY_TEAM);

            gr.homeTeam(homeTeam)
                    .awayTeam(awayTeam);
        }

        return games;
    }

    private GameJoinTeamRecordDTO filterGameJoinTeamByHomeAwayCode(List<GameJoinTeamRecordDTO> joinTeams, HomeAwayCode homeAwayCode)
    {
        /** 참가팀 구분 - 홈/어웨이팀 */
        GameJoinTeamRecordDTO joinTeam = joinTeams.stream()
                                .filter(t -> homeAwayCode.getCode().equals(t.getHomeAwayCode()))
                                .findFirst()
                                // TODO 에러메세지 동적으로 처리하기 homeAwayCode.getName();
                                .orElseThrow(() -> new CustomException(Error.NOT_FOUND_HOME_TEAM));
        joinTeam.homeAwayCodeName(joinTeam.getHomeAwayCode());

        /** 참가팀의 기록 조회 */
        Long gameJoinTeamSeq = joinTeam.getGameJoinTeamSeq();
        List<QuarterTeamRecords> quarterRecords = quarterTeamRecordsRepository.findQuarterRecordsByJoinTeamSeq(gameJoinTeamSeq);

        /** 조회한 기록으로 게임총점수 계산 */
        List<QuarterRecordDTO> joinTeamQuarterRecords = new ArrayList<>();
        Integer gameTotalScore = 0;
        for (QuarterTeamRecords qtr : quarterRecords)
        {
            int quarterScore = qtr.getQuarterTotalScore();
            gameTotalScore += quarterScore;

            String quarterCode = qtr.getQuarterCode();
            QuarterRecordDTO quarterRecordDTO = new QuarterRecordDTO()
                    .quarterTeamRecordsSeq(qtr.getQuarterTeamRecordsSeq())
                    .quarterCode(quarterCode)
                    .quarterCodeName(QuarterCode.nameOf(quarterCode))
                    .quarterScore(quarterScore);
            joinTeamQuarterRecords.add(quarterRecordDTO);
        }

        joinTeam.gameTotalScore(gameTotalScore);
        joinTeam.quarters(joinTeamQuarterRecords);
        return joinTeam;
    }
}