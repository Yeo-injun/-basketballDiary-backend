package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.interceptor.Auth;
import com.threeNerds.basketballDiary.mvc.dto.PlayerDTO;
import com.threeNerds.basketballDiary.mvc.dto.PlayerSearchDTO;
import com.threeNerds.basketballDiary.mvc.dto.loginUser.userTeamManager.JoinRequestDTO;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.*;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.myTeam.MemberDTO;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.myTeam.MyTeamDTO;
import com.threeNerds.basketballDiary.mvc.service.MyTeamService;
import com.threeNerds.basketballDiary.mvc.service.TeamMemberManagerService;
import com.threeNerds.basketballDiary.mvc.service.TeamMemberService;
import com.threeNerds.basketballDiary.session.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.threeNerds.basketballDiary.constant.Constant.*;
import static com.threeNerds.basketballDiary.session.SessionConst.LOGIN_MEMBER;
import static com.threeNerds.basketballDiary.utils.HttpResponses.RESPONSE_OK;

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

    private final MyTeamService myTeamService;
    private final TeamMemberService teamMemberService;
    private final TeamMemberManagerService teamMemberManagerService;


    /**
     * API001 : 소속팀 운영진 조회
     */
    @Auth(GRADE = TEAM_MEMBER)
    @GetMapping("/{teamSeq}/managers")
    public ResponseEntity<List<MemberDTO>> searchManagers(
            @SessionAttribute(value = LOGIN_MEMBER, required = false) SessionUser sessionUser,
            @PathVariable(value = "teamSeq") Long teamSeq
    ) {
        log.info("▒▒▒▒▒ API001: MyTeamController.searchManagers");
        List<MemberDTO> managerList = myTeamService.findManagers(teamSeq);

        return ResponseEntity.ok().body(managerList);
    }

    /**
     * API002 : 소속팀 팀원목록 조회
     */
    @Auth(GRADE = TEAM_MEMBER)
    @GetMapping("/{teamSeq}/members")
    public ResponseEntity<List<MemberDTO>> searchMembers(
            @SessionAttribute(value = LOGIN_MEMBER, required = false) SessionUser sessionUser,
            @PathVariable(value = "teamSeq") Long teamSeq,
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo
    ) {
        log.info("▒▒▒▒▒ API002: MyTeamController.searchMembers");
        List<MemberDTO> memberList = myTeamService.findMembers(teamSeq, pageNo);

        return ResponseEntity.ok().body(memberList);
    }

    /**
     * API003 : 소속팀 관리자임명
     * 22.03.08 인준 : CustomException적용 - 퇴장상태로 업데이트된 결과가 없을 때 USER_NOT_FOUND 예외 발생
     * 22.03.29 인준 : 권한어노테이션 추가
     */
    @Auth(GRADE = LEADER)
    @PostMapping("{teamSeq}/members/{teamMemberSeq}/manager")
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
    public ResponseEntity<?> removeTeamMember(
            @PathVariable Long teamSeq,
            @PathVariable Long teamMemberSeq
    ) {
        CmnMyTeamDTO teamMemberKey = new CmnMyTeamDTO()
                                .teamSeq(teamSeq)
                                .teamMemberSeq(teamMemberSeq);
        teamMemberManagerService.removeTeamMember(teamMemberKey);
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
     * API011 소속팀 개인프로필 수정데이터 조회
     */
    @GetMapping("/{teamSeq}/profile")
    public ResponseEntity<ResponseMyTeamProfileDTO> findMyTeamsProfile(
            @SessionAttribute(value = LOGIN_MEMBER,required = false) SessionUser sessionDTO,
            @PathVariable Long teamSeq){

        Long id = sessionDTO.getUserSeq();

        FindMyTeamProfileDTO findMyTeamProfileDTO = new FindMyTeamProfileDTO()
                .userSeq(id)
                .teamSeq(teamSeq);

        ResponseMyTeamProfileDTO myTeamProfileDTO = teamMemberService.findProfile(findMyTeamProfileDTO);

        return ResponseEntity.ok(myTeamProfileDTO);
    }

    /**
     * API012 소속팀 개인프로필 수정
     */
    @PatchMapping("/{teamSeq}/profile")
    public ResponseEntity<?> modifyMyTeamsProfile(
            @SessionAttribute(value = LOGIN_MEMBER,required = false) SessionUser sessionDTO,
            @PathVariable Long teamSeq,
            @RequestBody BackNumber backNumber){

        Long id = sessionDTO.getUserSeq();

        FindMyTeamProfileDTO findMyTeamProfileDTO = new FindMyTeamProfileDTO()
                .userSeq(id)
                .teamSeq(teamSeq);

        ModifyMyTeamProfileDTO myTeamProfileDTO = new ModifyMyTeamProfileDTO()
                .findMyTeamProfileDTO(findMyTeamProfileDTO)
                .backNumber(backNumber.getBackNumber());

        teamMemberService.updateMyTeamProfile(myTeamProfileDTO);
        return RESPONSE_OK;
    }

    /**
     * API013 소속팀 탈퇴
     */
    @DeleteMapping("/{teamSeq}/profile")
    public ResponseEntity<?> deleteMyTeamsProfile(
            @SessionAttribute(value = LOGIN_MEMBER,required = false) SessionUser sessionDTO,
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
    @GetMapping
    public ResponseEntity<List<MyTeamDTO>> searchTeams(
            @SessionAttribute(value = LOGIN_MEMBER, required = false) SessionUser sessionUser
    ) {
        log.info("▒▒▒▒▒ API014: MyTeamController.searchTeams");
        Long userSeq = sessionUser.getUserSeq();
        List<MyTeamDTO> myTeamList = myTeamService.findTeams(userSeq);

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
            @SessionAttribute(value = LOGIN_MEMBER, required = false) SessionUser sessionUser,
            @PathVariable(value = "teamSeq") Long teamSeq
    ) {
        log.info("▒▒▒▒▒ API016: MyTeamController.searchTeam");
        Long userSeq = sessionUser.getUserSeq();
        MyTeamDTO myTeam = myTeamService.findTeam(userSeq, teamSeq);

        return ResponseEntity.ok().body(myTeam);
    }

    /**
     * API017 : 소속팀 정보 수정
     */
    @Auth(GRADE = MANAGER)
    @PutMapping("/{teamSeq}/info")
    public ResponseEntity<?> modifyMyTeam(
            @SessionAttribute(value = LOGIN_MEMBER, required = false) SessionUser sessionUser,
            @PathVariable(value = "teamSeq") Long teamSeq,
            @RequestBody MyTeamDTO dto
    ) {
        log.info("▒▒▒▒▒ API017: MyTeamController.modifyMyTeam");
        Long userSeq = sessionUser.getUserSeq();
        myTeamService.modifyMyTeam(teamSeq, dto);
        // MyTeamDTO myTeam = myTeamService.findTeam(userSeq, teamSeq);

        //return new ResponseEntity<>(HttpStatus.OK); 과 동일...
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

}
