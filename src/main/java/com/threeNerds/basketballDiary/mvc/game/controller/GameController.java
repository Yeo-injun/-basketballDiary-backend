package com.threeNerds.basketballDiary.mvc.game.controller;

import com.threeNerds.basketballDiary.auth.constant.AuthLevel;
import com.threeNerds.basketballDiary.auth.constant.AuthType;
import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import com.threeNerds.basketballDiary.http.ResponseJsonBody;
import com.threeNerds.basketballDiary.auth.Auth;
import com.threeNerds.basketballDiary.mvc.game.controller.request.SaveQuarterRecordRequest;
import com.threeNerds.basketballDiary.mvc.game.dto.confirmGameJoinTeam.request.ConfirmGameJoinTeamRequest;
import com.threeNerds.basketballDiary.mvc.game.controller.request.CreateGameRequest;
import com.threeNerds.basketballDiary.mvc.game.controller.request.RegisterGameJoinPlayersRequest;
import com.threeNerds.basketballDiary.mvc.game.controller.request.SaveQuarterEntryInfoRequest;
import com.threeNerds.basketballDiary.mvc.game.controller.response.*;
import com.threeNerds.basketballDiary.mvc.game.dto.*;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameAllQuartersRecords.GetGameAllQuartersRecordsResponse;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameEntry.request.GetGameEntryRequest;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameEntry.response.GetGameEntryResponse;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameJoinPlayerRecordsByQuarter.request.GetGameJoinPlayerRecordsByQuarterRequest;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameJoinPlayers.request.GetGameJoinPlayersRequest;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameJoinPlayers.response.GetGameJoinPlayersResponse;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameQuarterRecords.request.GetGameQuarterRecordsRequest;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameQuarterRecords.response.GetGameQuarterRecordsResponse;
import com.threeNerds.basketballDiary.mvc.game.dto.saveGameRecorder.request.SaveGameRecordersRequest;
import com.threeNerds.basketballDiary.mvc.game.service.GameAuthService;
import com.threeNerds.basketballDiary.mvc.game.service.GameJoinManagerService;
import com.threeNerds.basketballDiary.mvc.game.service.GameRecordManagerService;
import com.threeNerds.basketballDiary.mvc.game.service.GameService;
import com.threeNerds.basketballDiary.mvc.game.service.dto.*;
import com.threeNerds.basketballDiary.session.SessionUser;
import com.threeNerds.basketballDiary.swagger.docs.game.ApiDocs035;
import com.threeNerds.basketballDiary.swagger.docs.game.ApiDocs038;
import com.threeNerds.basketballDiary.swagger.docs.game.ApiDocs040;
import com.threeNerds.basketballDiary.swagger.docs.game.ApiDocs044;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;

import static com.threeNerds.basketballDiary.constant.HttpResponseConst.RESPONSE_OK;
import static com.threeNerds.basketballDiary.session.util.SessionUtil.LOGIN_USER;

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
@Tag(
    name        = "게임컨트롤러",
    description = "Game 도메인과 관련된 서비스를 수행하는 Controller. Game도메인의 생성, 조회, 수정, 삭제 등"
)
/** swagger 관련 참고 자료 : https://devocean.sk.com/experts/techBoardDetail.do?ID=164919 */
public class GameController {

    /**
     * TODO Service 경계 재설계
     * - 기능 등장인물 정리
     * - 등장인물에 따른 역할과 책임 구분
     *  >> 게임정보
     *  >> 게임에 참가한 팀
     *  >> 게임에 참가한 선수
     *  >> 게임을 뛰고 있는 선수
     *  >> 팀과 선수들의 기록
     *  >> 게임은 여러개의 쿼터로 이뤄진다.
     *      >> 게임참가팀은 경기에 의존적이고
     *      >> 게임참가선수는 경기와 경기참가팀에 의존적이다.
     *      >> 경기기록은 경기, 팀, 선수에 의존적이다.
     *
     *  >> 경기기록을 입력하기 위한 전체 업무 흐름
     *      1. 경기를 생성한다.
     *          - 경기유형을 선택한다.
     *          - 유형에 따라 상대 팀을 선정한다.
     *      + 상대팀이 존재할 경우 상대팀의 승낙이 필요하다. ( 추가해야 하는 기능 )
     *      2. 경기에 참여할 선수들을 선택/입력한다. ( 경기참가선수 등록 )
     *      3. 쿼터 정보를 생성한다.
     *      -----------------------------------------
     *      경기를 기록한다.
     *      1. 경기에 뛸 선수들을 선택/수정한다. ( 경기기록입력 선수 등록 )
     *      2. 경기기록을 팀별, 선수별로 입력한다.
     *      3. 입력중인 경기기록을 조회한다.
     */
    private final GameService gameService;
    private final GameJoinManagerService gameJoinManagerService;
    private final GameRecordManagerService gameRecordManagerService;
    private final GameAuthService gameAuthService;


    /**
     * API035 경기참가선수 등록하기
     * 22.12.15(목) @ReauestBody부분 Request클래스로 대체
     */
    @ApiDocs035
    @Auth
    @PostMapping("/{gameSeq}/homeAwayCode/{homeAwayCode}/players")
    public ResponseEntity<URI> registerGameJoinPlayers(
            @PathVariable(name = "gameSeq") Long gameSeq,
            @PathVariable(name = "homeAwayCode") String homeAwayCode,
            @RequestBody @Valid RegisterGameJoinPlayersRequest request
    ) {
        gameJoinManagerService.registerGameJoinPlayers( request.toCommand( gameSeq, homeAwayCode ) );
        /**--------------------------------------------------------------------------------------
         * API061 게임참가선수 조회 URL을 리턴.
         * cf. created 상태코드는 return시 Location Header속성에 생성된 자원을 조회할 수 있는 URL를 표기함.
         **--------------------------------------------------------------------------------------*/
        URI createdURL = URI.create( "/api/games/" + gameSeq + "/players?homeAwayCode=" + homeAwayCode );
        return ResponseEntity.created( createdURL ).build();
    }


    /**
     * API038 경기 쿼터기록 수정하기
     * @param gameSeq     게임Seq
     * @param quarterCode 쿼터코드; 01~04(1~4쿼터), 11(전반), 12(후반)
     * @author 강창기
     */
    @ApiDocs038
    @Auth
    @PutMapping("/{gameSeq}/quarters/{quarterCode}")
    public ResponseEntity<Void> saveQuarterRecord(
            @PathVariable(name = "gameSeq") Long gameSeq,
            @PathVariable(name = "quarterCode") String quarterCode,
            @RequestBody @Valid SaveQuarterRecordRequest request
    ) {
        gameRecordManagerService.saveQuarterRecord( request.toCommand( gameSeq, quarterCode ) );
        return ResponseEntity.ok().build();
    }

    /**
     * API040 경기 엔트리 조회하기
     * Spring RestTemplate에서는 GET 메소드의  RequestBody를 지원하지 않음..
     * 참고자료 : https://brunch.co.kr/@kd4/158
     * 23.02.19(일) 인준 - API url 수정 (gameJoinTeamSeq를 화면에서 계속 가지고 있는 것이 번거롭기 때문)
     */
    // TODO Query 패턴 적용 / Service의 Request-Response클래스참조 걷어내기
    @ApiDocs040
    @Auth
    @GetMapping("/{gameSeq}/quarters/{quarterCode}/entry")
    public ResponseEntity<?> getGameEntry (
            @PathVariable("gameSeq") Long gameSeq,
            @PathVariable("quarterCode") String quarterCode,
            @RequestParam(name = "homeAwayCode", required = false) String homeAwayCode
    ) {
        GetGameEntryRequest request = new GetGameEntryRequest( gameSeq, quarterCode, homeAwayCode );
        GetGameEntryResponse resBody = gameJoinManagerService.getGameEntry(request);
        return ResponseEntity.ok(resBody);
    }


    /**
     * API064 경기 쿼터 기초정보 생성
     * @since 23.03.10(금)
     * @author 여인준
     */
    @Auth( type = AuthType.GAME_RECORD, level = AuthLevel.GAME_RECORDER )
    @PostMapping("/{gameSeq}/quarters/{quarterCode}")
    public ResponseEntity< Void > createGameQuarterBasicInfo (
            @PathVariable(name = "gameSeq") Long gameSeq,
            @PathVariable(name = "quarterCode") String quarterCode
    ) {
        gameRecordManagerService.createGameQuarterBasicInfo(
            GameQuarterCommand.builder()
                    .gameSeq(       gameSeq )
                    .quarterCode(   quarterCode )
                    .build()
        );
        return ResponseEntity.ok().build();
    }


    /**
     * API041 경기 쿼터정보 삭제
     * @since 23.03.10(금)
     * @author 여인준
     **/
    @Auth( type = AuthType.GAME_RECORD, level = AuthLevel.GAME_CREATOR )
    @DeleteMapping("/{gameSeq}/quarters/{quarterCode}")
    public ResponseEntity<Void> deleteGameQuarter(
            @PathVariable("gameSeq") Long gameSeq,
            @PathVariable("quarterCode") String quarterCode
    ) {
        gameRecordManagerService.deleteGameQuarter(
            GameQuarterCommand.builder()
                .gameSeq(       gameSeq )
                .quarterCode(   quarterCode )
                .build()
        );
        return ResponseEntity.ok().build();
    }

    /**
     * API043 참가선수 쿼터기록조회
     * @param gameSeq 게임Seq
     * @param quarterCode 쿼터코드; 01~04(1~4쿼터), 11(전반), 12(후반)
     * @result 특정쿼터의 선수별 기록조회
     * @author 강창기
     */
    // TODO Command 패턴 적용 / Service의 Request-Response클래스참조 걷어내기
    @Auth
    @GetMapping("/{gameSeq}/quarters/{quarterCode}/players")
    public ResponseEntity<?> getGameJoinPlayerRecordsByQuarter(
        @PathVariable(name = "gameSeq") Long gameSeq,
        @PathVariable(name = "quarterCode") String quarterCode,
        @RequestParam(name = "homeAwayCode", required = false) String homeAwayCode
    ) {
        // TODO 설계구조상 pagination data를 받지 않음.

        GetGameJoinPlayerRecordsByQuarterRequest request = new GetGameJoinPlayerRecordsByQuarterRequest()
                                                        .gameSeq(gameSeq)
                                                        .quarterCode(quarterCode)
                                                        .homeAwayCode(homeAwayCode);

        ResponseJsonBody response = gameRecordManagerService.getGameJoinPlayerRecordsByQuarter( request );

        return ResponseEntity.ok( response );
    }

    /**
     * API044 상대팀 목록 조회
     */
    // TODO Command 패턴 적용 / Service의 Request-Response클래스참조 걷어내기
    // 페이징 처리하기
    @ApiDocs044
    @Auth
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
    @Auth
    @GetMapping("{gameSeq}/info")
    public ResponseEntity<GetGameBasicInfoResponse> getGameBasicInfo(
            @PathVariable(name = "gameSeq") Long gameSeq
    ) {
        GameDetailDTO gameDetail = gameService.getGameDetailInfo( gameSeq );
        return ResponseEntity.ok( new GetGameBasicInfoResponse( gameDetail ) );
    }

    /**
     * API047 경기 참가팀 조회
     */
    // TODO Command 패턴 적용 / Service의 Request-Response클래스참조 걷어내기
    @Auth
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
     * API048 경기 쿼터기록 조회
     * @param gameSeq 게임Seq
     * @param quarterCode 쿼터코드
     * @result 특정쿼터의 선수별 기록조회
     * @author 강창기
     */
    // TODO Query 패턴 적용 / Service의 Request-Response클래스참조 걷어내기

    @Auth
    @GetMapping("/{gameSeq}/quarters/{quarterCode}")
    public ResponseEntity<?> getGameQuarterRecords(
            @PathVariable(name = "gameSeq") Long gameSeq,
            @PathVariable(name = "quarterCode") String quarterCode
    ) {
        GetGameQuarterRecordsRequest reqBody = new GetGameQuarterRecordsRequest()
                .gameSeq(gameSeq)
                .quarterCode(quarterCode);

        GetGameQuarterRecordsResponse resBody = gameRecordManagerService.getGameQuarterRecords(reqBody);

        return ResponseEntity.ok(resBody);
    }


    /**
     * API053 게임 생성
     * - 생성한 게임 정보를 반환
     * 22.12.15(목) @ReauestBody부분 Request클래스로 대체
     * TODO 여러개의 분할된 서비스를 하나의 트랜잭션으로 묶을 수 있도록 ServiceTransactionBroker를 만들어서 관리하기
     * TODO cf. 현재는 개별 서비스가 독립된 트랜잭션...
     */
    @Auth( type = AuthType.GAME_RECORD, level = AuthLevel.GAME_CREATOR )
    @PostMapping
    public ResponseEntity<CreateGameResponse> createGame (
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser sessionUser,
            @RequestBody CreateGameRequest request
    ) {
        Long userSeq            = sessionUser.getUserSeq();
        Long newGameSeq         = gameService.createGame( request.toCommand( userSeq ) );
        Long gameJoinPlayerSeq  = gameJoinManagerService.createGameJoin(
                                      GameJoinCommand.builder()
                                          .gameSeq( newGameSeq )
                                          .teamSeq( request.getTeamSeq() )
                                          .userSeq( userSeq )
                                          .build()
                                  );
        gameAuthService.createCreatorAuth(
            GameAuthCommand.builder()
                .gameSeq( newGameSeq )
                .userSeq( userSeq )
                .gameJoinPlayerSeq( gameJoinPlayerSeq )
                .build()
        );

        sessionUser.addGameCreatorAuth( newGameSeq );
        return ResponseEntity.ok( new CreateGameResponse( newGameSeq ) );
    }

    /**
     * API050 경기 확정(경기 등록)
     * @author 이성주
     */
    @Auth( type = AuthType.GAME_RECORD, level = AuthLevel.GAME_CREATOR )
    @PostMapping("/{gameSeq}/confirmation")
    public ResponseEntity<?> confirmGame(
            @PathVariable("gameSeq") Long gameSeq
    ) {
        gameService.confirmGame( gameSeq );
        return RESPONSE_OK;
    }

    /**
     * API051 경기 삭제
     */
    // TODO 데이터 삭제 로직 Service에 추가
    @Auth( type = AuthType.GAME_RECORD, level = AuthLevel.GAME_CREATOR )
    @DeleteMapping("/{gameSeq}")
    public ResponseEntity<?> deleteGame(
            @PathVariable(name = "gameSeq") Long gameSeq
    ) {
        gameService.deleteGame( gameSeq );
        return RESPONSE_OK;
    }

    /**
     * API055 경기기록원 조회
     */
    @Auth( type = AuthType.GAME_RECORD, level = AuthLevel.GAME_RECORDER )
    @GetMapping("/{gameSeq}/recorders")
    public ResponseEntity<?> getGameRecorders(
            @PathVariable("gameSeq") Long gameSeq
    ) {
        List<GameRecorderDTO> gameRecorders = gameAuthService.getGameRecorders(
                                                GameRecorderQuery.builder()
                                                    .gameSeq( gameSeq )
                                                    .build()
                                            );
        return ResponseEntity.ok( new GetGameRecordersResponse( gameRecorders ) );
    }

    /**
     * API056 경기기록원 목록 저장
     * @since 24.04.21 (일)
     * @author injun
     */
    @Auth( type = AuthType.GAME_RECORD, level = AuthLevel.GAME_CREATOR )
    @PostMapping("/{gameSeq}/recorders")
    public ResponseEntity<?> saveGameRecorders(
            @PathVariable("gameSeq") Long gameSeq,
            @RequestBody @Valid SaveGameRecordersRequest request
    ) {
        gameAuthService.saveGameRecorders( request.toCommand( gameSeq ) );
        return RESPONSE_OK;
    }

    /**
     * API057 경기기록원 후보 조회 ( TODO API명칭 변경 사항 반영 요망 )
     * @since 24.04.21 (일)
     * @author injun
     *  - 경기기록 권한을 부여할 수 있는 후보군 조회
     */
    @Auth( type = AuthType.GAME_RECORD, level = AuthLevel.GAME_RECORDER )
    @GetMapping("/{gameSeq}/recorders/candidates")
    public ResponseEntity<?> getGameRecorderCandidates(
            @PathVariable(name = "gameSeq") Long gameSeq,
            @RequestParam(name = "homeAwayCode", required = false) String homeAwayCode
    ) {
        List<GameRecorderCandidateDTO> candidates = gameAuthService.getGameRecorderCandidates(
                                                        GameRecorderCandidatesQuery.builder()
                                                            .gameSeq( gameSeq )
                                                            .homeAwayCode( homeAwayCode )
                                                            .build()
                                                    );
        return ResponseEntity.ok( new GetGameRecorderCandidatesResponse( candidates ) );
    }

    /**
     * API060 쿼터 엔트리 정보 저장
     * 22.12.15(목) @ReauestBody부분 Request클래스로 대체
     */
    @Auth( type = AuthType.GAME_RECORD, level = AuthLevel.GAME_RECORDER )
    @PostMapping("/{gameSeq}/entry")
    public ResponseEntity<?> saveQuarterEntryInfo(
            @PathVariable(name = "gameSeq") Long gameSeq,
            @RequestBody @Valid SaveQuarterEntryInfoRequest reqBody
    ) {
        // TODO Command 패턴으로 변경하기
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
    // TODO Query 패턴 적용 / Service의 Request-Response클래스참조 걷어내기
    @Auth
    @GetMapping("/{gameSeq}/players")
    public ResponseEntity<?> getGameJoinPlayers(
            @PathVariable(name = "gameSeq") Long gameSeq,
            @RequestParam(name = "homeAwayCode", required = false) String homeAwayCode
    ) {
        GetGameJoinPlayersRequest request = new GetGameJoinPlayersRequest()
                                            .gameSeq(gameSeq)
                                            .homeAwayCode(homeAwayCode);

        GetGameJoinPlayersResponse resBody = gameJoinManagerService.getGameJoinPlayers(request);

        return ResponseEntity.ok(resBody);
    }

    /**
     * API062 게임참가팀 확정
     * 22.12.15(목) @ReauestBody부분 Request클래스로 대체
     * 23.01.11(수) 누락된 로직 추가 - 게임기록상태코드 업데이트
     */
    // TODO Command 패턴 적용
    @Auth( type = AuthType.GAME_RECORD, level = AuthLevel.GAME_CREATOR )
    @PostMapping("/{gameSeq}/gameJoinTeams")
    public ResponseEntity<?> confirmGameJoinTeam (
            @PathVariable(name = "gameSeq") Long gameSeq,
            @RequestBody @Valid ConfirmGameJoinTeamRequest reqBody
    ) {
        GameJoinTeamCreationDTO joinTeamCreation = new GameJoinTeamCreationDTO()
                                        .gameSeq(gameSeq)
                                        .gameTypeCode(reqBody.getGameTypeCode())
                                        .opponentTeamSeq(reqBody.getOpponentTeamSeq());

        gameJoinManagerService.confirmJoinTeam(joinTeamCreation);
        return RESPONSE_OK;
    }

    /**
     * API063 게임전체쿼터 조회
     * @since 22.12.25(금)
     * @author 강창기
     * 23.01.25(수) 여인준 - API Body 수정
     */
    // TODO Query 패턴 적용 / Service의 Request-Response클래스참조 걷어내기
    // TODO 난이도 상
    @Auth
    @GetMapping("/{gameSeq}/quarters")
    public ResponseEntity<?> getGameAllQuartersRecords (
            @PathVariable(name = "gameSeq") Long gameSeq
    ) {
        SearchGameDTO searchGameDTO = new SearchGameDTO()
                                             .gameSeq(gameSeq);

        GetGameAllQuartersRecordsResponse resBody = gameRecordManagerService.getGameAllQuartersRecords(searchGameDTO);
        return ResponseEntity.ok(resBody);
    }

}
