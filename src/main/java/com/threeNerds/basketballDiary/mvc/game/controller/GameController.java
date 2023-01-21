package com.threeNerds.basketballDiary.mvc.game.controller;

import com.threeNerds.basketballDiary.constant.code.HomeAwayCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.Error;
import com.threeNerds.basketballDiary.interceptor.Auth;
import com.threeNerds.basketballDiary.mvc.game.controller.dto.GameAuthDTO;
import com.threeNerds.basketballDiary.mvc.game.controller.response.*;
import com.threeNerds.basketballDiary.mvc.game.domain.GameRecordAuth;
import com.threeNerds.basketballDiary.mvc.game.controller.dto.GameJoinPlayerRegistrationDTO;
import com.threeNerds.basketballDiary.mvc.game.controller.request.*;
import com.threeNerds.basketballDiary.mvc.game.domain.QuarterPlayerRecords;
import com.threeNerds.basketballDiary.mvc.game.domain.QuarterTeamRecords;
import com.threeNerds.basketballDiary.mvc.game.dto.*;
import com.threeNerds.basketballDiary.mvc.game.service.GameJoinManagerService;
import com.threeNerds.basketballDiary.mvc.game.service.GameRecordManagerService;
import com.threeNerds.basketballDiary.mvc.game.service.GameService;
import com.threeNerds.basketballDiary.session.SessionUser;
import com.threeNerds.basketballDiary.utils.CommonUtil;
import com.threeNerds.basketballDiary.utils.ValidateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

import static com.threeNerds.basketballDiary.constant.UserAuthConst.USER;
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

    private final GameService gameService;
    private final GameJoinManagerService gameJoinManagerService;
    private final GameRecordManagerService gameRecordManagerService;


    /**
     * API035 게임참가 선수등록하기
     * @result 특정쿼터의 선수별 기록조회
     * 22.12.15(목) @ReauestBody부분 Request클래스로 대체
     */
//    @Auth(GRADE = USER)
    @PostMapping("/{gameSeq}/gameJoinTeams/{gameJoinTeamSeq}/players")
    public ResponseEntity<?> registerGameJoinPlayers(
            @PathVariable(name = "gameSeq") Long gameSeq,
            @PathVariable(name = "gameJoinTeamSeq") Long gameJoinTeamSeq,
            @RequestBody RegisterGameJoinPlayersRequest reqBody
    ) {
        GameJoinPlayerRegistrationDTO playerRegistrationDTO = new GameJoinPlayerRegistrationDTO()
                .gameSeq(gameSeq)
                .gameJoinTeamSeq(gameJoinTeamSeq)
                .gameJoinPlayerDTOList(reqBody.getGameJoinPlayers());

        gameJoinManagerService.registerGameJoinPlayers(playerRegistrationDTO);
        return RESPONSE_CREATED;
    }


    /**
     * API038 쿼터 저장하기/수정하기
     * @param gameSeq     게임Seq
     * @param quarterCode 쿼터코드; 01~04(1~4쿼터), 11(전반), 12(후반)
     * @result 특정 경기의 쿼터 기록을 저장·수정한다.
     * @author 강창기
     */
    //@Auth(GRADE = USER) TODO
    // TODO 하나의 서비스로 통일하고(트랜잭션 이슈때문에), 
    // TODO 컨트롤러에서 호출하는 메소드를 서비스에서 구현하고 private메소드로 구현
    @PutMapping("/{gameSeq}/quarters/{quarterCode}")
    public ResponseEntity<?> processQuarterRecords(
            @PathVariable(name = "gameSeq") String gameSeq,
            @PathVariable(name = "quarterCode") String quarterCode,
            @RequestBody QuarterCreationDTO quarterCreationDTO
    ) {
        log.debug("call test");
        if (ObjectUtils.isEmpty(gameSeq) || !StringUtils.hasText(gameSeq))
            throw new CustomException(Error.NO_PARAMETER);
        // TODO QuarterCode enum parameter값에 해당하는지 체크 필요 ...
        if (ObjectUtils.isEmpty(quarterCode))
            throw new CustomException(Error.NO_PARAMETER);
        if (ObjectUtils.isEmpty(quarterCreationDTO))
            throw new CustomException(Error.NO_PARAMETER);
        if (ObjectUtils.isEmpty(quarterCreationDTO.getHomeAwayTeamRecordDTO()))
            throw new CustomException(Error.NO_PARAMETER);
        if (CollectionUtils.isEmpty(quarterCreationDTO.getPlayerRecordDTOList()))
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

            if (!ObjectUtils.isEmpty(quarterPlayerRecords)) {
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
        if (!ObjectUtils.isEmpty(quarterTeamRecords)) {
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
     * API040 게임엔트리 조회하기
     * @param gameSeq
     * @param gameJoinTeamSeq
     * @param quarterCode
     * @return GetGameEntryResponse
     * Spring RestTemplate에서는 GET 메소드의  RequestBody를 지원하지 않음..
     * 참고자료 : https://brunch.co.kr/@kd4/158
     */
//    @Auth(GRADE = USER)
    @GetMapping("/{gameSeq}/gameJoinTeams/{gameJoinTeamSeq}/quarters/{quarterCode}/entry")
    public ResponseEntity<?> getGameEntry (
            @PathVariable("gameSeq") Long gameSeq,
            @PathVariable("gameJoinTeamSeq") Long gameJoinTeamSeq,
            @PathVariable("quarterCode") String quarterCode
    ) {
        // TODO @Valid로 유효성 체크하기
        SearchEntryDTO searchDTO = new SearchEntryDTO()
                .gameJoinTeamSeq(gameJoinTeamSeq)
                .quarterCode(quarterCode);

        List<QuarterPlayerRecordDTO> playerList = gameJoinManagerService.getGameEntry(searchDTO);
        GetGameEntryResponse resBody = new GetGameEntryResponse().playerList(playerList);

        return ResponseEntity.ok(resBody);
    }


    /**
     * API041 게임쿼터 삭제
     * @param gameSeq
     * @param quaterCode
     * @return
     */
    @DeleteMapping("/{gameSeq}/quaters/{quaterCode}")
    public ResponseEntity<?> deleteQuarter(
            @PathVariable("gameSeq") Long gameSeq,
            @PathVariable("quaterCode") String quaterCode
    ){
        QuarterCodeDTO quarterCodeDTO = new QuarterCodeDTO()
                        .gameSeq(gameSeq)
                        .quaterCodeDTO(quaterCode);
        gameRecordManagerService.deleteQuater(quarterCodeDTO);
        return RESPONSE_OK;
    }

    /**
     * API043 게임쿼터별 선수기록조회
     * @param gameSeq 게임Seq
     * @param teamSeq 팀Seq
     * @param quarterCode 쿼터코드; 01~04(1~4쿼터), 11(전반), 12(후반)
     * @result 특정쿼터의 선수별 기록조회
     * @author 강창기
     */
    //@Auth(GRADE = USER) TODO
    @GetMapping("/{gameSeq}/teams/{teamSeq}/quarters/{quarterCode}/players")
    public ResponseEntity<?> searchPlayerRecordsByQuarter(
            // TODO  인준 : seq는 자료형이 String이 아니라 Long으로 관리하고 있음.
        @PathVariable(name = "gameSeq") String gameSeq,
        @PathVariable(name = "teamSeq") String teamSeq,
        @PathVariable(name = "quarterCode") String quarterCode
    ) {
        // TODO 설계구조상 pagination data를 받지 않음.
        // TODO 파라미터 값 지정하여 throw처리...
        if(ObjectUtils.isEmpty(gameSeq) || !StringUtils.hasText(gameSeq))
            throw new CustomException(Error.NO_PARAMETER);
        if(ObjectUtils.isEmpty(teamSeq) || !StringUtils.hasText(teamSeq))
            throw new CustomException(Error.NO_PARAMETER);
        if(ObjectUtils.isEmpty(quarterCode) || !StringUtils.hasText(quarterCode))
            throw new CustomException(Error.NO_PARAMETER);

        //if(quarterCode.contains()) TODO 쿼터코드에 해당하는 값인지 체크필요

        //if(!HomeAwayCode.HOME_TEAM.equals(homeAwayCode) && !HomeAwayCode.AWAY_TEAM.equals(homeAwayCode))
        //throw new CustomException(); TODO 홈어웨이코드에 해당하는 값인지 체크필요

        SearchGameDTO searchGameDTO = new SearchGameDTO()
                .gameSeq(Long.parseLong(gameSeq))
                .teamSeq(Long.parseLong(teamSeq))
                .quarterCode(quarterCode);

        List<PlayerRecordDTO> listPlayerRecordsByQuarter = gameRecordManagerService.getListPlayerRecordsByQuarter(searchGameDTO);

        return ResponseEntity.ok(listPlayerRecordsByQuarter);
    }

    /**
     * API048 경기 쿼터기록 조회
     * @param gameSeq 게임Seq
     * @param quarterCode 쿼터코드
     * @result 특정쿼터의 선수별 기록조회
     * @author 강창기
     */
    //@Auth(GRADE = USER) TODO
    @GetMapping("/{gameSeq}/quarters/{quarterCode}")
    public ResponseEntity<?> searchGameRecordByQuarter(
            @PathVariable(name = "gameSeq") String gameSeq,
            @PathVariable(name = "quarterCode") String quarterCode
    ){
        if(ObjectUtils.isEmpty(gameSeq) || !StringUtils.hasText(gameSeq))
            throw new CustomException(Error.NO_PARAMETER);

        if(ObjectUtils.isEmpty(quarterCode) || !StringUtils.hasText(quarterCode))
            throw new CustomException(Error.NO_PARAMETER);

        SearchGameDTO searchGameDTO = new SearchGameDTO()
                .gameSeq(Long.parseLong(gameSeq))
                .quarterCode(quarterCode);

        HomeAwayTeamRecordDTO homeAwayTeamRecordByQuarter = gameRecordManagerService.getHomeAwayTeamRecordByQuarter(searchGameDTO);

        return ResponseEntity.ok(homeAwayTeamRecordByQuarter);
    }

    /**
     * API050 경기 확정(경기 등록)
     * @author 이성주
     */
    @PostMapping("/{gameSeq}/confirmation")
    public ResponseEntity<?> confirmGame(
            @PathVariable("gameSeq") Long gameSeq
    ){
        gameService.confirmGame(gameSeq);
        return ResponseEntity.ok(null);
    }

    /**
     * API053 게임 생성
     * - 생성한 게임 정보를 반환
     * 22.12.15(목) @ReauestBody부분 Request클래스로 대체
     */
    @Auth(GRADE = USER)
    @PostMapping
    public ResponseEntity<?> createGame (
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser sessionUser,
            @RequestBody CreateGameRequest reqBody
    ) {
        GameCreationDTO gameCreationInfo = reqBody.getGameCreationInfo();

        Long userSeq = sessionUser.getUserSeq();
        gameCreationInfo.userSeq(userSeq);

        GameCreationDTO result = gameService.createGame(gameCreationInfo);
        return ResponseEntity.ok(result);
    }

    /**
     * API044 상대팀 목록 조회
     */
    @GetMapping("/opponents")
    public ResponseEntity<?> searchOpponents(
            @RequestParam(name = "sidoCode", required = false) String sidoCode,
            @RequestParam(name = "teamName", required = false) String teamName,
            @RequestParam(name = "leaderName", required = false) String leaderName
    ){
        SearchOppenentsDTO searchCond = new SearchOppenentsDTO()
                .sidoCode(sidoCode)
                .teamName(teamName)
                .leaderName(leaderName);
        List<GameOpponentDTO> opponents = gameJoinManagerService.searchOpponents(searchCond);
        SearchOpponentsResponse resBody = new SearchOpponentsResponse()
                                                .opponents(opponents);
        return ResponseEntity.ok(resBody);
    }

    /**
     * API046 경기 기초정보 조회
     */
    @GetMapping("{gameSeq}/info")
    public ResponseEntity<?> getGameBasicInfo(
            @PathVariable(name = "gameSeq")Long gameSeq
    ){
        GameInfoDTO gameInfo = gameService.getGameInfo(gameSeq);

        GetGameBasicInfoResponse resBody = new GetGameBasicInfoResponse()
                                                    .gameInfo(gameInfo);
        return ResponseEntity.ok(resBody);
    }

    /**
     * API047 경기 참가팀 조회
     */
    @GetMapping("{gameSeq}/teams")
    public ResponseEntity<?> getGameJoinTeamsInfo(
            @PathVariable(name = "gameSeq") Long gameSeq,
            @RequestParam(name = "homeAwayCode", required = false) String homeAwayCode
    ){
        SearchGameJoinTeamDTO searchGameHomeAwayDTO = new SearchGameJoinTeamDTO()
                                                            .gameSeq(gameSeq)
                                                            .homeAwayCode(homeAwayCode);

        Map<HomeAwayCode, GameJoinTeamInfoDTO> joinTeamsInfo = gameJoinManagerService.getGameJoinTeams(searchGameHomeAwayDTO);

        GetGameJoinTeamsResponse resBody = new GetGameJoinTeamsResponse()
                                                .homeTeamInfo(joinTeamsInfo.get(HomeAwayCode.HOME_TEAM))
                                                .awayTeamInfo(joinTeamsInfo.get(HomeAwayCode.AWAY_TEAM));
        return ResponseEntity.ok(resBody);
    }

    /**
     * API051 게임 삭제
     */
    @DeleteMapping("/{gameSeq}")
    public ResponseEntity<?> deleteGame(
            @PathVariable(name = "gameSeq") Long gameSeq
    ){
        gameService.deleteGame(gameSeq);
        return RESPONSE_OK;
    }

    /**
     * API055 게임기록자 조회
     */
    @GetMapping("/{gameSeq}/gameRecorders")
    public ResponseEntity<?> searchRecorder(
            @PathVariable("gameSeq") Long gameSeq
    ){
        GameAuthDTO gameAuthDTO = new GameAuthDTO()
                .gameSeq(gameSeq)
                .auth("02");
        List<GameAuthRecordersResponse> gameAuthRecordersResponses = gameRecordManagerService.searchGameRecorders(gameAuthDTO);
        return ResponseEntity.ok(gameAuthRecordersResponses);
    }

    /**
     * API056 게임기록자 저장
     */
    @PostMapping("/{gameSeq}/gameRecorder")
    public ResponseEntity<?> saveGameRecorder(
            @PathVariable("gameSeq") Long gameSeq,
            @RequestBody GameRecordAuth gameRecordAuth
    ){
        GameRecordAuth gameAuthRecorderDTO = GameRecordAuth
                                                    .getOnlyWriterAuth(gameSeq,gameRecordAuth.getTeamMemberSeq());

        gameRecordManagerService.saveAuthRecorder(gameAuthRecorderDTO);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /**
     * API060 쿼터 엔트리 정보 저장
     * 22.12.15(목) @ReauestBody부분 Request클래스로 대체
     */
    @PostMapping("/{gameSeq}/entry")
    public ResponseEntity<?> saveQuarterEntryInfo(
            @PathVariable(name = "gameSeq") Long gameSeq,
            @RequestBody SaveQuarterEntryInfoRequest reqBody
    ) {
        // TODO @Valid 어노테이션을 활용하여 제약사항 걸기
        Object[] pathVariables = { gameSeq };
        ValidateUtil.check(pathVariables);

        QuarterEntryInfoDTO qeiDTO = new QuarterEntryInfoDTO()
                                        .gameSeq(gameSeq)
                                        .gameJoinTeamSeq(reqBody.getGameJoinTeamSeq())
                                        .homeAwayCode(reqBody.getHomeAwayCode())
                                        .quarterCode(reqBody.getQuarterCode())
                                        .playerList(reqBody.getPlayerList());

        gameJoinManagerService.saveQuarterEntryInfo(qeiDTO);
        return RESPONSE_OK;
    }

    /**
     * API061 경기참가선수 조회
     */
    @GetMapping("/{gameSeq}/players")
    public ResponseEntity<?> getGameJoinPlayers(
            @PathVariable(name = "gameSeq") Long gameSeq,
            @RequestParam(name = "homeAwayCode", required = false) String homeAwayCode
    ) {

        SearchPlayersDTO searchDTO = new SearchPlayersDTO()
                                            .gameSeq(gameSeq)
                                            .homeAwayCode(homeAwayCode);

        Map<HomeAwayCode, GameJoinTeamDTO> teams = gameJoinManagerService.getGameJoinPlayers(searchDTO);

        GetGameJoinPlayersResponse resBody = new GetGameJoinPlayersResponse()
                .gameSeq(gameSeq)
                .homeTeam(teams.get(HomeAwayCode.HOME_TEAM))
                .awayTeam(teams.get(HomeAwayCode.AWAY_TEAM));

        return ResponseEntity.ok(resBody);
    }

    /**
     * API062 게임참가팀 확정
     * 22.12.15(목) @ReauestBody부분 Request클래스로 대체
     * 23.01.11(수) 누락된 로직 추가 - 게임기록상태코드 업데이트
     */
    @PostMapping("/{gameSeq}/gameJoinTeams")
    public ResponseEntity<?> confirmJoinTeam (
            @PathVariable(name = "gameSeq") Long gameSeq,
            @RequestBody ConfirmGameJoinTeamRequest reqBody
    ) {
        GameJoinTeamCreationDTO joinTeamCreation = new GameJoinTeamCreationDTO()
                                        .gameSeq(gameSeq)
                                        .gameTypeCode(reqBody.getGameTypeCode())
                                        .gameJoinTeamSeq(reqBody.getGameJoinTeamSeq())
                                        .opponentTeamSeq(reqBody.getOpponentTeamSeq());

        gameJoinManagerService.confirmJoinTeam(joinTeamCreation);
        return RESPONSE_OK;
    }

    /**
     * API063 게임전체쿼터 조회
     * 22.12.25(금)
     * @author 강창기
     */
    @GetMapping("/{gameSeq}/quarters")
    public ResponseEntity<?> getGameAllQuartersRecords (
            @PathVariable(name = "gameSeq") Long gameSeq
    ) {
        if(ObjectUtils.isEmpty(gameSeq)) {
            throw new CustomException(Error.NO_PARAMETER);
        }

        SearchGameDTO searchGameDTO = new SearchGameDTO()
                                             .gameSeq(gameSeq);

        List<HomeAwayTeamRecordDTO> homeAwayTeamRecordByQuarterList = gameRecordManagerService.getGameAllQuartersRecords(searchGameDTO);

        return ResponseEntity.ok(homeAwayTeamRecordByQuarterList);
    }
}
