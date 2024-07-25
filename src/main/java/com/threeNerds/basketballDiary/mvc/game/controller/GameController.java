package com.threeNerds.basketballDiary.mvc.game.controller;

import com.threeNerds.basketballDiary.auth.constant.AuthLevel;
import com.threeNerds.basketballDiary.auth.constant.AuthType;
import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import com.threeNerds.basketballDiary.auth.Auth;
import com.threeNerds.basketballDiary.mvc.game.controller.docs.*;
import com.threeNerds.basketballDiary.mvc.game.controller.request.*;
import com.threeNerds.basketballDiary.mvc.game.controller.response.*;
import com.threeNerds.basketballDiary.mvc.game.service.GameAuthService;
import com.threeNerds.basketballDiary.mvc.game.service.GameJoinManagerService;
import com.threeNerds.basketballDiary.mvc.game.service.GameRecordManagerService;
import com.threeNerds.basketballDiary.mvc.game.service.GameService;
import com.threeNerds.basketballDiary.mvc.game.service.dto.*;
import com.threeNerds.basketballDiary.session.SessionUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;


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
     * API067 경기참가선수 추가 ( 단건 )
     * @since 23.05.06(월)
     * @author 여인준
     */
    @ApiDocs067
    @Auth
    @PostMapping("/{gameSeq}/homeAwayCode/{homeAwayCode}/player")
    public ResponseEntity< URI > addGameJoinPlayer(
            @PathVariable(name = "gameSeq") Long gameSeq,
            @PathVariable(name = "homeAwayCode") String homeAwayCode,
            @RequestBody @Valid AddGameJoinPlayerRequest request
    ) {
        gameJoinManagerService.addGameJoinPlayer( request.toCommand( gameSeq, homeAwayCode ) );
        /**--------------------------------------------------------------------------------------
         * API061 경기참가선수 조회 URL을 리턴.
         * cf. created 상태코드는 return시 Location Header속성에 생성된 자원을 조회할 수 있는 URL를 표기함.
         **--------------------------------------------------------------------------------------*/
        URI createdURL = URI.create( "/api/games/" + gameSeq + "/players?homeAwayCode=" + homeAwayCode );
        return ResponseEntity.created( createdURL ).build();
    }

    /**
     * API068 경기참가선수 삭제 ( 단건 )
     * @since 23.05.06(월)
     * @author 여인준
     */
    @ApiDocs068
    @Auth( type = AuthType.GAME_RECORD, level = AuthLevel.GAME_RECORDER )
    @DeleteMapping("/{gameSeq}/homeAwayCode/{homeAwayCode}/players/{gameJoinPlayerSeq}")
    public ResponseEntity< Void > deleteGameJoinPlayers(
            @PathVariable(name = "gameSeq" ) Long gameSeq,
            @PathVariable(name = "homeAwayCode" ) String homeAwayCode,
            @PathVariable(name = "gameJoinPlayerSeq" ) Long gameJoinPlayerSeq
    ) {
        gameJoinManagerService.deleteGameJoinPlayer( GameJoinPlayerCommand.builder()
                .gameSeq( gameSeq )
                .homeAwayCode( homeAwayCode )
                .gameJoinPlayerSeq( gameJoinPlayerSeq )
                .build()
        );
        return ResponseEntity.ok().build();
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
    public ResponseEntity< Void > saveQuarterRecord(
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
    @ApiDocs040
    @Auth
    @GetMapping("/{gameSeq}/quarters/{quarterCode}/entry")
    public ResponseEntity<?> getGameEntry (
            @PathVariable("gameSeq") Long gameSeq,
            @PathVariable("quarterCode") String quarterCode,
            @RequestParam(name = "homeAwayCode", required = false) String homeAwayCode
    ) {
        GameEntryQuery.Result result = gameJoinManagerService.getGameEntry(
                                            GameEntryQuery.builder()
                                                  .gameSeq( gameSeq )
                                                  .quarterCode( quarterCode )
                                                  .homeAwayCode( homeAwayCode )
                                                  .build()
                                       );
        return ResponseEntity.ok( new GetGameEntryResponse( result ) );
    }


    /**
     * API064 경기 쿼터 기초정보 생성
     * @since 23.03.10(금)
     * @author 여인준
     */
    @ApiDocs064
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
    @ApiDocs041
    @Auth( type = AuthType.GAME_RECORD, level = AuthLevel.GAME_CREATOR )
    @DeleteMapping("/{gameSeq}/quarters/{quarterCode}")
    public ResponseEntity< Void > deleteGameQuarter(
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
     * API043 쿼터별 경기참가선수들의 경기기록 조회
     * @since 23.03.10(금)
     * @author 강창기
     */
    @ApiDocs043
    @Auth
    @GetMapping("/{gameSeq}/quarters/{quarterCode}/players")
    public ResponseEntity<?> getGameJoinPlayerQuarterRecords(
        @PathVariable(name = "gameSeq") Long gameSeq,
        @PathVariable(name = "quarterCode") String quarterCode,
        @RequestParam(name = "homeAwayCode", required = false) String homeAwayCode
    ) {
        GameJoinPlayerRecordQuery.Result result = gameRecordManagerService.getGameJoinPlayerQuarterRecords(
            GameJoinPlayerRecordQuery.builder()
                .gameSeq(       gameSeq )
                .quarterCode(   quarterCode )
                .homeAwayCode(  homeAwayCode )
                .build()
        );
        return ResponseEntity.ok( new GetGameJoinPlayerQuarterRecordsResponse(
                gameSeq, quarterCode,
                result.getHomeTeamPlayers(),
                result.getAwayTeamPlayers()
        ) );
    }

    /**
     * API044 상대팀 목록 조회
     */
    @ApiDocs044
    @Auth
    @GetMapping("/opponents")
    public ResponseEntity<?> searchOpponents(
            @RequestParam(name = "sidoCode", required = false) String sidoCode,
            @RequestParam(name = "teamName", required = false) String teamName,
            @RequestParam(name = "leaderName", required = false) String leaderName,
            @RequestParam(name = "pageNo", defaultValue = "0" ) Integer pageNo
    ) {
        OppenentTeamQuery.Result queryResult = gameJoinManagerService.searchOpponents(
                OppenentTeamQuery.builder()
                    .sidoCode(      sidoCode )
                    .teamName(      teamName )
                    .leaderName(    leaderName )
                    .pageNo(        pageNo )
                    .build()
        );
        return ResponseEntity.ok( new SearchOpponentsResponse( queryResult ) );
    }

    /**
     * API046 경기 기초 정보 조회
     */
    @ApiDocs046
    @Auth
    @GetMapping("{gameSeq}/info")
    public ResponseEntity< GetGameBasicInfoResponse > getGameBasicInfo(
            @PathVariable(name = "gameSeq") Long gameSeq
    ) {
        GameQuery.Result result = gameService.getGameBasicInfo(
                GameQuery.builder()
                         .gameSeq( gameSeq )
                         .build()
        );
        return ResponseEntity.ok( new GetGameBasicInfoResponse( result ) );
    }

    /**
     * API047 경기 참가팀 조회
     */
    @ApiDocs047
    @Auth
    @GetMapping("{gameSeq}/teams")
    public ResponseEntity<?> getGameJoinTeamsInfo(
            @PathVariable(name = "gameSeq") Long gameSeq,
            @RequestParam(name = "homeAwayCode", required = false) String homeAwayCode
    ) {
        GameJoinTeamQuery.Result result = gameJoinManagerService.getGameJoinTeams(
                                                        GameJoinTeamQuery.builder()
                                                                .gameSeq( gameSeq )
                                                                .homeAwayCode( homeAwayCode )
                                                                .build()
                                                    );
        return ResponseEntity.ok( new GetGameJoinTeamsResponse( result ) );
    }

    /**
     * API048 경기 쿼터기록 조회
     * @param gameSeq 게임Seq
     * @param quarterCode 쿼터코드
     * @result 특정쿼터의 선수별 기록조회
     * @author 강창기
     */
    @ApiDocs048
    @Auth
    @GetMapping("/{gameSeq}/quarters/{quarterCode}")
    public ResponseEntity<?> getGameQuarterRecords(
            @PathVariable(name = "gameSeq") Long gameSeq,
            @PathVariable(name = "quarterCode") String quarterCode
    ) {
        GameQuarterQuery.Result result = gameRecordManagerService.getGameQuarterRecords(
                GameQuarterQuery.builder()
                    .gameSeq( gameSeq )
                    .quarterCode( quarterCode )
                    .build()
        );
        return ResponseEntity.ok( new GetGameQuarterRecordsResponse( result ) );
    }


    /**
     * API053 경기 생성
     * - 생성한 게임 정보를 반환
     * 22.12.15(목) @ReauestBody부분 Request클래스로 대체
     * TODO 여러개의 분할된 서비스를 하나의 트랜잭션으로 묶을 수 있도록 ServiceTransactionBroker를 만들어서 관리하기
     * TODO cf. 현재는 개별 서비스가 독립된 트랜잭션...
     */
    @ApiDocs053
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
     * API050 경기 확정 기록 확정하기
     * @author 이성주
     */
    @ApiDocs050
    @Auth( type = AuthType.GAME_RECORD, level = AuthLevel.GAME_CREATOR )
    @PostMapping("/{gameSeq}/confirmation")
    public ResponseEntity<?> confirmGame(
            @PathVariable("gameSeq") Long gameSeq
    ) {
        gameService.confirmGame( gameSeq );
        return RESPONSE_OK;
    }

    /**
     * API051 경기 삭제하기
     */
    @ApiDocs051
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
    @ApiDocs055
    @Auth( type = AuthType.GAME_RECORD, level = AuthLevel.GAME_RECORDER )
    @GetMapping("/{gameSeq}/recorders")
    public ResponseEntity<?> getGameRecorders(
            @PathVariable("gameSeq") Long gameSeq
    ) {
        GameRecorderQuery.Result result = gameAuthService.getGameRecorders(
                                                GameRecorderQuery.builder()
                                                    .gameSeq( gameSeq )
                                                    .build()
                                          );
        return ResponseEntity.ok( new GetGameRecordersResponse( result ) );
    }

    /**
     * API056 경기기록원 저장
     * @since 24.04.21 (일)
     * @author injun
     */
    @ApiDocs056
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
     */
    @ApiDocs057
    @Auth( type = AuthType.GAME_RECORD, level = AuthLevel.GAME_RECORDER )
    @GetMapping("/{gameSeq}/recorders/candidates")
    public ResponseEntity<?> getGameRecorderCandidates(
            @PathVariable(name = "gameSeq") Long gameSeq,
            @RequestParam(name = "homeAwayCode", required = false) String homeAwayCode
    ) {
        GameRecorderCandidatesQuery.Result result = gameAuthService.getGameRecorderCandidates(
                                                        GameRecorderCandidatesQuery.builder()
                                                            .gameSeq( gameSeq )
                                                            .homeAwayCode( homeAwayCode )
                                                            .build()
                                                    );
        return ResponseEntity.ok( new GetGameRecorderCandidatesResponse( result ) );
    }

    /**
     * API060 쿼터 엔트리 정보 저장
     * 22.12.15(목) @ReauestBody부분 Request클래스로 대체
     */
    @ApiDocs060
    @Auth( type = AuthType.GAME_RECORD, level = AuthLevel.GAME_RECORDER )
    @PostMapping("/{gameSeq}/entry")
    public ResponseEntity<?> saveQuarterEntryInfo(
            @PathVariable(name = "gameSeq") Long gameSeq,
            @RequestBody @Valid SaveQuarterEntryInfoRequest reqBody
    ) {
        gameJoinManagerService.saveQuarterEntryInfo( reqBody.toCommand( gameSeq ) );
        return RESPONSE_OK;
    }

    /**
     * API061 경기 참가 선수 전체 조회
     */
    @ApiDocs061
    @Auth
    @GetMapping("/{gameSeq}/players")
    public ResponseEntity<?> getAllGameJoinPlayers(
            @PathVariable(name = "gameSeq") Long gameSeq
    ) {
        GameJoinPlayerQuery.Result homeResult = gameJoinManagerService.getGameJoinPlayers(
            GameJoinPlayerQuery.builder()
                    .gameSeq(       gameSeq )
                    .homeAwayCode(  HomeAwayCode.HOME_TEAM.getCode() )
                    .build()
        );
        GameJoinPlayerQuery.Result awayResult = gameJoinManagerService.getGameJoinPlayers(
            GameJoinPlayerQuery.builder()
                    .gameSeq(       gameSeq )
                    .homeAwayCode(  HomeAwayCode.AWAY_TEAM.getCode() )
                    .build()
        );
        return ResponseEntity.ok(
            new GetAllGameJoinPlayersResponse( gameSeq, homeResult.getPlayers(), awayResult.getPlayers() )
        );
    }

    /**
     * API066 경기 참가 선수 조회 ( 팀별 조회 )
     * - 페이징 처리 적용 완료
     */
    @ApiDocs066
    @Auth
    @GetMapping("/{gameSeq}/homeAwayCode/{homeAwayCode}/players")
    public ResponseEntity< GetGameJoinPlayersResponse > getGameJoinPlayersWithPaging(
        @PathVariable(name = "gameSeq")         Long gameSeq,
        @PathVariable(name = "homeAwayCode")    String homeAwayCode,
        @RequestParam(name = "pageNo" , defaultValue = "0") Integer pageNo
    ) {
        GameJoinPlayerQuery.Result result = gameJoinManagerService.getGameJoinPlayersWithPaging(
            GameJoinPlayerQuery.builder()
                    .gameSeq(       gameSeq )
                    .homeAwayCode(  homeAwayCode )
                    .pageNo(        pageNo )
                    .build()
        );
        return ResponseEntity.ok( new GetGameJoinPlayersResponse(
                gameSeq,
                homeAwayCode,
                result.getTeamSeq(),
                result.getPagination(),
                result.getPlayers()
        ) );
    }

    /**
     * API062 경기참가팀 확정
     * 22.12.15(목) @ReauestBody부분 Request클래스로 대체
     * 23.01.11(수) 누락된 로직 추가 - 게임기록상태코드 업데이트
     */
    @ApiDocs062
    @Auth( type = AuthType.GAME_RECORD, level = AuthLevel.GAME_CREATOR )
    @PostMapping("/{gameSeq}/gameJoinTeams")
    public ResponseEntity< Void > confirmGameJoinTeam (
            @PathVariable(name = "gameSeq") Long gameSeq,
            @RequestBody @Valid ConfirmGameJoinTeamRequest request
    ) {
        gameJoinManagerService.confirmJoinTeam( request.toCommand( gameSeq ) );
        return ResponseEntity.ok().build();
    }

    /**
     * API063 경기 전체 기록 조회
     * @since 22.12.25(금)
     * @author 강창기
     * 23.01.25(수) 여인준 - API Body 수정
     */
    @ApiDocs063
    @Auth
    @GetMapping("/{gameSeq}/quarters")
    public ResponseEntity<?> getGameAllRecords (
            @PathVariable(name = "gameSeq") Long gameSeq
    ) {
        GameAllRecordsQuery.Result result = gameRecordManagerService.getGameAllRecords( GameAllRecordsQuery.builder()
            .gameSeq( gameSeq )
            .build()
        );
        return ResponseEntity.ok( new GetGameAllRecordsResponse( result ) );
    }

}
