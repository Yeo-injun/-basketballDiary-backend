package com.threeNerds.basketballDiary.mvc.myTeam.controller;

import com.threeNerds.basketballDiary.auth.Auth;
import com.threeNerds.basketballDiary.auth.constant.AuthLevel;
import com.threeNerds.basketballDiary.constant.code.type.JoinRequestStateCode;
import com.threeNerds.basketballDiary.mvc.game.service.dto.TeamMemberQuery;
import com.threeNerds.basketballDiary.mvc.myTeam.controller.docs.*;
import com.threeNerds.basketballDiary.mvc.myTeam.controller.request.SearchMyTeamGamesRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.controller.response.*;
import com.threeNerds.basketballDiary.mvc.myTeam.controller.response.GetProfileResponse;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.TeamInfoDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.service.*;
import com.threeNerds.basketballDiary.mvc.myTeam.service.dto.*;
import com.threeNerds.basketballDiary.mvc.game.service.GameRecordManagerService;
import com.threeNerds.basketballDiary.mvc.myTeam.controller.response.SearchAllTeamMembersResponse;
import com.threeNerds.basketballDiary.mvc.team.dto.TeamRegularExerciseDTO;
import com.threeNerds.basketballDiary.session.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.threeNerds.basketballDiary.constant.HttpResponseConst.RESPONSE_OK;
import static com.threeNerds.basketballDiary.session.util.SessionUtil.LOGIN_USER;

/**
 * 소속팀과 관련된 업무를 처리하는 Controller
 *
 * @author 책임자 작성
 * <p>
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * 2022.02.11 강창기 : 소속팀 목록조회 구현
 * 2022.02.15 여인준 : 소속팀 선수초대API 구현
 * 2022.02.23 강창기 : 소속팀 정보 수정 구현
 * 2022.02.24 강창기 : 소속팀 정보 삭제 구현
 * 2022.02.26 강창기 : 소속팀 운영진 조회 구현
 * 2022.02.27 강창기 : 소속팀 팀원 조회 구현
 * 2022.03.04 여인준 : 책임 API ResponseEntity적용
 * </pre>
 */


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/myTeams")
public class MyTeamController {

    /**--------------------------------------
     * Repository
     **--------------------------------------*/
    private final MyTeamService myTeamService;
    private final MyTeamAuthService myTeamAuthService;
    private final MyTeamJoinService myTeamJoinService;
    private final MyTeamProfileService myTeamProfileService;

    private final GameRecordManagerService gameRecordManagerService;


    /**
     * API001 : 소속팀 운영진 조회
     */
    @ApiDocs001
    @Auth( level = AuthLevel.TEAM_MEMBER )
    @GetMapping("/{teamSeq}/managers")
    public ResponseEntity<GetManagersResponse> getManagers(
            @PathVariable(value = "teamSeq") Long teamSeq
    ) {
        TeamMemberQuery.Result result = myTeamService.getManagers(
            TeamMemberQuery.builder()
                    .teamSeq(   teamSeq )
                    .build()
        );
        return ResponseEntity.ok().body( new GetManagersResponse( result ) );
    }

    /**
     * API002 : 소속팀 팀원등급인 팀원 목록 조회
     */
    @ApiDocs002
    @Auth( level = AuthLevel.TEAM_MEMBER )
    @GetMapping("/{teamSeq}/members")
    public ResponseEntity<?> getTeamMembers(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser sessionUser,
            @PathVariable(value = "teamSeq") Long teamSeq,
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo
    ) {
        TeamMemberQuery.Result result = myTeamService.getTeamMembers(
            TeamMemberQuery.builder()
                    .teamSeq(   teamSeq )
                    .pageNo(    pageNo )
                    .build()
        );
        return ResponseEntity.ok().body( new GetTeamMembersResponse( result ) );
    }

    /**
     * API036 : 소속팀 전체 팀원목록 검색
     */
    @ApiDocs036
    @Auth( level = AuthLevel.TEAM_MEMBER )
    @GetMapping("/{teamSeq}/allTeamMembers")
    public ResponseEntity<SearchAllTeamMembersResponse> searchAllTeamMembers(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser sessionUser,
            @PathVariable( value = "teamSeq" ) Long teamSeq,
            @RequestParam( name = "pageNo", defaultValue = "0" ) Integer pageNo,
            @RequestParam( name = "playerName", required = false ) String playerName
    ) {
        TeamMemberQuery.Result result = myTeamService.searchAllTeamMembers(
                TeamMemberQuery.builder()
                    .teamSeq( teamSeq )
                    .playerName( playerName )
                    .pageNo( pageNo )
                    .build()
        );
        return ResponseEntity.ok().body( new SearchAllTeamMembersResponse( result ) );
    }

    /**
     * API003 : 소속팀 관리자 임명하기
     */
    @ApiDocs003
    @Auth( level = AuthLevel.TEAM_LEADER )
    @PatchMapping("{teamSeq}/members/{teamMemberSeq}/manager")
    public ResponseEntity<Void> appointManager (
            @PathVariable Long teamSeq,
            @PathVariable Long teamMemberSeq
    ) {
        myTeamAuthService.appointManager(
            TeamAuthCommand.builder()
                .teamSeq(       teamSeq )
                .teamMemberSeq( teamMemberSeq )
                .build()
        );
        return ResponseEntity.ok().build();
    }

    /**
     * API015 : 소속팀 관리자 제명하기
     */
    @ApiDocs015
    @Auth( level = AuthLevel.TEAM_LEADER )
    @DeleteMapping("/{teamSeq}/members/{teamMemberSeq}/manager")
    public ResponseEntity<?> dismissManager(
            @PathVariable Long teamSeq,
            @PathVariable Long teamMemberSeq
    ) {
        myTeamAuthService.dismissManager(
            TeamAuthCommand.builder()
                .teamSeq(       teamSeq )
                .teamMemberSeq( teamMemberSeq )
                .build()
        );
        return RESPONSE_OK;
    }

    /**
     * API004 : 소속팀 회원 강퇴시키기
     */
    @ApiDocs004
    @Auth( level = AuthLevel.TEAM_LEADER )
    @DeleteMapping("{teamSeq}/members/{teamMemberSeq}")
    public ResponseEntity<?> dischargeTeamMember(
            @PathVariable Long teamSeq,
            @PathVariable Long teamMemberSeq
    ) {
        myTeamAuthService.dismissTeamMember(
            TeamAuthCommand.builder()
                    .teamSeq(       teamSeq )
                    .teamMemberSeq( teamMemberSeq )
                    .build()
        );
        return RESPONSE_OK;
    }

    /**
     * API005 : 초대한 사용자 목록 조회
     */
    @ApiDocs005
    @Auth( level = AuthLevel.TEAM_MANAGER )
    @GetMapping("/{teamSeq}/joinRequestsTo")
    public ResponseEntity<?> getInvitations(
            @SessionAttribute(value = LOGIN_USER,required = false) SessionUser userSession,
            @PathVariable Long teamSeq,
            @RequestParam(name = "state", required = false) String joinRequestStateCode
    ) {
        InvitationQuery query = InvitationQuery.builder()
                .userSeq(           userSession.getUserSeq() )
                .teamSeq(           teamSeq )
                .joinRequestState(  JoinRequestStateCode.ofType( joinRequestStateCode ) )
                .build();
        return ResponseEntity.ok( new GetInvitationsResponse( myTeamJoinService.getInvitations( query ) ) );
    }

    /**
     * API007 : 회원에게 초대 요청 보내기
     */
    @ApiDocs007
    @Auth( level = AuthLevel.TEAM_MANAGER )
    @PostMapping("/{teamSeq}/joinRequestTo/{userSeq}")
    public ResponseEntity<?> inviteUser(
            @PathVariable Long teamSeq,
            @PathVariable Long userSeq
    ) {
        myTeamJoinService.inviteUser(
            InvitationCommand.builder()
                .teamSeq( teamSeq )
                .userSeq( userSeq )
                .build()
        );
        return RESPONSE_OK;
    }

    /**
     * API008 : 소속팀이 받은 가입요청목록 조회
     */
    @ApiDocs008
    @Auth( level = AuthLevel.TEAM_MANAGER )
    @GetMapping("/{teamSeq}/joinRequestsFrom")
    public ResponseEntity<?> getReceivedJoinRequests(
            @SessionAttribute( value = LOGIN_USER,required = false ) SessionUser userSession,
            @PathVariable Long teamSeq,
            @RequestParam( name = "state", required = false ) String joinRequestStateCode
    ) {
        JoinRequestQuery query = JoinRequestQuery.builder()
                .userSeq(           userSession.getUserSeq() )
                .teamSeq(           teamSeq )
                .joinRequestState(  JoinRequestStateCode.ofType( joinRequestStateCode ) )
                .build();
        return ResponseEntity.ok( new GetReceivedJoinRequestsResponse( myTeamJoinService.getReceivedJoinRequests( query ) ) );
    }

    /**
     * API009 : 팀가입요청 승인
     */
    @ApiDocs009
    @Auth( level = AuthLevel.TEAM_MANAGER )
    @PatchMapping("/{teamSeq}/joinRequestFrom/{teamJoinRequestSeq}/approval")
    public ResponseEntity<?> approveJoinRequest(
            @PathVariable Long teamJoinRequestSeq,
            @PathVariable Long teamSeq
    ) {
        myTeamJoinService.approveJoinRequest(
            JoinRequestCommand.builder()
                    .teamJoinRequestSeq(    teamJoinRequestSeq )
                    .teamSeq(               teamSeq )
                    .build()
        );
        return RESPONSE_OK;
    }

    /**
     * API010 : 팀가입요청 거절
     */
    @ApiDocs010
    @Auth( level = AuthLevel.TEAM_MANAGER )
    @PatchMapping("/{teamSeq}/joinRequestFrom/{teamJoinRequestSeq}/rejection")
    public ResponseEntity<?> rejectJoinRequest(
            @PathVariable Long teamSeq,
            @PathVariable Long teamJoinRequestSeq
    ) {
        myTeamJoinService.rejectJoinRequest(
            JoinRequestCommand.builder()
                    .teamJoinRequestSeq(    teamJoinRequestSeq )
                    .teamSeq(               teamSeq )
                    .build()
        );
        return RESPONSE_OK;
    }

    /**
     * API011 소속팀 개인프로필 조회
     */
    @ApiDocs011
    @Auth( level = AuthLevel.TEAM_MEMBER )
    @GetMapping("/{teamSeq}/profile")
    public ResponseEntity<?> getProfile(
            @SessionAttribute(value = LOGIN_USER,required = false) SessionUser userSession,
            @PathVariable Long teamSeq
    ) {
        ProfileQuery query = ProfileQuery.builder()
                .userSeq( userSession.getUserSeq() )
                .teamSeq( teamSeq )
                .build();
        return ResponseEntity.ok( new GetProfileResponse( myTeamProfileService.getProfile( query ) ) );
    }

    /**
     * API012 소속팀 개인프로필 수정
     */
    @ApiDocs012
    @Auth( level = AuthLevel.TEAM_MEMBER )
    @PostMapping("/{teamSeq}/profile")
    public ResponseEntity<?> modifyProfile(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser userSession,
            @PathVariable Long teamSeq,
            @RequestPart(required = false) String backNumber,
            @RequestPart(required = false) MultipartFile profileImage
    ) {
        myTeamProfileService.modifyProfile(
            ProfileCommand.builder()
                    .userSeq(       userSession.getUserSeq() )
                    .teamSeq(       teamSeq )
                    .backNumber(    backNumber )
                    .profileImage(  profileImage )
                    .build()
        );
        return RESPONSE_OK;
    }


    /**
     * API013 소속팀 탈퇴하기 ( 프로필 삭제 )
     * TODO FrontEnd에서 호출 하지 않음. >> 탈퇴기능 만들기..
     */
    @ApiDocs013
    @Auth( level = AuthLevel.TEAM_MEMBER )
    @DeleteMapping("/{teamSeq}/profile")
    public ResponseEntity<?> removeProfile(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser userSession,
            @PathVariable Long teamSeq
    ) {
        myTeamProfileService.removeProfile(
            ProfileCommand.builder()
                    .userSeq(       userSession.getUserSeq() )
                    .teamSeq(       teamSeq )
                    .build()
        );
        return RESPONSE_OK;
    }

    /**
     * API014 : 소속팀 목록 조회
     */
    @ApiDocs014
    @Auth( level = AuthLevel.TEAM_MEMBER )
    @GetMapping
    public ResponseEntity<GetMyTeamsResponse> getMyTeams(
            @SessionAttribute( value = LOGIN_USER ) SessionUser sessionUser,
            @RequestParam( name = "pageNo", defaultValue = "0" ) Integer pageNo
    ) {
        MyTeamQuery.Result result = myTeamService.getMyTeams(
            MyTeamQuery.builder()
                    .userSeq(   sessionUser.getUserSeq() )
                    .pageNo(    pageNo )
                    .build()
        );
        return ResponseEntity.ok().body( new GetMyTeamsResponse( result ) );
    }


    /**
     * API016 : 소속팀 정보 조회
     */
    @ApiDocs016
    @Auth( level = AuthLevel.TEAM_MEMBER )
    @GetMapping("/{teamSeq}/info")
    public ResponseEntity<GetTeamInfoResponse> getMyTeamInfo(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser userSession,
            @PathVariable(value = "teamSeq") Long teamSeq
    ) {
        MyTeamInfoQuery.Result result = myTeamService.getMyTeamInfo(
                MyTeamInfoQuery.builder()
                        .userSeq( userSession.getUserSeq() )
                        .teamSeq( teamSeq )
                        .build()
        );
        return ResponseEntity.ok().body( new GetTeamInfoResponse( result ) );
    }

    /**
     * API017 : 소속팀 정보 수정
     * 23.10.28 인준 : 팀 이미지 속성 추가 반영 ( @RequestPart를 적용하여 mulitpart/form 데이터의 객체 바인딩 제공 )
     */
    @ApiDocs017
    @Auth( level = AuthLevel.TEAM_MANAGER )
    @PostMapping( "/{teamSeq}/info" )
    public ResponseEntity<?> modifyMyTeamInfo(
            @PathVariable( value = "teamSeq" ) Long teamSeq,
            @RequestPart( value = "teamInfo", required = false ) TeamInfoDTO teamInfo,
            @RequestPart( value = "teamRegularExercises", required = false ) List<TeamRegularExerciseDTO> exercises,
            @RequestPart( value = "teamLogo", required = false ) MultipartFile teamLogo
    ) {
        myTeamService.modifyMyTeamInfo(
                MyTeamInfoCommand.builder()
                        .teamSeq( teamSeq )
                        .teamInfo( teamInfo )
                        .teamRegularExercises( exercises )
                        .teamLogoImage( teamLogo )
                        .build()
        );
        return RESPONSE_OK;
    }

    /**
     * API018 : 소속팀 정보 삭제
     */
    // TODO 리팩토링 서비스 패턴 작업
    @Auth( level = AuthLevel.TEAM_LEADER )
    @DeleteMapping("/{teamSeq}")
    public ResponseEntity<?> removeMyTeam(
            @PathVariable(value = "teamSeq") Long teamSeq
    ) {
        myTeamService.deleteMyTeam( teamSeq );

        // 삭제가 정상적으로 완료된 경우 204 No Content로 응답한다.
        return ResponseEntity.noContent().build();
    }

    /**
     * API052 : 소속팀 게임목록조회
     */
    // TODO 리팩토링 서비스 패턴 작업
    @Auth( level = AuthLevel.TEAM_MEMBER )
    @GetMapping("/{teamSeq}/games")
    public ResponseEntity<SearchMyTeamGamesResponse> searchMyTeamGames (
            @SessionAttribute( value = LOGIN_USER ) SessionUser sessionUser                     ,
            @PathVariable( value = "teamSeq" ) Long teamSeq                                     ,
            @RequestParam( name = "pageNo", defaultValue = "1") Integer pageNo                  ,
            @RequestParam( name = "gameBgngYmd"     , required = false ) String gameBgngYmd     ,
            @RequestParam( name = "gameEndYmd"      , required = false ) String gameEndYmd      ,
            @RequestParam( name = "sidoCode"        , required = false ) String sidoCode        ,
            @RequestParam( name = "gamePlaceName"   , required = false ) String gamePlaceName   ,
            @RequestParam( name = "gameTypeCode"    , required = false ) String gameTypeCode    ,
            @RequestParam( name = "homeAwayCode"    , required = false ) String homeAwayCode
    ) {
        // myTeamGameService.getGames() 소속팀을 기준으로 경기 기록 조회
        return ResponseEntity.ok(
            gameRecordManagerService.searchMyTeamGames( new SearchMyTeamGamesRequest(
                    sessionUser.getUserSeq()    , teamSeq           , pageNo        ,
                    gameBgngYmd                 , gameEndYmd        , sidoCode      ,
                    gamePlaceName               , gameTypeCode      , homeAwayCode
                )
            )
        );
    }

}
