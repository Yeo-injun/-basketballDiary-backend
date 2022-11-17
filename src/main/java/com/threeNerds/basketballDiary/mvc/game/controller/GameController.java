package com.threeNerds.basketballDiary.mvc.game.controller;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.Error;
import com.threeNerds.basketballDiary.mvc.dto.team.team.TeamDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.*;
import com.threeNerds.basketballDiary.mvc.game.service.GameJoinManagerService;
import com.threeNerds.basketballDiary.mvc.game.service.GameRecordManagerService;
import com.threeNerds.basketballDiary.mvc.game.service.GameService;
import com.threeNerds.basketballDiary.mvc.service.LoginService;
import com.threeNerds.basketballDiary.mvc.service.UserService;
import com.threeNerds.basketballDiary.mvc.service.UserTeamManagerService;
import com.threeNerds.basketballDiary.session.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * API038 쿼터 저장하기/수정하기
     * @param gameSeq 게임Seq
     * @param quarterCode 쿼터코드; 01~04(1~4쿼터), 11(전반), 12(후반)
     * @result 특정 경기의 쿼터 기록을 저장한다.
     */
    //@Auth(GRADE = USER) TODO
    @PatchMapping("/{gameSeq}/quarters/{quarterCode}")
    public ResponseEntity<?> test(
        @PathVariable(name = "gameSeq") String gameSeq,
        @PathVariable(name = "quarterCode") String quarterCode,
        @RequestBody GameCreationDTO gameCreationDTO
    ) {
        

        return ResponseEntity.ok(null);
    }

    /**
     * API043 게임쿼터별 선수기록조회
     * @param gameSeq 게임Seq
     * @param quarterCode 쿼터코드; 01~04(1~4쿼터), 11(전반), 12(후반)
     * @param homeAwayCode 홈·어웨이코드; 01(홈),02(어웨이)
     * @result 특정쿼터의 선수별 기록조회
     */
    //@Auth(GRADE = USER) TODO
    @GetMapping("/{gameSeq}/quarters/{quarterCode}/players/homeAway/{homeAwayCode}")
    public ResponseEntity<?> searchPlayerRecordsByQuarter(
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
    public ResponseEntity<?> createGame(
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
     * /api/games/opponents?sidoCode={sidoCode}&teamName={teamName}&leaderName=${leaderName}
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
     * API046 게임기록 상세조회
     */
    @GetMapping("{gameSeq}/info")
    public ResponseEntity<?> findGameRecords(
            @PathVariable(name = "gameSeq")Long gameSeq
    ){
        GameInfoDTO gameInfo = gameService.getGameInfo(gameSeq);
        return ResponseEntity.ok(gameInfo);
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