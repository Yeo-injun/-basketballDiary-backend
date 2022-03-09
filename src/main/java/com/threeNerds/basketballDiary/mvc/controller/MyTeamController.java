package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.exception.AlreadyExsitException;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.NotExistException;
import com.threeNerds.basketballDiary.interceptor.Auth;
import com.threeNerds.basketballDiary.mvc.dto.*;
import com.threeNerds.basketballDiary.mvc.service.MyTeamService;
import com.threeNerds.basketballDiary.mvc.service.TeamMemberManagerService;
import com.threeNerds.basketballDiary.mvc.service.TeamMemberService;
import com.threeNerds.basketballDiary.session.SessionUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.threeNerds.basketballDiary.session.SessionConst.LOGIN_MEMBER;
import static com.threeNerds.basketballDiary.utils.HttpResponses.*;

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
    @Auth(GRADE = 2L)
    @GetMapping("/{teamSeq}/managers")
    public ResponseEntity<List<MemberDTO>> searchManagers(
            @SessionAttribute(value = LOGIN_MEMBER, required = false) SessionUser sessionUser,
            @PathVariable(value = "teamSeq") Long teamSeq
    ) {
        List<MemberDTO> managerList = myTeamService.findManagers(teamSeq);

        return ResponseEntity.ok().body(managerList);
    }

    /**
     * API002 : 소속팀 팀원목록 조회
     */
    @Auth(GRADE = 2L)
    @GetMapping("/{teamSeq}/members")
    public ResponseEntity<List<MemberDTO>> searchMembers(
            @SessionAttribute(value = LOGIN_MEMBER, required = false) SessionUser sessionUser,
            @PathVariable(value = "teamSeq") Long teamSeq,
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo
    ) {
        List<MemberDTO> memberList = myTeamService.findMembers(teamSeq, pageNo);

        return ResponseEntity.ok().body(memberList);
    }

    /**
     * API003 : 소속팀 관리자임명
     * 22.03.08 인준 : CustomException적용 - 퇴장상태로 업데이트된 결과가 없을 때 USER_NOT_FOUND 예외 발생
     */
    @PostMapping("{teamSeq}/members/{teamMemberSeq}/manager")
    public ResponseEntity<?> appointManager (
            @PathVariable Long teamSeq,
            @PathVariable Long teamMemberSeq
    ) {
        KeyDTO.TeamMember teamMemberKey = new KeyDTO.TeamMember()
                .teamSeq(teamSeq)
                .teamMemberSeq(teamMemberSeq);

        teamMemberManagerService.appointManager(teamMemberKey);
        return RESPONSE_OK;
    }

    /**
     * API004 : 소속팀 회원 강퇴시키기
     * 22.03.08 인준 : CustomException적용 - 퇴장상태로 업데이트된 결과가 없을 때 USER_NOT_FOUND 예외 발생
     */
    @DeleteMapping("{teamSeq}/members/{teamMemberSeq}")
    public ResponseEntity<?> removeTeamMember(
            @PathVariable Long teamSeq,
            @PathVariable Long teamMemberSeq
    ) {
        KeyDTO.TeamMember teamMemberKey = new KeyDTO.TeamMember()
                                .teamSeq(teamSeq)
                                .teamMemberSeq(teamMemberSeq);
        teamMemberManagerService.removeTeamMember(teamMemberKey);
        return RESPONSE_OK;
    }

    /**
     * API005 : 소속팀의 초대한 선수목록 조회
     */
    @GetMapping("/{teamSeq}/joinRequestTo") // TODO URL 마지막에 /users 추가하는 것은 어떤지 (22.02.20 인준의견)
    public ResponseEntity<?> searchInvitedPlayer(
            @PathVariable Long teamSeq,
            @RequestParam(name = "state", defaultValue = "00") String joinRequestStateCode
    ) {
        PlayerSearchDTO searchCond = new PlayerSearchDTO()
                                            .teamSeq(teamSeq)
                                            .joinRequestStateCode(joinRequestStateCode);

        List<PlayerDTO> playerList = teamMemberManagerService.searchInvitedPlayer(searchCond);
        return ResponseEntity.ok(playerList);
    }

    /**
     * API007 : 소속팀의 선수초대
     */
    @PostMapping("/{teamSeq}/joinRequestTo/{userSeq}")
    public ResponseEntity<?> inviteTeamMember(
            @PathVariable Long teamSeq,
            @PathVariable Long userSeq
    ) {
        JoinRequestDTO joinRequest = new JoinRequestDTO()
                                            .teamSeq(teamSeq)
                                            .userSeq(userSeq);

        teamMemberManagerService.inviteTeamMember(joinRequest);
        return RESPONSE_OK;
        // TODO 예외처리 반영
    }

    /**
     * API008 : 소속팀이 받은 가입요청목록 조회
     */
    @GetMapping("/{teamSeq}/joinRequestFrom") // TODO URL 마지막에 /users 추가하는 것은 어떤지 (22.02.20 인준의견)
    public ResponseEntity<?> searchJoinRequestPlayer(
            @PathVariable Long teamSeq,
            @RequestParam(name = "state", defaultValue = "00") String joinRequestStateCode
    ) {
        PlayerSearchDTO searchCond = new PlayerSearchDTO()
                .teamSeq(teamSeq)
                .joinRequestStateCode(joinRequestStateCode);

        List<PlayerDTO> playerList = teamMemberManagerService.searchJoinRequestPlayer(searchCond);
        return ResponseEntity.ok(playerList);
    }

    /**
     * API009 : 소속팀이 사용자의 가입요청 승인
     */
    @PatchMapping("/{teamSeq}/joinRequestFrom/{teamJoinRequestSeq}/approval")
    public ResponseEntity<?> approveJoinRequest(
            @PathVariable Long teamJoinRequestSeq,
            @PathVariable Long teamSeq
    ) {
        JoinRequestDTO joinRequest = new JoinRequestDTO()
                .teamJoinRequestSeq(teamJoinRequestSeq)
                .teamSeq(teamSeq);
        try
        {
            teamMemberManagerService.approveJoinRequest(joinRequest);
            return RESPONSE_CREATED;
        }
        catch (AlreadyExsitException | NotExistException e)
        {
            // TODO 참고자료(왜 409에러로 처리했는지) : https://deveric.tistory.com/62
            return RESPONSE_CONFLICT;
        }
    }

    /**
     * API010 : 소속팀의 가입요청 거절
     */
    @PatchMapping("/{teamSeq}/joinRequestFrom/{teamJoinRequestSeq}/rejection")
    public ResponseEntity<?> rejectJoinRequest(
            @PathVariable Long teamSeq,
            @PathVariable Long teamJoinRequestSeq
    ) {
        JoinRequestDTO joinRequest = new JoinRequestDTO()
                .teamSeq(teamSeq)
                .teamJoinRequestSeq(teamJoinRequestSeq);

        teamMemberManagerService.rejectJoinRequest(joinRequest);
        return RESPONSE_OK;
        // TODO 예외처리 반영
    }
/*****************************************************************************************************************/
    /**
     * API011 소속팀 개인프로필 수정데이터 조회
     */
    @GetMapping("/{teamSeq}/profile")
    public ResponseEntity<ResponseMyTeamProfileDTO> findMyTeamsProfile(
            @SessionAttribute(value = LOGIN_MEMBER,required = false) SessionUser sessionDTO,
            @PathVariable Long teamSeq){

        Long id = sessionDTO.getUserSeq();

        MyTeamController.FindMyTeamProfileDTO findMyTeamProfileDTO = new MyTeamController
                .FindMyTeamProfileDTO()
                .userSeq(id)
                .teamSeq(teamSeq);

        ResponseMyTeamProfileDTO myTeamProfileDTO = teamMemberService.findProfile(findMyTeamProfileDTO);

        return ResponseEntity.ok(myTeamProfileDTO);
    }
    @Getter
    public static class FindMyTeamProfileDTO {
        private Long userSeq;
        private Long teamSeq;

        public MyTeamController.FindMyTeamProfileDTO userSeq(Long userSeq){
            this.userSeq=userSeq;
            return this;
        }
        public MyTeamController.FindMyTeamProfileDTO teamSeq(Long teamSeq){
            this.teamSeq=teamSeq;
            return this;
        }
    }

    /**
     * API012 소속팀 개인프로필 수정
     */
    @PatchMapping("/{teamSeq}/profile")
    public ResponseEntity<?> modifyMyTeamsProfile(
            @SessionAttribute(value = LOGIN_MEMBER,required = false) SessionUser sessionDTO,
            @PathVariable Long teamSeq,
            @RequestBody MyTeamController.BackNumber backNumber){

        Long id = sessionDTO.getUserSeq();

        MyTeamController.FindMyTeamProfileDTO findMyTeamProfileDTO = new MyTeamController.FindMyTeamProfileDTO()
                .userSeq(id)
                .teamSeq(teamSeq);

        MyTeamController.ModifyMyTeamProfileDTO myTeamProfileDTO = new MyTeamController.ModifyMyTeamProfileDTO()
                .findMyTeamProfileDTO(findMyTeamProfileDTO)
                .backNumber(backNumber.getBackNumber());

        teamMemberService.updateMyTeamProfile(myTeamProfileDTO);
        return RESPONSE_OK;
    }

    @Getter
    public static class BackNumber{
        private String backNumber;
    }

    @Getter
    public static class ModifyMyTeamProfileDTO{
        private MyTeamController.FindMyTeamProfileDTO findMyTeamProfileDTO;
        private String backNumber;

        public MyTeamController.ModifyMyTeamProfileDTO findMyTeamProfileDTO(MyTeamController.FindMyTeamProfileDTO findMyTeamProfileDTO){
            this.findMyTeamProfileDTO = findMyTeamProfileDTO;
            return this;
        }
        public MyTeamController.ModifyMyTeamProfileDTO backNumber(String backNumber){
            this.backNumber = backNumber;
            return this;
        }
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

        MyTeamController.FindMyTeamProfileDTO findMyTeamProfileDTO = new MyTeamController.FindMyTeamProfileDTO()
                .userSeq(id)
                .teamSeq(teamSeq);
        teamMemberService.deleteMyTeamProfile(findMyTeamProfileDTO);
        return RESPONSE_OK;
    }

    /********************************************************************************************************/

    /**
     * API014 : 소속팀 목록 조회
     */
    @GetMapping
    public ResponseEntity<List<MyTeamDTO>> searchTeams(
            @SessionAttribute(value = LOGIN_MEMBER, required = false) SessionUser sessionUser
    ) {
        Long userSeq = sessionUser.getUserSeq();
        List<MyTeamDTO> myTeamList = myTeamService.findTeams(userSeq);

        return ResponseEntity.ok().body(myTeamList);
    }

    /**
     * API015 : 소속팀 관리자 제명
     * 22.03.08 인준 : CustomException적용 - 퇴장상태로 업데이트된 결과가 없을 때 USER_NOT_FOUND 예외 발생
     */
    @DeleteMapping("/{teamSeq}/members/{teamMemberSeq}/manager")
    public ResponseEntity<?> dismissManager(
            @PathVariable Long teamSeq,
            @PathVariable Long teamMemberSeq
    ) {
        KeyDTO.TeamMember teamMemberKeys = new KeyDTO.TeamMember()
                .teamMemberSeq(teamMemberSeq)
                .teamSeq(teamSeq);
        teamMemberManagerService.dismissManager(teamMemberKeys);
        return RESPONSE_OK;
    }

    /**
     * API016 : 소속팀 정보 단건 조회
     */
    @Auth(GRADE = 2L)
    @GetMapping("/{teamSeq}/info")
    public ResponseEntity<MyTeamDTO> searchTeam(
            @SessionAttribute(value = LOGIN_MEMBER, required = false) SessionUser sessionUser,
            @PathVariable(value = "teamSeq") Long teamSeq
    ) {
        Long userSeq = sessionUser.getUserSeq();
        MyTeamDTO myTeam = myTeamService.findTeam(userSeq, teamSeq);

        return ResponseEntity.ok().body(myTeam);
    }

    /**
     * API017 : 소속팀 정보 수정
     */
    @Auth(GRADE = 2L)
    @PutMapping("/{teamSeq}/info")
    public ResponseEntity<?> modifyMyTeam(
            @SessionAttribute(value = LOGIN_MEMBER, required = false) SessionUser sessionUser,
            @PathVariable(value = "teamSeq") Long teamSeq,
            @RequestBody MyTeamDTO dto
    ) {
        Long userSeq = sessionUser.getUserSeq();
        myTeamService.modifyMyTeam(teamSeq, dto);
        // MyTeamDTO myTeam = myTeamService.findTeam(userSeq, teamSeq);

        //return new ResponseEntity<>(HttpStatus.OK); 과 동일...
        return RESPONSE_OK;
    }

    /**
     * API018 : 소속팀 정보 삭제
     */
    @Auth(GRADE = 4L)
    @DeleteMapping("/{teamSeq}")
    public ResponseEntity<?> removeMyTeam(
            @PathVariable(value = "teamSeq") Long teamSeq
    ) {
        myTeamService.deleteMyTeam(teamSeq);

        return ResponseEntity.noContent().build();
    }

}
