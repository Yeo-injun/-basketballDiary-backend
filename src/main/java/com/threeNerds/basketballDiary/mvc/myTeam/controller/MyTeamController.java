package com.threeNerds.basketballDiary.mvc.myTeam.controller;

import com.threeNerds.basketballDiary.auth.Auth;
import com.threeNerds.basketballDiary.auth.constant.AuthLevel;
import com.threeNerds.basketballDiary.mvc.game.service.dto.TeamMemberQuery;
import com.threeNerds.basketballDiary.mvc.myTeam.controller.docs.ApiDocs001;
import com.threeNerds.basketballDiary.mvc.myTeam.controller.docs.ApiDocs002;
import com.threeNerds.basketballDiary.mvc.myTeam.controller.docs.ApiDocs036;
import com.threeNerds.basketballDiary.mvc.myTeam.controller.request.GetMyTeamsRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.controller.request.ModifyMyTeamInfoRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.controller.request.SearchMyTeamGamesRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.controller.response.GetMyTeamsResponse;
import com.threeNerds.basketballDiary.mvc.myTeam.controller.response.GetTeamInfoResponse;
import com.threeNerds.basketballDiary.mvc.myTeam.controller.response.GetTeamMembersResponse;
import com.threeNerds.basketballDiary.mvc.myTeam.controller.response.SearchMyTeamGamesResponse;
import com.threeNerds.basketballDiary.mvc.myTeam.controller.response.GetManagersResponse;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getMyTeamProfile.request.GetMyTeamProfileRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getMyTeamProfile.response.GetMyTeamProfileResponse;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getMyTeamProfile.response.MyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.modifyMyTeamProfile.request.ModifyMyTeamProfileRequest;
import com.threeNerds.basketballDiary.mvc.team.dto.PlayerDTO;
import com.threeNerds.basketballDiary.mvc.game.service.GameRecordManagerService;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.*;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.searchAllTeamMembers.response.SearchAllTeamMembersResponse;
import com.threeNerds.basketballDiary.mvc.myTeam.service.MyTeamService;
import com.threeNerds.basketballDiary.mvc.myTeam.service.TeamMemberManagerService;
import com.threeNerds.basketballDiary.mvc.myTeam.service.TeamMemberService;
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
    private final TeamMemberService teamMemberService;
    private final TeamMemberManagerService teamMemberManagerService;

    private final GameRecordManagerService gameRecordManagerService;


    /**
     * API001 : 소속팀 운영진 조회
     * 23.05.14. Request & Response 형 변경
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
     * API003 : 소속팀 관리자임명
     * 22.03.08 인준 : CustomException적용 - 퇴장상태로 업데이트된 결과가 없을 때 USER_NOT_FOUND 예외 발생
     * 22.03.29 인준 : 권한어노테이션 추가
     */
    @Auth( level = AuthLevel.TEAM_LEADER )
    @PatchMapping("{teamSeq}/members/{teamMemberSeq}/manager")
    public ResponseEntity<Void> appointManager (
            @PathVariable Long teamSeq,
            @PathVariable Long teamMemberSeq
    ) {
        CmnMyTeamDTO teamMemberKey = new CmnMyTeamDTO()
                .teamSeq(teamSeq)
                .teamMemberSeq(teamMemberSeq);

        teamMemberManagerService.appointManager(teamMemberKey);
        return ResponseEntity.ok().build();
    }

    /**
     * API004 : 소속팀 회원 강퇴시키기
     * 22.03.08 인준 : CustomException적용 - 퇴장상태로 업데이트된 결과가 없을 때 USER_NOT_FOUND 예외 발생
     * 22.03.29 인준 : 권한어노테이션 추가
     */
    @Auth( level = AuthLevel.TEAM_LEADER )
    @DeleteMapping("{teamSeq}/members/{teamMemberSeq}")
    public ResponseEntity<?> dischargeTeamMember(
            @PathVariable Long teamSeq,
            @PathVariable Long teamMemberSeq
    ) {
        CmnMyTeamDTO teamMemberKey = new CmnMyTeamDTO()
                                .teamSeq(teamSeq)
                                .teamMemberSeq(teamMemberSeq);
        teamMemberManagerService.dischargeTeamMember(teamMemberKey);
        return RESPONSE_OK;
    }

    /**
     * API005 : 소속팀의 초대한 선수목록 조회
     * 22.03.22(화) 인준 : 공통DTO적용 및 동적쿼리 수정
     * 22.03.29 인준 : 권한어노테이션 추가
     */
    @Auth( level = AuthLevel.TEAM_MANAGER )
    @GetMapping("/{teamSeq}/joinRequestsTo")
    public ResponseEntity<?> searchInvitedPlayer(
            @PathVariable Long teamSeq,
            @RequestParam(name = "state", required = false) String joinRequestStateCode
    ) {
        CmnMyTeamDTO playerSearchCond = new CmnMyTeamDTO()
                                            .teamSeq(teamSeq)
                                            .joinRequestStateCode(joinRequestStateCode);

        List<PlayerDTO> playerList = teamMemberManagerService.searchInvitedPlayer(playerSearchCond);
        return ResponseEntity.ok(playerList);
    }

    /**
     * API007 : 소속팀의 선수초대
     * 22.03.10 인준 : Service Layer에 CustomException 적용
     * 22.03.29 인준 : 권한어노테이션 추가
     */
    @Auth( level = AuthLevel.TEAM_MANAGER )
    @PostMapping("/{teamSeq}/joinRequestTo/{userSeq}")
    public ResponseEntity<?> inviteTeamMember(
            @PathVariable Long teamSeq,
            @PathVariable Long userSeq
    ) {
        CmnMyTeamDTO joinRequest = new CmnMyTeamDTO()
                                            .teamSeq(teamSeq)
                                            .userSeq(userSeq);

        teamMemberManagerService.inviteTeamMember(joinRequest);
        return RESPONSE_OK;
    }

    /**
     * API008 : 소속팀이 받은 가입요청목록 조회
     * 22.03.23 인준 : QueryString 기본값 제거 및 필수값 설정 해제. 공통DTO로 Service넘겨주기
     * 22.03.29 인준 : 권한어노테이션 추가
     */
    @Auth( level = AuthLevel.TEAM_MANAGER )
    @GetMapping("/{teamSeq}/joinRequestsFrom")
    public ResponseEntity<?> searchJoinRequestPlayer (
            @PathVariable Long teamSeq,
            @RequestParam(name = "state", required = false) String joinRequestStateCode
    ) {
        CmnMyTeamDTO playerSearchCond = new CmnMyTeamDTO()
                .teamSeq(teamSeq)
                .joinRequestStateCode(joinRequestStateCode);

        List<PlayerDTO> playerList = teamMemberManagerService.searchJoinRequestPlayer(playerSearchCond);
        return ResponseEntity.ok(playerList);
    }

    /**
     * API009 : 소속팀이 사용자의 가입요청 승인
     * 22.03.10 인준 : Service Layer에 CustomException 적용
     * 22.03.25 인준 : CmnMyTeamDTO적용
     * 22.03.29 인준 : 권한어노테이션 추가
     */
    @Auth( level = AuthLevel.TEAM_MANAGER )
    @PatchMapping("/{teamSeq}/joinRequestFrom/{teamJoinRequestSeq}/approval")
    public ResponseEntity<?> approveJoinRequest(
            @PathVariable Long teamJoinRequestSeq,
            @PathVariable Long teamSeq
    ) {
        CmnMyTeamDTO joinRequest = new CmnMyTeamDTO()
                .teamJoinRequestSeq(teamJoinRequestSeq)
                .teamSeq(teamSeq);

        teamMemberManagerService.approveJoinRequest(joinRequest);
        return RESPONSE_OK;
    }

    /**
     * API010 : 소속팀의 가입요청 거절
     * 22.03.25 인준 : CmnMyTeamDTO적용 및 예외처리
     * 22.03.29 인준 : 권한어노테이션 추가
     */
    @Auth( level = AuthLevel.TEAM_MANAGER )
    @PatchMapping("/{teamSeq}/joinRequestFrom/{teamJoinRequestSeq}/rejection")
    public ResponseEntity<?> rejectJoinRequest(
            @PathVariable Long teamSeq,
            @PathVariable Long teamJoinRequestSeq
    ) {
        CmnMyTeamDTO joinRequest = new CmnMyTeamDTO()
                .teamSeq(teamSeq)
                .teamJoinRequestSeq(teamJoinRequestSeq);

        teamMemberManagerService.rejectJoinRequest(joinRequest);
        return RESPONSE_OK;
    }

    /**
     * API011 소속팀 개인프로필 조회
     */
    @Auth( level = AuthLevel.TEAM_MEMBER )
    @GetMapping("/{teamSeq}/profile")
    public ResponseEntity<GetMyTeamProfileResponse> getMyTeamProfile(
            @SessionAttribute(value = LOGIN_USER,required = false) SessionUser userSession,
            @PathVariable Long teamSeq
    ) {

        GetMyTeamProfileRequest reqBody = new GetMyTeamProfileRequest(
                userSession.getUserSeq()
              , teamSeq
        );
        MyTeamProfileDTO myTeamProfile = teamMemberService.getMyTeamProfile( reqBody );
        return ResponseEntity.ok( new GetMyTeamProfileResponse().toResponse( myTeamProfile ) );
    }

    /**
     * API012 소속팀 개인프로필 수정
     */
    @Auth( level = AuthLevel.TEAM_MEMBER )
    @PostMapping("/{teamSeq}/profile")
    public ResponseEntity<?> modifyMyTeamsProfile(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser userSession,
            @PathVariable Long teamSeq,
            @RequestPart(required = false) String backNumber,
            @RequestPart(required = false) MultipartFile imageFile
    ) {
        teamMemberService.modifyMyTeamProfile(
            new ModifyMyTeamProfileRequest(
                    userSession.getUserSeq()
                  , teamSeq
                  , backNumber
                  , imageFile
            )
        );
        return RESPONSE_OK;
    }


    /**
     * API013 소속팀
     * TODO 테스트 필요 FrontEnd에서 호출 하지 않음.
     */
    @Auth( level = AuthLevel.TEAM_MEMBER )
    @DeleteMapping("/{teamSeq}/profile")
    public ResponseEntity<?> deleteMyTeamProfile(
            @SessionAttribute(value = LOGIN_USER,required = false) SessionUser userSession,
            @PathVariable Long teamSeq
    ) {
        FindMyTeamProfileDTO findMyTeamProfileDTO = new FindMyTeamProfileDTO()
                                                            .userSeq( userSession.getUserSeq() )
                                                            .teamSeq( teamSeq );
        teamMemberService.deleteMyTeamProfile(findMyTeamProfileDTO);
        return RESPONSE_OK;
    }

    /**
     * API014 : 소속팀 목록 조회
     */
    @Auth( level = AuthLevel.TEAM_MEMBER )
    @GetMapping
    public ResponseEntity<GetMyTeamsResponse> getMyTeams(
            @SessionAttribute( value = LOGIN_USER ) SessionUser sessionUser,
            @RequestParam( name = "pageNo", defaultValue = "0" ) Integer pageNo
    ) {
        GetMyTeamsResponse myTeamList = myTeamService.findTeams(
            new GetMyTeamsRequest( sessionUser.getUserSeq(), pageNo )
        );

        return ResponseEntity.ok().body( myTeamList );
    }

    /**
     * API015 : 소속팀 관리자 제명
     * 22.03.08 인준 : CustomException적용 - 퇴장상태로 업데이트된 결과가 없을 때 USER_NOT_FOUND 예외 발생
     * 22.03.29 인준 : 권한어노테이션 추가
     */
    @Auth( level = AuthLevel.TEAM_LEADER )
    @DeleteMapping("/{teamSeq}/members/{teamMemberSeq}/manager")
    public ResponseEntity<?> dismissManager(
            @PathVariable Long teamSeq,
            @PathVariable Long teamMemberSeq
    ) {
        CmnMyTeamDTO teamMemberKeys = new CmnMyTeamDTO()
                .teamMemberSeq(teamMemberSeq)
                .teamSeq(teamSeq);
        teamMemberManagerService.dismissManager(teamMemberKeys);
        return RESPONSE_OK;
    }

    /**
     * API016 : 소속팀 정보 조회
     */
    @Auth( level = AuthLevel.TEAM_MEMBER )
    @GetMapping("/{teamSeq}/info")
    public ResponseEntity<GetTeamInfoResponse> getTeamInfo(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser userSession,
            @PathVariable(value = "teamSeq") Long teamSeq
    ) {
        return ResponseEntity.ok().body( myTeamService.getTeamInfo( teamSeq, userSession.getUserSeq() ) );
    }

    /**
     * API017 : 소속팀 정보 수정
     * 23.10.28 인준 : 팀 이미지 속성 추가 반영 ( @RequestPart를 적용하여 mulitpart/form 데이터의 객체 바인딩 제공 )
     */
    @Auth( level = AuthLevel.TEAM_MANAGER )
    @PostMapping( "/{teamSeq}/info" )
    public ResponseEntity<?> modifyMyTeamInfo(
            @PathVariable( value = "teamSeq" ) Long teamSeq,
            @RequestPart( value = "teamLogo", required = false ) MultipartFile teamLogo,
            @RequestPart( value = "teamInfo", required = false ) ModifyMyTeamInfoRequest teamInfo
    ) {

        myTeamService.modifyMyTeamInfo( new ModifyMyTeamInfoRequest( teamSeq, teamInfo ), teamLogo );

        return RESPONSE_OK;
    }

    /**
     * API018 : 소속팀 정보 삭제
     */
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
