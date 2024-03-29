package com.threeNerds.basketballDiary.mvc.game.controller;

import com.threeNerds.basketballDiary.auth.constant.AuthLevel;
import com.threeNerds.basketballDiary.auth.constant.AuthType;
import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import com.threeNerds.basketballDiary.http.ResponseJsonBody;
import com.threeNerds.basketballDiary.auth.Auth;
import com.threeNerds.basketballDiary.mvc.game.dto.confirmGameJoinTeam.request.ConfirmGameJoinTeamRequest;
import com.threeNerds.basketballDiary.mvc.game.controller.request.CreateGameRequest;
import com.threeNerds.basketballDiary.mvc.game.controller.request.RegisterGameJoinPlayersRequest;
import com.threeNerds.basketballDiary.mvc.game.controller.request.SaveQuarterEntryInfoRequest;
import com.threeNerds.basketballDiary.mvc.game.controller.response.*;
import com.threeNerds.basketballDiary.mvc.game.dto.*;
import com.threeNerds.basketballDiary.mvc.game.dto.createGameQuarterBasicInfo.request.CreateGameQuarterBasicInfoRequest;
import com.threeNerds.basketballDiary.mvc.game.dto.deleteGameQuarter.request.DeleteGameQuarterRequest;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameAllQuartersRecords.GetGameAllQuartersRecordsResponse;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameEntry.request.GetGameEntryRequest;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameEntry.response.GetGameEntryResponse;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameJoinPlayerRecordsByQuarter.request.GetGameJoinPlayerRecordsByQuarterRequest;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameJoinPlayers.request.GetGameJoinPlayersRequest;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameJoinPlayers.response.GetGameJoinPlayersResponse;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameJoinTeamMembers.request.GetGameJoinTeamMembersRequest;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameQuarterRecords.request.GetGameQuarterRecordsRequest;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameQuarterRecords.response.GetGameQuarterRecordsResponse;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameRecorders.request.GetGameRecordersRequest;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameRecorders.response.GetGameRecordersResponse;
import com.threeNerds.basketballDiary.mvc.game.dto.saveGameRecorder.request.SaveGameRecordersRequest;
import com.threeNerds.basketballDiary.mvc.game.dto.saveQuarterRecords.request.SaveQuarterRecordsRequest;
import com.threeNerds.basketballDiary.mvc.game.service.GameAuthService;
import com.threeNerds.basketballDiary.mvc.game.service.GameJoinManagerService;
import com.threeNerds.basketballDiary.mvc.game.service.GameRecordManagerService;
import com.threeNerds.basketballDiary.mvc.game.service.GameService;
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

    private final GameService gameService;
    private final GameJoinManagerService gameJoinManagerService;
    private final GameRecordManagerService gameRecordManagerService;
    private final GameAuthService gameAuthService;


    /**
     * API035 게임참가 선수등록하기
     * 22.12.15(목) @ReauestBody부분 Request클래스로 대체
     */

    @ApiDocs035
    @Auth
    @PostMapping("/{gameSeq}/homeAwayCode/{homeAwayCode}/players")
    public ResponseEntity<URI> registerGameJoinPlayers(
            @PathVariable(name = "gameSeq") Long gameSeq,
            @PathVariable(name = "homeAwayCode") String homeAwayCode,
            @RequestBody @Valid RegisterGameJoinPlayersRequest reqBody
    ) {
         reqBody = new RegisterGameJoinPlayersRequest(
                                                        gameSeq,
                                                        homeAwayCode,
                                                        reqBody.getGameJoinPlayers()
                                                      );
        gameJoinManagerService.registerGameJoinPlayers(reqBody);
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
    public ResponseEntity<Void> saveQuarterRecords(
            @PathVariable(name = "gameSeq") Long gameSeq,
            @PathVariable(name = "quarterCode") String quarterCode,
            @RequestBody @Valid SaveQuarterRecordsRequest requestMessage
    ) {
        gameRecordManagerService.saveQuarterRecord( new SaveQuarterRecordsRequest(
                gameSeq,
                quarterCode,
                requestMessage
        ) );
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
        GetGameEntryRequest request = new GetGameEntryRequest( gameSeq, quarterCode, homeAwayCode );
        GetGameEntryResponse resBody = gameJoinManagerService.getGameEntry(request);
        return ResponseEntity.ok(resBody);
    }


    /**
     * API041 게임쿼터 삭제
     * @auth    게임기록권한자인 경우
     * @param   gameSeq
     * @param   quarterCode
     * @return
     */
    @Auth
    @DeleteMapping("/{gameSeq}/quarters/{quarterCode}")
    public ResponseEntity<?> deleteGameQuarter(
            @PathVariable("gameSeq") Long gameSeq,
            @PathVariable("quarterCode") String quarterCode
    ){
        DeleteGameQuarterRequest reqBody = new DeleteGameQuarterRequest()
                        .gameSeq(gameSeq)
                        .quarterCode(quarterCode);
        gameRecordManagerService.deleteGameQuarter(reqBody);
        return RESPONSE_OK;
    }

    /**
     * API043 참가선수 쿼터기록조회
     * @param gameSeq 게임Seq
     * @param quarterCode 쿼터코드; 01~04(1~4쿼터), 11(전반), 12(후반)
     * @result 특정쿼터의 선수별 기록조회
     * @author 강창기
     */
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
        return ResponseEntity.ok( gameService.getGameInfo( gameSeq ) );
    }

    /**
     * API047 경기 참가팀 조회
     */
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
     * API050 경기 확정(경기 등록)
     * @author 이성주
     */
    @Auth // TODO 게임기록권한자
    @PostMapping("/{gameSeq}/confirmation")
    public ResponseEntity<?> confirmGame(
            @PathVariable("gameSeq") Long gameSeq
    ) {
        gameService.confirmGame(gameSeq);
        return RESPONSE_OK;
    }

    /**
     * API051 게임 삭제
     */
    @Auth // TODO 게임기록권한자
    @DeleteMapping("/{gameSeq}")
    public ResponseEntity<?> deleteGame(
            @PathVariable(name = "gameSeq") Long gameSeq
    ) {
        gameService.deleteGame(gameSeq);
        return RESPONSE_OK;
    }

    /**
     * API053 게임 생성
     * - 생성한 게임 정보를 반환
     * 22.12.15(목) @ReauestBody부분 Request클래스로 대체
     */
    @Auth // TODO 게임기록권한자
    @PostMapping
    public ResponseEntity<?> createGame (
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser sessionUser,
            @RequestBody CreateGameRequest reqBody
    ) {
        GameCreationDTO gameCreationInfo = reqBody.getGameCreationInfo();

        Long userSeq = sessionUser.getUserSeq();
        gameCreationInfo.userSeq(userSeq);

        GameCreationDTO result = gameService.createGame(gameCreationInfo);

        // TODO 경기 권한정보 생성 및 Session에 할당 처리 추가
        // gameAuthService.createGameCreatorAuth( userSeq, gameSeq );
        // sessionUser.setAuthGames( gameAuthService.get
        return ResponseEntity.ok(result);
    }


    /**
     * API055 게임기록자 조회
     */
    @Auth( type = AuthType.GAME_RECORD, level = AuthLevel.GAME_RECORDER )
    @GetMapping("/{gameSeq}/gameRecorders")
    public ResponseEntity<?> getGameRecorders(
            @PathVariable("gameSeq") Long gameSeq
    ) {
        GetGameRecordersRequest reqBody = new GetGameRecordersRequest( gameSeq );
        GetGameRecordersResponse resBody = gameAuthService.getGameManagers( reqBody );
        return ResponseEntity.ok( resBody );
    }

    /**
     * API056 게임기록자 목록 저장
     */
    @Auth( type = AuthType.GAME_RECORD, level = AuthLevel.GAME_CREATOR )
    @PostMapping("/{gameSeq}/gameRecorders")
    public ResponseEntity<?> saveGameRecorders(
            @PathVariable("gameSeq") Long gameSeq,
            @RequestBody @Valid SaveGameRecordersRequest request
    ) {
        request.gameSeq( gameSeq );
        gameAuthService.saveGameRecorders( request );
        return RESPONSE_OK;
    }

    /**
     * API057 게임참가팀 팀원조회
     * 23.01.28(토)
     * @author 강창기
     */
    @Auth( type = AuthType.GAME_RECORD, level = AuthLevel.GAME_RECORDER )
    @GetMapping("/{gameSeq}/teamMembers")
    public ResponseEntity<?> getGameJoinTeamMembers(
            @PathVariable(name = "gameSeq") Long gameSeq,
            @RequestParam(name = "homeAwayCode", required = false) String homeAwayCode
    ) {
        GetGameJoinTeamMembersRequest reqBody = new GetGameJoinTeamMembersRequest( gameSeq, homeAwayCode );
        ResponseJsonBody resBody = gameJoinManagerService.getGameJoinTeamMembers( reqBody );
        return ResponseEntity.ok( resBody );
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
        // TODO SaveQuarterEntryInfoRequset로 변경하기
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

    /**
     * API064 게임쿼터 기초정보생성
     * @since 23.03.10(금)
     * @author 여인준
     */
    @Auth( type = AuthType.GAME_RECORD, level = AuthLevel.GAME_RECORDER )
    @PostMapping("/{gameSeq}/quarters/{quarterCode}")
    public ResponseEntity< Void > createGameQuarterBasicInfo (
            @PathVariable(name = "gameSeq") Long gameSeq,
            @PathVariable(name = "quarterCode") String quarterCode
    ) {
        gameRecordManagerService.createGameQuarterBasicInfo( new CreateGameQuarterBasicInfoRequest()
                .gameSeq( gameSeq )
                .quarterCode( quarterCode ) );
        return ResponseEntity.ok().build();
    }
}
