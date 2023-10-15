package com.threeNerds.basketballDiary.mvc.myTeam.controller;

import com.threeNerds.basketballDiary.file.ImageUploader;
import com.threeNerds.basketballDiary.interceptor.Auth;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getManagers.request.GetManagersRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getManagers.response.GetManagersResponse;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getMyTeamProfile.request.GetMyTeamProfileRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getMyTeamProfile.response.GetMyTeamProfileResponse;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getMyTeamProfile.response.MyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getTeamMembers.request.GetTeamMembersRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getTeamMembers.response.GetTeamMembersResponse;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.modifyMyTeamProfile.request.ModifyMyTeamProfileRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.searchAllTeamMembers.request.SearchAllTeamMembersRequest;
import com.threeNerds.basketballDiary.mvc.team.dto.PlayerDTO;
import com.threeNerds.basketballDiary.mvc.game.service.GameRecordManagerService;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.*;
import com.threeNerds.basketballDiary.pagination.PaginatedMyTeamDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.searchAllTeamMembers.response.SearchAllTeamMembersResponse;
import com.threeNerds.basketballDiary.mvc.myTeam.service.MyTeamService;
import com.threeNerds.basketballDiary.mvc.myTeam.service.TeamMemberManagerService;
import com.threeNerds.basketballDiary.mvc.myTeam.service.TeamMemberService;
import com.threeNerds.basketballDiary.session.SessionUser;
import com.threeNerds.basketballDiary.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.threeNerds.basketballDiary.constant.UserAuthConst.*;
import static com.threeNerds.basketballDiary.constant.HttpResponseConst.RESPONSE_OK;
import static com.threeNerds.basketballDiary.utils.SessionUtil.LOGIN_USER;

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

    /**--------------------------------------
     * Components
     **--------------------------------------*/
    private final ImageUploader imageUploader;

    /**
     * API001 : 소속팀 운영진 조회
     * 23.05.14. Request & Response 형 변경
     */
    @Auth(GRADE = TEAM_MEMBER)
    @GetMapping("/{teamSeq}/managers")
    public ResponseEntity<GetManagersResponse> getManagers(
            @PathVariable(value = "teamSeq") Long teamSeq
    ) {
        GetManagersRequest reqBody = new GetManagersRequest( teamSeq );
        GetManagersResponse resBody = myTeamService.getManagers( reqBody );
        return ResponseEntity.ok().body(resBody);
    }

    /**
     * API002 : 소속팀 팀원등급인 팀원 목록 조회
     */
    @Auth(GRADE = TEAM_MEMBER)
    @GetMapping("/{teamSeq}/members")
    public ResponseEntity<GetTeamMembersResponse> getTeamMembers(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser sessionUser,
            @PathVariable(value = "teamSeq") Long teamSeq,
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo
    ) {
        GetTeamMembersRequest reqBody = new GetTeamMembersRequest(teamSeq, pageNo);
        GetTeamMembersResponse resBody = myTeamService.getTeamMembers(reqBody);
        return ResponseEntity.ok().body(resBody);
    }

    /**
     * API036 : 소속팀 전체 팀원목록 검색
     */
    @Auth(GRADE = TEAM_MEMBER)
    @GetMapping("/{teamSeq}/allTeamMembers")
    public ResponseEntity<SearchAllTeamMembersResponse> searchAllTeamMembers(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser sessionUser,
            @PathVariable(value = "teamSeq") Long teamSeq,
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "playerName", required = false) String playerName
    ) {
        log.info("▒▒▒▒▒ API002: MyTeamController.searchMembers");
        SearchAllTeamMembersRequest reqBody = new SearchAllTeamMembersRequest(teamSeq, pageNo, playerName);
        SearchAllTeamMembersResponse resBody = myTeamService.searchAllTeamMembers(reqBody);

        return ResponseEntity.ok().body(resBody);
    }

    /**
     * API003 : 소속팀 관리자임명
     * 22.03.08 인준 : CustomException적용 - 퇴장상태로 업데이트된 결과가 없을 때 USER_NOT_FOUND 예외 발생
     * 22.03.29 인준 : 권한어노테이션 추가
     */
    @Auth(GRADE = LEADER)
    @PatchMapping("{teamSeq}/members/{teamMemberSeq}/manager")
    public ResponseEntity<?> appointManager (
            @PathVariable Long teamSeq,
            @PathVariable Long teamMemberSeq
    ) {
        CmnMyTeamDTO teamMemberKey = new CmnMyTeamDTO()
                .teamSeq(teamSeq)
                .teamMemberSeq(teamMemberSeq);

        teamMemberManagerService.appointManager(teamMemberKey);
        return RESPONSE_OK;
    }

    /**
     * API004 : 소속팀 회원 강퇴시키기
     * 22.03.08 인준 : CustomException적용 - 퇴장상태로 업데이트된 결과가 없을 때 USER_NOT_FOUND 예외 발생
     * 22.03.29 인준 : 권한어노테이션 추가
     */
    @Auth(GRADE = LEADER)
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
    @Auth(GRADE = MANAGER)
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
    @Auth(GRADE = MANAGER)
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
    @Auth(GRADE = MANAGER)
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
    @Auth(GRADE = MANAGER)
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
    @Auth(GRADE = MANAGER)
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
    @PostMapping("/{teamSeq}/profile")
    public ResponseEntity<?> modifyMyTeamsProfile(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser userSession,
            @PathVariable Long teamSeq,
            @ModelAttribute ModifyMyTeamProfileRequest reqBody
    ) {
        teamMemberService.modifyMyTeamProfile(
            new ModifyMyTeamProfileRequest(
                    userSession.getUserSeq()
                  , teamSeq
                  , reqBody.getBackNumber()
                  , reqBody.getImageFile()
            )
        );
        return RESPONSE_OK;
    }


    /**
     * API013 소속팀 탈퇴
     */
    @DeleteMapping("/{teamSeq}/profile")
    public ResponseEntity<?> deleteMyTeamsProfile(
            @SessionAttribute(value = LOGIN_USER,required = false) SessionUser sessionDTO,
            @PathVariable Long teamSeq)
    {
        Long id = sessionDTO.getUserSeq();

        FindMyTeamProfileDTO findMyTeamProfileDTO = new FindMyTeamProfileDTO()
                .userSeq(id)
                .teamSeq(teamSeq);
        teamMemberService.deleteMyTeamProfile(findMyTeamProfileDTO);
        return RESPONSE_OK;
    }

    /**
     * API014 : 소속팀 목록 조회
     */
    @Auth(GRADE = TEAM_MEMBER)
    @GetMapping
    public ResponseEntity<PaginatedMyTeamDTO> searchMyTeams(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser sessionUser,
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo
    ) {
        log.info("▒▒▒▒▒ API014: MyTeamController.searchTeams");
        Long userSeq = SessionUtil.getUserSeq();

        SearchMyTeamDTO searchMyTeamDTO = new SearchMyTeamDTO()
                 .pageNo(pageNo)
                 .userSeq(userSeq);
        PaginatedMyTeamDTO myTeamList = myTeamService.findTeams(searchMyTeamDTO);

        return ResponseEntity.ok().body(myTeamList);
    }

    /**
     * API015 : 소속팀 관리자 제명
     * 22.03.08 인준 : CustomException적용 - 퇴장상태로 업데이트된 결과가 없을 때 USER_NOT_FOUND 예외 발생
     * 22.03.29 인준 : 권한어노테이션 추가
     */
    @Auth(GRADE = LEADER)
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
     * API016 : 소속팀 정보 단건 조회
     */
    @Auth(GRADE = TEAM_MEMBER)
    @GetMapping("/{teamSeq}/info")
    public ResponseEntity<MyTeamDTO> searchTeam(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser sessionUser,
            @PathVariable(value = "teamSeq") Long teamSeq
    ) {
        log.info("▒▒▒▒▒ API016: MyTeamController.searchTeam");
        Long userSeq = sessionUser.getUserSeq();
        FindMyTeamProfileDTO paramDTO = new FindMyTeamProfileDTO()
                .teamSeq(teamSeq)
                .userSeq(userSeq);
        MyTeamDTO myTeam = myTeamService.findTeam(paramDTO);

        return ResponseEntity.ok().body(myTeam);
    }

    /**
     * API017 : 소속팀 정보 수정
     */
    @Auth(GRADE = MANAGER)
    @PostMapping("/{teamSeq}/info")
    public ResponseEntity<?> modifyMyTeam(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser sessionUser,
            @PathVariable(value = "teamSeq") Long teamSeq,
            @RequestBody MyTeamDTO dto
    ) {
        log.info("▒▒▒▒▒ API017: MyTeamController.modifyMyTeam");
        Long userSeq = sessionUser.getUserSeq();
        myTeamService.modifyMyTeam(teamSeq, dto);

        return RESPONSE_OK;
    }

    /**
     * API018 : 소속팀 정보 삭제
     */
    @Auth(GRADE = LEADER)
    @DeleteMapping("/{teamSeq}")
    public ResponseEntity<?> removeMyTeam(
            @PathVariable(value = "teamSeq") Long teamSeq
    ) {
        log.info("▒▒▒▒▒ API018: MyTeamController.removeMyTeam");
        myTeamService.deleteMyTeam(teamSeq);

        // 삭제가 정상적으로 완료된 경우 204 No Content로 응답한다.
        return ResponseEntity.noContent().build();
    }

    /**
     * API052 : 소속팀 게임목록조회
     */
    @GetMapping("/{teamSeq}/games")
    public ResponseEntity<?> searchMyTeamGames (
            @SessionAttribute( value = LOGIN_USER ) SessionUser sessionUser,
            @PathVariable( value = "teamSeq" ) Long teamSeq,
            @RequestParam( name = "gameBgngYmd"     , required = false ) String gameBgngYmd     ,
            @RequestParam( name = "gameEndYmd"      , required = false ) String gameEndYmd      ,
            @RequestParam( name = "sidoCode"        , required = false ) String sidoCode        ,
            @RequestParam( name = "gamePlaceName"   , required = false ) String gamePlaceName   ,
            @RequestParam( name = "gameTypeCode"    , required = false )  String gameTypeCode   ,
            @RequestParam( name = "homeAwayCode"    , required = false ) String homeAwayCode
    ) {
        log.info("▒▒▒▒▒ API052: MyTeamController.searchMyTeamGames");
        GameCondDTO condDTO = new GameCondDTO()
                                    .userSeq(sessionUser.getUserSeq())
                                    .teamSeq(teamSeq)
                                    .gameBgngYmd(gameBgngYmd)
                                    .gameEndYmd(gameEndYmd)
                                    .sidoCode(sidoCode)
                                    .gamePlaceName(gamePlaceName)
                                    .gameTypeCode(gameTypeCode)
                                    .homeAwayCode(homeAwayCode);

        List<GameRecordDTO> myTeamGames = gameRecordManagerService.searchMyTeamGames(condDTO);
        return ResponseEntity.ok(myTeamGames);
    }

}
