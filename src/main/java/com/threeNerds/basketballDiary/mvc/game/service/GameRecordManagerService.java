package com.threeNerds.basketballDiary.mvc.game.service;

import com.threeNerds.basketballDiary.constant.code.GameRecordStateCode;
import com.threeNerds.basketballDiary.constant.code.GameTypeCode;
import com.threeNerds.basketballDiary.constant.code.HomeAwayCode;
import com.threeNerds.basketballDiary.constant.code.QuarterCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.Error;
import com.threeNerds.basketballDiary.mvc.game.domain.QuarterPlayerRecords;
import com.threeNerds.basketballDiary.mvc.game.dto.HomeAwayTeamRecordDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.PlayerRecordDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.SearchGameDTO;
import com.threeNerds.basketballDiary.mvc.game.domain.QuarterTeamRecords;
import com.threeNerds.basketballDiary.mvc.game.repository.GameJoinTeamRepository;
import com.threeNerds.basketballDiary.mvc.game.repository.QuarterPlayerRecordsRepository;
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
import org.springframework.util.StringUtils;

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
    private QuarterPlayerRecordsRepository quarterPlayerRecordsRepository;
    private final TeamMemberRepository teamMemberRepository;

    private final GameRecordManagerRepository gameRecordManagerRepository;

    private final String QUARTER_1ST_CODE = QuarterCode.FIRST.getCode();
    private final String QUARTER_2ND_CODE = QuarterCode.SECOND.getCode();
    private final String QUARTER_3RD_CODE = QuarterCode.THIRD.getCode();
    private final String QUARTER_4TH_CODE = QuarterCode.FOURTH.getCode();

    /**
     * 22.11.25
     * 쿼터별 선수기록 조회(단건)
     * @param quarterPlayerRecords 쿼터별선수기록 DTO
     * @author 강창기
     */
    public QuarterPlayerRecords findQuarterPlayerRecords(QuarterPlayerRecords quarterPlayerRecords) {
        if(ObjectUtils.isEmpty(quarterPlayerRecords))
            throw new CustomException(Error.NO_PARAMETER);

        return quarterPlayerRecordsRepository.find(quarterPlayerRecords);
    }

    /**
     * 22.11.25
     * 쿼터별 선수기록 수정
     * @param quarterPlayerRecords 쿼터별선수기록 DTO
     * @author 강창기
     */
    public void modifyQuarterPlayerRecords(QuarterPlayerRecords quarterPlayerRecords) {
        if(ObjectUtils.isEmpty(quarterPlayerRecords))
            throw new CustomException(Error.NO_PARAMETER);

        quarterPlayerRecordsRepository.modify(quarterPlayerRecords);
    }

    /**
     * 22.11.22
     * 특정쿼터의 선수별 기록조회(단건)
     * homeAwayCode에 따라 특정쿼터의 선수별 기록 단건을 조회한다.
     * @param searchGameDTO 게임조회용 DTO
     * @author 강창기
     */
    public PlayerRecordDTO getPlayerRecordsByQuarter(SearchGameDTO searchGameDTO) {
        if(ObjectUtils.isEmpty(searchGameDTO))
            throw new CustomException(Error.NO_PARAMETER);

        if(ObjectUtils.isEmpty(searchGameDTO.getQuarterPlayerRecordsSeq())) {
            if(ObjectUtils.isEmpty(searchGameDTO.getGameSeq())
                    || ObjectUtils.isEmpty(searchGameDTO.getGameJoinPlayerSeq())
                    || ObjectUtils.isEmpty(searchGameDTO.getGameJoinTeamSeq())
                    || ObjectUtils.isEmpty(searchGameDTO.getQuarterCode()))
                throw new CustomException(Error.NO_PARAMETER);

            if(ObjectUtils.isEmpty(gameRepository.getGameInfo(searchGameDTO.getGameSeq())))
                throw new CustomException(Error.NOT_FOUND_GAME);  // 게임 정보가 존재하지 않습니다.
        }

        PlayerRecordDTO resultDVO = gameRecordManagerRepository.findPlayerRecordsByQuarter(searchGameDTO);
        return resultDVO;
    }

    /**
     * 22.11.06
     * 특정쿼터의 선수별 기록조회(목록)
     * homeAwayCode에 따라 특정쿼터의 선수별 기록 목록을 조회한다.
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
     * 22.11.22
     * 특정쿼터의 홈·어웨이 팀기록조회
     * 홈 & 어웨이의 쿼터별 팀 합산기록을 조회한다.
     * @param searchGameDTO 게임조회용 DTO
     * @author 강창기
     */
    public HomeAwayTeamRecordDTO getHomeAwayTeamRecordByQuarter(SearchGameDTO searchGameDTO) {
        if(ObjectUtils.isEmpty(searchGameDTO))
            throw new CustomException(Error.NO_PARAMETER);

        if(ObjectUtils.isEmpty(searchGameDTO.getGameSeq())
                || ObjectUtils.isEmpty(searchGameDTO.getGameJoinTeamSeq())
                || ObjectUtils.isEmpty(searchGameDTO.getQuarterCode()))
            throw new CustomException(Error.NO_PARAMETER);

        if(ObjectUtils.isEmpty(gameRepository.getGameInfo(searchGameDTO.getGameSeq())))
            throw new CustomException(Error.NOT_FOUND_GAME);  // 게임 정보가 존재하지 않습니다.

        HomeAwayTeamRecordDTO resultDVO = gameRecordManagerRepository.findHomeAwayTeamRecordsByQuarter(searchGameDTO);

        return resultDVO;
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
        Integer gameTotalScore = 0;
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
            int quarterScore = qtr.getQuarterTotalScore();
            gameTotalScore += quarterScore;

            String quarterCode = qtr.getQuarterCode();
            if (QUARTER_1ST_CODE.equals(quarterCode)) {
                joinTeam.quarterScore1st(quarterScore);
                continue;
            }
            if (QUARTER_2ND_CODE.equals(quarterCode)) {
                joinTeam.quarterScore2nd(quarterScore);
                continue;
            }
            if (QUARTER_3RD_CODE.equals(quarterCode)) {
                joinTeam.quarterScore3rd(quarterScore);
                continue;
            }
            if (QUARTER_4TH_CODE.equals(quarterCode)) {
                joinTeam.quarterScore4th(quarterScore);
                continue;
            }
        }

        joinTeam.gameTotalScore(gameTotalScore);
        return joinTeam;
    }
}
