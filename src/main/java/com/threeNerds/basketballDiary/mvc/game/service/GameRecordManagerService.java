package com.threeNerds.basketballDiary.mvc.game.service;

import com.threeNerds.basketballDiary.constant.code.GameRecordStateCode;
import com.threeNerds.basketballDiary.constant.code.HomeAwayCode;
import com.threeNerds.basketballDiary.constant.code.QuarterCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.Error;
import com.threeNerds.basketballDiary.mvc.game.controller.dto.GameAuthDTO;
import com.threeNerds.basketballDiary.mvc.game.controller.response.GameAuthRecordersResponse;
import com.threeNerds.basketballDiary.mvc.game.domain.GameRecordAuth;
import com.threeNerds.basketballDiary.mvc.game.domain.QuarterPlayerRecords;
import com.threeNerds.basketballDiary.mvc.game.domain.QuarterTeamRecords;

import com.threeNerds.basketballDiary.mvc.game.dto.*;
import com.threeNerds.basketballDiary.mvc.game.dto.response.getGameAllQuartersRecords.QuarterAllTeamsRecordsDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.response.getGameAllQuartersRecords.QuarterTeamRecordsDTO;


import com.threeNerds.basketballDiary.mvc.game.repository.GameRecordAuthRepository;
import com.threeNerds.basketballDiary.mvc.game.repository.GameRepository;
import com.threeNerds.basketballDiary.mvc.game.repository.QuarterPlayerRecordsRepository;
import com.threeNerds.basketballDiary.mvc.game.repository.QuarterTeamRecordsRepository;
import com.threeNerds.basketballDiary.mvc.game.repository.dto.GameRecordManagerRepository;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.GameCondDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.GameJoinTeamRecordDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.GameRecordDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GameRecordManagerService {

    private final GameRepository gameRepository;
    private final QuarterTeamRecordsRepository quarterTeamRecordsRepository;
    private final QuarterPlayerRecordsRepository quarterPlayerRecordsRepository;

    private final GameRecordManagerRepository gameRecordManagerRepository;
    private final GameRecordAuthRepository gameRecordAuthRepository;

    private final String QUARTER_1ST_CODE = QuarterCode.FIRST.getCode();
    private final String QUARTER_2ND_CODE = QuarterCode.SECOND.getCode();
    private final String QUARTER_3RD_CODE = QuarterCode.THIRD.getCode();
    private final String QUARTER_4TH_CODE = QuarterCode.FOURTH.getCode();


    /**
     * 22.12.04
     * 쿼터별 팀기록 조회(단건)
     * @param quarterTeamRecords 쿼터별팀기록 DTO
     * @return 쿼터별팀기록 DTO
     * @author 강창기
     */
    public QuarterTeamRecords findQuarterTeamRecords(QuarterTeamRecords quarterTeamRecords) {
        if(ObjectUtils.isEmpty(quarterTeamRecords))
            throw new CustomException(Error.NO_PARAMETER);

        return quarterTeamRecordsRepository.find(quarterTeamRecords);
    }

    /**
     * 22.12.04
     * 쿼터별 선수기록 생성
     * @param quarterTeamRecords 쿼터별팀기록 DTO
     * @return QuarterPlayerRecordsSeq
     * @author 강창기
     */
    public Long createQuarterTeamRecords(QuarterTeamRecords quarterTeamRecords) {
        if(ObjectUtils.isEmpty(quarterTeamRecords))
            throw new CustomException(Error.NO_PARAMETER);

        return quarterTeamRecordsRepository.create(quarterTeamRecords);
    }

    /**
     * 22.12.04
     * 쿼터별 선수기록 수정
     * @param quarterTeamRecords 쿼터별팀기록 DTO
     * @author 강창기
     */
    public void modifyQuarterTeamRecords(QuarterTeamRecords quarterTeamRecords) {
        if(ObjectUtils.isEmpty(quarterTeamRecords))
            throw new CustomException(Error.NO_PARAMETER);

        quarterTeamRecordsRepository.modify(quarterTeamRecords);
    }

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
     * 22.12.03
     * 쿼터별 선수기록 생성
     * @param quarterPlayerRecords 쿼터별선수기록 DTO
     * @return QuarterPlayerRecordsSeq
     * @author 강창기
     */
    public Long createQuarterPlayerRecords(QuarterPlayerRecords quarterPlayerRecords) {
        if(ObjectUtils.isEmpty(quarterPlayerRecords))
            throw new CustomException(Error.NO_PARAMETER);

        return quarterPlayerRecordsRepository.create(quarterPlayerRecords);
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

            if(ObjectUtils.isEmpty(gameRepository.findGameBasicInfo(searchGameDTO.getGameSeq())))
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
        if(ObjectUtils.isEmpty(gameRepository.findGameBasicInfo(gameSeq)))
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
                || ObjectUtils.isEmpty(searchGameDTO.getQuarterCode()))
            throw new CustomException(Error.NO_PARAMETER);

        //쿼터코드; 01~04(1~4쿼터), 11(전반), 12(후반)
        if(ObjectUtils.isEmpty(gameRepository.findGameBasicInfo(searchGameDTO.getGameSeq())))
            throw new CustomException(Error.NOT_FOUND_GAME);  // 게임 정보가 존재하지 않습니다.

        HomeAwayTeamRecordDTO resultDVO = gameRecordManagerRepository.findHomeAwayTeamRecordsByQuarter(searchGameDTO);

        return resultDVO;
    }

    /**
     * 22.12.25
     * 전체쿼터의 홈·어웨이 팀기록조회
     * 홈 & 어웨이의 쿼터별 팀 합산기록을 조회한다.
     * @param searchGameDTO 게임조회용 DTO
     * @author 강창기
     */
    public Map<QuarterCode, QuarterAllTeamsRecordsDTO> getGameAllQuartersRecords(SearchGameDTO searchGameDTO)
    {
//        if(ObjectUtils.isEmpty(searchGameDTO.getGameSeq()))
//            throw new CustomException(Error.NO_PARAMETER);

        boolean hasNoGameInfo = ObjectUtils.isEmpty(gameRepository.findGameBasicInfo(searchGameDTO.getGameSeq()));
        if(hasNoGameInfo) {
            throw new CustomException(Error.NOT_FOUND_GAME);
        }

        List<QuarterTeamRecordsDTO> allQuarterRecords = gameRecordManagerRepository.findAllQuarterRecords(searchGameDTO);
        if (allQuarterRecords.isEmpty()) {
            return new HashMap<>();
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
        return allQuarterRecordsMap;
    }

    private QuarterTeamRecordsDTO filterTeamRecords (QuarterCode quarterCode, HomeAwayCode homeAwayCode, List<QuarterTeamRecordsDTO> allQuarterRecords)
    {
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
    public List<GameRecordDTO> searchMyTeamGames(GameCondDTO gc)
    {
        /**
         *  1. 소속팀을 기준으로 게임정보 조회 - List<GameRecordDTO>를 받아서 이후 for순회
         *  2. 조회된 게임정보목록을 순회
         *      - 참가팀 조회하여 GameRecordDTO안에 필드채우기
         *  3. 참가팀 조회시 쿼터별기록을 조회해서 GameJoinTeamRecord필드에 할당해주기
         **/
        // 게임참가팀 테이블에서 TEAM_SEQ를 조회
        // TODO homeAwayCode로 게임 목록을 조회하기
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

    /**
     * 2022.12.05
     * 쿼터 삭제
     * @author 이성주
     */
    public void deleteQuater(QuarterCodeDTO quarterCodeDTO){
        //TODO 어떤 처리를 할 필요가 없는건지?
        gameRecordManagerRepository.deleteQuarterPlayerRecords(quarterCodeDTO);
        gameRecordManagerRepository.deleteQuarterTeamRecords(quarterCodeDTO);
    }

    /**
     * 2022.01.04
     * 게임기록자 조회
     * @author 이성주
     */
    public List<GameAuthRecordersResponse> searchGameRecorders(GameAuthDTO gameAuthDTO){
        List<GameAuthRecordersResponse> gameAuthRecordersResponses = gameRecordAuthRepository.searchGameRecorders(gameAuthDTO);
        return gameAuthRecordersResponses;
    }

    /**
     * 2022.01.14
     * 게임기록자 저장
     * @author 이성주
     */
    public void saveAuthRecorder(GameRecordAuth gameRecordAuth){
        gameRecordAuthRepository.saveGameRecordAuth(gameRecordAuth);
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
        if(ObjectUtils.isEmpty(searchGameDTO))
            throw new CustomException(Error.NO_PARAMETER);

        if(ObjectUtils.isEmpty(searchGameDTO.getGameSeq()))
            throw new CustomException(Error.NO_PARAMETER);

        List<PlayerInfoDTO> resultDVOList = gameRecordManagerRepository.findTeamMembersByGameSeq(searchGameDTO);

        return resultDVOList;
    }
}