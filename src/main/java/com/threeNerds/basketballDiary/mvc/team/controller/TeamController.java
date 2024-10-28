package com.threeNerds.basketballDiary.mvc.team.controller;

import com.threeNerds.basketballDiary.auth.Auth;

import com.threeNerds.basketballDiary.mvc.team.controller.docs.ApiDocs019;
import com.threeNerds.basketballDiary.mvc.team.controller.docs.ApiDocs021;
import com.threeNerds.basketballDiary.mvc.team.controller.docs.ApiDocs052;
import com.threeNerds.basketballDiary.mvc.team.controller.request.CreateTeamRequest;
import com.threeNerds.basketballDiary.mvc.team.controller.response.SearchTeamGamesResponse;
import com.threeNerds.basketballDiary.mvc.team.controller.response.SearchTeamsResponse;
import com.threeNerds.basketballDiary.mvc.team.service.TeamAuthService;
import com.threeNerds.basketballDiary.mvc.team.service.TeamGameService;
import com.threeNerds.basketballDiary.mvc.team.service.TeamService;
import com.threeNerds.basketballDiary.mvc.team.service.dto.TeamAuthQuery;
import com.threeNerds.basketballDiary.mvc.team.service.dto.TeamGameQuery;
import com.threeNerds.basketballDiary.mvc.team.service.dto.TeamQuery;
import com.threeNerds.basketballDiary.session.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.threeNerds.basketballDiary.session.util.SessionUtil.LOGIN_USER;

/**
 * ... 수행하는 Controller
 * @author 책임자 작성
 *
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * 2022.03.14 강창기 : 팀 목록 조회 구현
 * 2022.03.23 강창기 : 팀 생성 구현
 * </pre>
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;
    private final TeamGameService teamGameService;
    private final TeamAuthService teamAuthService;

    /**
     * API019 : 팀 목록 조회
     */
    @ApiDocs019
    @GetMapping
    public ResponseEntity<SearchTeamsResponse> searchTeams(
            @RequestParam(name = "team-name"  , required = false) String teamName,
            @RequestParam(name = "sigungu"    , required = false) String sigungu,
            @RequestParam(name = "start-day"  , required = false) String startDay,
            @RequestParam(name = "end-day"    , required = false) String endDay,
            @RequestParam(name = "start-time" , required = false) String startTime,
            @RequestParam(name = "end-time"   , required = false) String endTime,
            @RequestParam(name = "page-no"     , defaultValue = "0") Integer pageNo
    ) {
        TeamQuery query = TeamQuery.builder()
                            .teamName(  teamName )
                            .sigungu(   sigungu )
                            .startDay(  startDay )
                            .endDay(    endDay )
                            .startTime( startTime )
                            .endTime(   endTime )
                            .pageNo(    pageNo )
                            .build();
        return ResponseEntity.ok().body( new SearchTeamsResponse( teamService.searchTeams( query ) ) );
    }

    /**
     * API021 : 팀 생성
     */
    @ApiDocs021
    @Auth
    @PostMapping
    public ResponseEntity<Void> createTeam(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser sessionUser,
            @RequestPart( required = false ) MultipartFile teamLogoImage,
            @RequestPart @Valid CreateTeamRequest teamInfo
    ) {
        Long loginUserSeq = sessionUser.getUserSeq();
        teamService.createTeam( teamInfo.toCommand( loginUserSeq, teamLogoImage ) );

        /** 세션의 소속팀 권한정보 update */
        TeamAuthQuery.Result authResult = teamAuthService.getAllTeamAuthInfo(
                TeamAuthQuery.builder()
                        .userSeq( loginUserSeq )
                        .build()
        );
        sessionUser.setAuthTeams( authResult.getAuthTeams() );
        return ResponseEntity.ok().build();
    }

    /**
     * API052 : 팀의 경기 목록 검색
     */
    @ApiDocs052
    @GetMapping("/{teamSeq}/games")
    public ResponseEntity<SearchTeamGamesResponse> searchTeamGames (
            @PathVariable( value = "teamSeq" ) Long teamSeq                                     ,
            @RequestParam( name = "pageNo", defaultValue = "1") Integer pageNo                  ,
            @RequestParam( name = "gameBgngYmd"     , required = false ) String gameBgngYmd     ,
            @RequestParam( name = "gameEndYmd"      , required = false ) String gameEndYmd      ,
            @RequestParam( name = "sidoCode"        , required = false ) String sidoCode        ,
            @RequestParam( name = "gamePlaceName"   , required = false ) String gamePlaceName   ,
            @RequestParam( name = "gameTypeCode"    , required = false ) String gameTypeCode    ,
            @RequestParam( name = "homeAwayCode"    , required = false ) String homeAwayCode
    ) {
        TeamGameQuery.Result result = teamGameService.searchGames(
            TeamGameQuery.builder()
                    .teamSeq(       teamSeq )
                    .pageNo(        pageNo )
                    .gameBgngYmd(   gameBgngYmd )
                    .gameEndYmd(    gameEndYmd )
                    .sidoCode(      sidoCode )
                    .gamePlaceName( gamePlaceName )
                    .gameTypeCode(  gameTypeCode )
                    .homeAwayCode(  homeAwayCode )
                    .build()
        );
        return ResponseEntity.ok( new SearchTeamGamesResponse( result ) );
    }
}
