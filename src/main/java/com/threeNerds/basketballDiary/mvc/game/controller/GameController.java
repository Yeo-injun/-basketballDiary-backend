package com.threeNerds.basketballDiary.mvc.game.controller;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.Error;
import com.threeNerds.basketballDiary.mvc.dto.team.team.TeamDTO;
import com.threeNerds.basketballDiary.mvc.game.controller.dto.GameJoinPlayerRegistrationDTO;
import com.threeNerds.basketballDiary.mvc.game.domain.QuarterPlayerRecords;
import com.threeNerds.basketballDiary.mvc.game.domain.QuarterTeamRecords;
import com.threeNerds.basketballDiary.mvc.game.dto.*;
import com.threeNerds.basketballDiary.mvc.game.service.GameJoinManagerService;
import com.threeNerds.basketballDiary.mvc.game.service.GameRecordManagerService;
import com.threeNerds.basketballDiary.mvc.game.service.GameService;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.FindGameHomeAwayDTO;
import com.threeNerds.basketballDiary.mvc.service.LoginService;
import com.threeNerds.basketballDiary.mvc.service.UserService;
import com.threeNerds.basketballDiary.mvc.service.UserTeamManagerService;
import com.threeNerds.basketballDiary.session.SessionUser;
import com.threeNerds.basketballDiary.utils.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static com.threeNerds.basketballDiary.constant.HttpResponseConst.RESPONSE_CREATED;
import static com.threeNerds.basketballDiary.constant.HttpResponseConst.RESPONSE_OK;
import static com.threeNerds.basketballDiary.utils.SessionUtil.LOGIN_USER;

/**
 * ... 수행하는 Controller
 * @author 여인준
 *
 * issue and history
 * <pre>
 * 2022.10.16 최초생성 여인준
 * </pre>
 */

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/games")
public class GameController {

    private final LoginService loginService;
    private final UserService userService;
    private final UserTeamManagerService userTeamManagerService;

    private final GameService gameService;
    private final GameJoinManagerService gameJoinManagerService;
    private final GameRecordManagerService gameRecordManagerService;


    /**
     * API035 게임참가 선수등록하기
     * @result 특정쿼터의 선수별 기록조회
     */
    //@Auth(GRADE = USER) 
    @PostMapping("/{gameSeq}/gameJoinTeam/{gameJoinTeamSeq}/players")
    public ResponseEntity<?> registerGameJoinPlayers(
            @PathVariable(name = "gameSeq") Long gameSeq,
            @PathVariable(name = "gameJoinTeamSeq") Long gameJoinTeamSeq,
            @RequestBody List<GameJoinPlayerDTO> gameJoinPlayers
    ) {
        GameJoinPlayerRegistrationDTO playerRegistrationDTO = new GameJoinPlayerRegistrationDTO()
                .gameSeq(gameSeq)
                .gameJoinTeamSeq(gameJoinTeamSeq)
                .gameJoinPlayerDTOList(gameJoinPlayers);

        gameJoinManagerService.registerGameJoinPlayers(playerRegistrationDTO);
        return RESPONSE_CREATED;
    }


    /**
     * API038 쿼터 저장하기/수정하기
     * @param gameSeq 게임Seq
     * @param quarterCode 쿼터코드; 01~04(1~4쿼터), 11(전반), 12(후반)
     * @result 특정 경기의 쿼터 기록을 저장·수정한다.
     * @author 강창기
     */
    //@Auth(GRADE = USER) TODO
    @PutMapping("/{gameSeq}/quarters/{quarterCode}")
    public ResponseEntity<?> processQuarterRecords(
        @PathVariable(name = "gameSeq") String gameSeq,
        @PathVariable(name = "quarterCode") String quarterCode,
        @RequestBody QuarterCreationDTO quarterCreationDTO
    ) {
        log.debug("call test");
        if(ObjectUtils.isEmpty(gameSeq) || !StringUtils.hasText(gameSeq))
            throw new CustomException(Error.NO_PARAMETER);
        // TODO QuarterCode enum parameter값에 해당하는지 체크 필요 ...
        if(ObjectUtils.isEmpty(quarterCode))
            throw new CustomException(Error.NO_PARAMETER);
        if(ObjectUtils.isEmpty(quarterCreationDTO))
            throw new CustomException(Error.NO_PARAMETER);
        if(ObjectUtils.isEmpty(quarterCreationDTO.getHomeAwayTeamRecordDTO()))
            throw new CustomException(Error.NO_PARAMETER);
        if(CollectionUtils.isEmpty(quarterCreationDTO.getPlayerRecordDTOList()))
            throw new CustomException(Error.NO_PARAMETER);

        HomeAwayTeamRecordDTO homeAwayTeamRecordDTO = quarterCreationDTO.getHomeAwayTeamRecordDTO();
        List<PlayerRecordDTO> playerRecordDTOList = quarterCreationDTO.getPlayerRecordDTOList();

        // 쿼터별 선수기록 업데이트
        for (PlayerRecordDTO playerRecordDTO : playerRecordDTOList) {
            // QUARTER_PLAYER_RECORDS 업데이트
            QuarterPlayerRecords quarterPlayerRecords = QuarterPlayerRecords.builder()
                    .quarterPlayerRecordsSeq(playerRecordDTO.getQuarterPlayerRecordsSeq())
                    .build();

            // 기존 쿼터기록 조회
            quarterPlayerRecords = gameRecordManagerService.findQuarterPlayerRecords(quarterPlayerRecords);

            if(!ObjectUtils.isEmpty(quarterPlayerRecords)) {
                BeanUtils.copyProperties(playerRecordDTO, quarterPlayerRecords, CommonUtil.getNullPropertyNames(playerRecordDTO));
                gameRecordManagerService.modifyQuarterPlayerRecords(quarterPlayerRecords);
            } else {
                // 신규생성 필요
                quarterPlayerRecords = new QuarterPlayerRecords();
                BeanUtils.copyProperties(playerRecordDTO, quarterPlayerRecords);
                Long quarterPlayerRecordsSeq = gameRecordManagerService.createQuarterPlayerRecords(quarterPlayerRecords);
            }
        }
        
        // 쿼터별 팀기록 업데이트
        QuarterTeamRecords quarterTeamRecords = QuarterTeamRecords.builder()
                .quarterTeamRecordsSeq(homeAwayTeamRecordDTO.getQuarterTeamRecordsSeq())
                .build();

        quarterTeamRecords = gameRecordManagerService.findQuarterTeamRecords(quarterTeamRecords);
        if(!ObjectUtils.isEmpty(quarterTeamRecords)) {
            BeanUtils.copyProperties(homeAwayTeamRecordDTO, quarterTeamRecords, CommonUtil.getNullPropertyNames(homeAwayTeamRecordDTO));
            gameRecordManagerService.modifyQuarterTeamRecords(quarterTeamRecords);
        } else {
            quarterTeamRecords = new QuarterTeamRecords();
            BeanUtils.copyProperties(homeAwayTeamRecordDTO, quarterTeamRecords);
            Long quarterTeamRecordsSeq = gameRecordManagerService.createQuarterTeamRecords(quarterTeamRecords);
        }

        return ResponseEntity.ok(null);
    }

    /**
     * API043 게임쿼터별 선수기록조회
     * @param gameSeq 게임Seq
     * @param quarterCode 쿼터코드; 01~04(1~4쿼터), 11(전반), 12(후반)
     * @param homeAwayCode 홈·어웨이코드; 01(홈),02(어웨이)
     * @result 특정쿼터의 선수별 기록조회
     * @author 강창기
     */
    //@Auth(GRADE = USER) TODO
    @GetMapping("/{gameSeq}/quarters/{quarterCode}/players/homeAway/{homeAwayCode}")
    public ResponseEntity<?> searchPlayerRecordsByQuarter(
            // TODO  인준 : seq는 자료형이 String이 아니라 Long으로 관리하고 있음.
        @PathVariable(name = "gameSeq") String gameSeq,
        @PathVariable(name = "quarterCode") String quarterCode,
        @PathVariable(name = "homeAwayCode") String homeAwayCode
    ) {
        // TODO 설계구조상 pagination data를 받지 않음.
        // TODO 파라미터 값 지정하여 throw처리...
        if(ObjectUtils.isEmpty(gameSeq) || !StringUtils.hasText(gameSeq))
            throw new CustomException(Error.NO_PARAMETER);
        if(ObjectUtils.isEmpty(quarterCode) || !StringUtils.hasText(quarterCode))
            throw new CustomException(Error.NO_PARAMETER);
        if(ObjectUtils.isEmpty(homeAwayCode) || !StringUtils.hasText(homeAwayCode))
            throw new CustomException(Error.NO_PARAMETER);

        //if(quarterCode.contains()) TODO 쿼터코드에 해당하는 값인지 체크필요

        //if(!HomeAwayCode.HOME_TEAM.equals(homeAwayCode) && !HomeAwayCode.AWAY_TEAM.equals(homeAwayCode))
        //throw new CustomException(); TODO 홈어웨이코드에 해당하는 값인지 체크필요

        SearchGameDTO searchGameDTO = new SearchGameDTO()
                .gameSeq(Long.parseLong(gameSeq))
                .quarterCode(quarterCode)
                .homeAwayCode(homeAwayCode);

        List<PlayerRecordDTO> listPlayerRecordsByQuarter = gameRecordManagerService.getListPlayerRecordsByQuarter(searchGameDTO);

        return ResponseEntity.ok(listPlayerRecordsByQuarter);
    }

    /**
     * API048 경기 쿼터기록 조회
     * @param gameSeq 게임Seq
     * @param quarterCode 쿼터코드; 01~04(1~4쿼터), 11(전반), 12(후반)
     * @result 특정쿼터의 선수별 기록조회
     * @author 강창기
     */
    //@Auth(GRADE = USER) TODO
    @GetMapping("/{gameSeq}/quarterRecords/quaterCode/{quaterCode}")
    public ResponseEntity<?> searchGameRecordByQuarter(
            @PathVariable(name = "gameSeq") String gameSeq,
            @PathVariable(name = "quarterCode") String quarterCode
    ){
        // TODO 파라미터 값 지정하여 throw처리...
        if(ObjectUtils.isEmpty(gameSeq) || !StringUtils.hasText(gameSeq))
            throw new CustomException(Error.NO_PARAMETER);
        if(ObjectUtils.isEmpty(quarterCode) || !StringUtils.hasText(quarterCode))
            throw new CustomException(Error.NO_PARAMETER);

        //if(quarterCode.contains()) TODO 쿼터코드에 해당하는 값인지 체크필요

        SearchGameDTO searchGameDTO = new SearchGameDTO()
                .gameSeq(Long.parseLong(gameSeq))
                .quarterCode(quarterCode);

        return ResponseEntity.ok(null);
    }

    /**
     * API053 게임 생성
     * - 생성한 게임 정보를 반환
     */
    @PostMapping
    public ResponseEntity<?> createGame (
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser sessionUser,
            @RequestBody  GameCreationDTO gameCreationDTO
    ) {
        //  TODO 임시주석처리로 권한@ 처리 이후 살려야 하는 코드
//        Long userSeq = sessionUser.getUserSeq();
//        gameCreationDTO.userSeq(userSeq);
        gameCreationDTO.userSeq(3L);

        GameCreationDTO gc = gameService.createGame(gameCreationDTO);
        return ResponseEntity.ok(gc);
    }

    /**
     * API044 상대팀 목록 조회
     */
    @GetMapping("/opponents")
    public ResponseEntity<?> searchOpponents(
            @RequestParam(name = "sidoCode") String sidoCode,
            @RequestParam(name = "teamName") String teamName,
            @RequestParam(name = "leaderName") String leaderName
    ){

        //1. teamSeq 먼저 get(List 형식으로 return 받아야됨 : sidoCode,teamName,leaderName 중 null 값이 있고 동적으로 쿼리를 작성해야하기 때문)
        //2. [팀명,팀장이름,설립일,활동지역] 의 dto를 생성하고 return
        List<TeamDTO> teams = gameJoinManagerService.searchOpponents(sidoCode, teamName, leaderName);

        return ResponseEntity.ok(teams);
    }

    /**
     * API046 경기 상세정보 조회
     */
    @GetMapping("{gameSeq}/info")
    public ResponseEntity<?> findGameDetailRecords(
            @PathVariable(name = "gameSeq")Long gameSeq
    ){
        GameInfoDTO gameInfo = gameService.getGameInfo(gameSeq);
        return ResponseEntity.ok(gameInfo);
    }

    /**
     * API047 경기 참가팀 조회
     */
    @GetMapping("{gameSeq}/teams")
    public ResponseEntity<?> findGameHomeAwayInfo(
            @PathVariable(name = "gameSeq") Long gameSeq,
            @RequestParam(name = "homeAwayCode") String homeAwayCode
    ){
        List<FindGameHomeAwayDTO> gameHomeAwayInfo = gameJoinManagerService.findGameHomeAwayInfo(gameSeq, homeAwayCode);
        return ResponseEntity.ok(gameHomeAwayInfo);
    }

    /**
     * API051 게임 삭제
     */
    @DeleteMapping("/{gameSeq}")
    public ResponseEntity<?> deleteGame(
            @PathVariable(name = "gameSeq") Long gameSeq
    ){
        gameService.DeleteGame(gameSeq);
        return RESPONSE_OK;
    }

    /**
     * API061 경기참가선수 조회
     */
    @GetMapping("/{gameSeq}/players")
    public ResponseEntity<?> getMathPlayers(
            @PathVariable(name = "gameSeq") @Min(1) Long gameSeq,
            @RequestParam(name = "homeAwayCode") String homeAwayCode
    ){
        List<MatchPlayersInfoDTO> matchPlayersInfo = gameService.getMatchPlayersInfo(gameSeq, homeAwayCode);
        return ResponseEntity.ok(matchPlayersInfo);
    }

    /**
     * API062 게임참가팀 확정
     */
    @PostMapping("/{gameSeq}/gameJoinTeams")
    public ResponseEntity<?> confirmJoinTeam (
            @PathVariable(name = "gameSeq") Long gameSeq,
            @RequestBody  GameJoinTeamCreationDTO joinTeamCreationDTO
    ){
        /**
         * {
         *      gameTypeCode : ,    // 필수값
         *      homeTeamSeq : ,     // gameTypeCode가 03일경우 필수 아님
         *      awqyTeamSeq : ,     // gameTypeCode가 03일경우 필수 아님
         * }
         */
        joinTeamCreationDTO.gameSeq(gameSeq);

        gameJoinManagerService.confirmJoinTeam(joinTeamCreationDTO);
        // 게임참가팀이 확정되면
        // 게임에 참가하는 참가팀 정보를 리턴
        /**
         * {
         *      gameSeq : 1,
         *      homeTeam : {
         *          gameJoinTeamSeq :
         *          teamSeq :
         *      },
         *      awayTeam : {
         *          gameJoinTeamSeq :
         *          teamSeq :
         *      }
         */

        return RESPONSE_OK;
    }

}
