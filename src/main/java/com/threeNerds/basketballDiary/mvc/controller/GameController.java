package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.mvc.dto.user.CmnUserDTO;
import com.threeNerds.basketballDiary.mvc.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.threeNerds.basketballDiary.constant.HttpResponseConst.RESPONSE_OK;

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
    /**
     * API044 상대팀 목록 조회
     * /api/games/opponents?sidoCode={sidoCode}&teamName={teamName}&leaderName=${leaderName}
     */
    @PostMapping("/opponents")
    public ResponseEntity<?> searchOpponents (
            @RequestBody CmnUserDTO cmnUserDTO
    ) {
        return RESPONSE_OK;
    }

    /**
     * API051 게임 삭제
     */

    /**
     * API053 게임 생성
     */

    /**
     * API062 게임참가팀확정
     */

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

        return RESPONSE_OK;
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
    public ResponseEntity<?> fixedJoinTeam(
            @PathVariable(name = "gameSeq") Long gaemSeq
    ){
        //1. GameJoinTeam 테이블에 홈/어웨이 팀 Insert
        return RESPONSE_OK;
    }

}
