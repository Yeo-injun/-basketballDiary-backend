package com.threeNerds.basketballDiary.mvc.authUser.controller;

import com.threeNerds.basketballDiary.auth.Auth;
import com.threeNerds.basketballDiary.mvc.authUser.controller.request.UpdateProfileRequest;
import com.threeNerds.basketballDiary.mvc.authUser.service.AuthUserService;
import com.threeNerds.basketballDiary.mvc.authUser.service.TeamJoinService;
import com.threeNerds.basketballDiary.mvc.authUser.service.dto.TeamInvitationCommand;
import com.threeNerds.basketballDiary.mvc.authUser.service.dto.JoinRequestCommand;
import com.threeNerds.basketballDiary.mvc.authUser.service.dto.JoinRequestQuery;
import com.threeNerds.basketballDiary.mvc.authUser.service.dto.TeamInvitationQuery;
import com.threeNerds.basketballDiary.mvc.myTeam.service.MyTeamAuthService;
import com.threeNerds.basketballDiary.mvc.authUser.dto.PasswordUpdateDTO;
import com.threeNerds.basketballDiary.mvc.authUser.dto.JoinRequestDTO;
import com.threeNerds.basketballDiary.mvc.authUser.dto.UpdateUserDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.service.dto.TeamAuthDTO;
import com.threeNerds.basketballDiary.mvc.user.dto.UserDTO;
import com.threeNerds.basketballDiary.session.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.threeNerds.basketballDiary.session.util.SessionUtil.LOGIN_USER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/loginUser")
public class AuthUserController {

    private final AuthUserService authUserService;

    private final MyTeamAuthService myTeamAuthService;

    private final TeamJoinService teamJoinService;

    /**
     *  API020 : 농구팀 가입요청 보내기
     *  22.03.25 인준 : SessionUser null체크후 예외처리 적용. Service Layer에서의 예외처리 적용
     *  22.03.26 인준 : SessionUser null체크 로직 제거 - 인터셉터에서 하기 때문.
     *  22.03.29 인준 : 권한어노테이션 추가
     **/
    @Auth
    @PostMapping("/joinRequestTo/{teamSeq}")
    public ResponseEntity<Void> sendRequest (
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser userSession,
            @PathVariable Long teamSeq
    ) {
        teamJoinService.sendRequest( JoinRequestCommand.ofCreation( teamSeq, userSession.getUserSeq() ) );
        return ResponseEntity.ok().build();
    }

    /**
     *  API023 : 팀 가입요청 취소
     *  22.03.29 인준 : 권한어노테이션 추가
     **/
    @Auth
    @DeleteMapping("/joinRequestsTo/{teamJoinRequestSeq}")
    public ResponseEntity<?> cancelRequest (
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser userSession,
            @PathVariable Long teamJoinRequestSeq
    ) {
        teamJoinService.cancelRequest( JoinRequestCommand.ofCancel( teamJoinRequestSeq, userSession.getUserSeq() ) );
        return ResponseEntity.ok().build();
    }

    /**
     *  API022 : 농구팀 가입요청 목록 조회
     *  22.03.13 인준 : API022 세분화 - 가입요청 및 초대 목록을 하나의 API콜로 가져오는 것에서 API 2개를 콜해서 가져오는 구조로 변경
     *  22.03.26 인준 : SessionUser null체크 로직 제거 - 인터셉터에서 체크하기 때문
     *  22.03.29 인준 : 권한어노테이션 추가
     **/
    @Auth
    @GetMapping("/joinRequestsTo")
    public ResponseEntity<?> getJoinRequests (
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser userSession
    ) {
        List<JoinRequestDTO> result = teamJoinService.getJoinRequests( JoinRequestQuery.of( userSession.getUserSeq() ) );
        return ResponseEntity.ok().body(result);
    }

    /**
     *  API024 : 팀 초대 승인
     *  22.03.29 인준 : 권한어노테이션 추가
     **/
    @Auth
    @PutMapping("/joinRequestsFrom/{teamJoinRequestSeq}/approval")
    public ResponseEntity<?> approveTeamInvitation (
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser userSession,
            @PathVariable Long teamJoinRequestSeq
    ) {
        teamJoinService.approveInvitation( TeamInvitationCommand.ofApproval( teamJoinRequestSeq, userSession.getUserSeq() ) );

        /** 세션 팀 권한 정보 update */
        TeamAuthDTO teamAuthInfo = myTeamAuthService.getAllTeamAuthInfo( TeamAuthDTO.of( userSession.getUserSeq() ) );
        userSession.setAuthTeams( teamAuthInfo.getAuthTeams() );
        return ResponseEntity.ok().build();
    }


    /**
     *  API033 : 농구팀 초대 거절
     *  22.03.29 인준 : 권한어노테이션 추가
     **/
    @Auth
    @PutMapping("/joinRequestsFrom/{teamJoinRequestSeq}/rejection")
    public ResponseEntity<?> rejectTeamInvitation (
            @SessionAttribute(value=LOGIN_USER, required = false) SessionUser userSession,
            @PathVariable Long teamJoinRequestSeq
    ) {
        teamJoinService.rejectInvitation( TeamInvitationCommand.ofRejection( teamJoinRequestSeq, userSession.getUserSeq() ) );
        return ResponseEntity.ok().build();
    }


    /**
     *  API032 : 농구팀 초대 목록 조회
     *  22.03.13 인준 : API022 세분화 - 가입요청 및 초대 목록을 하나의 API콜로 가져오는 것에서 API 2개를 콜해서 가져오는 구조로 변경
     *  22.03.29 인준 : 권한어노테이션 추가
     **/
    @Auth
    @GetMapping("/joinRequestsFrom")
    public ResponseEntity<?> getTeamInvitations(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser userSession
    ) {
        List<JoinRequestDTO> result = teamJoinService.getTeamInvitations( TeamInvitationQuery.ofUser( userSession.getUserSeq() ) );
        return ResponseEntity.ok().body( result );
    }

    /**
     * API025 회원 프로필 조회
     */
    @Auth
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile (
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser userSession
    ) {
        UserDTO userDto = authUserService.getProfile( userSession.getUserSeq() );
        return ResponseEntity.ok().body(userDto);
    }

    /**
     * API026 회원 프로필 수정
     */
    @Auth
    @PostMapping("/profile")
    public ResponseEntity<?> updateProfile(
            @SessionAttribute(value = LOGIN_USER,required = false) SessionUser userSession,
            @RequestBody @Valid UpdateProfileRequest request
    ) {
        authUserService.updateProfile( request.toCommand( userSession.getUserSeq() ) );
        return ResponseEntity.ok().build();
    }

    /**
     *  TODO 이하 리팩토링 진행요망 ....
     **/


    /**
     * API028 회원탈퇴 : 회원탈퇴기능은 verify로 deleteUser 메소드가 호출되었는지 확인하는 방법 말고는 존재하지 않는다.
     *          만약 컬럼값 1개만 Y->N 으로 변경했더라면 객체간 비교를 해줄 수 있지만, 아에 테이블에서 삭제를 해버리는 이상 마땅한 방법이 없음
     */
    @Auth
    @DeleteMapping("/profile")
    public ResponseEntity<Void> deleteUser(
            @SessionAttribute(value = LOGIN_USER,required = false) SessionUser userSession
    ){

        String id = userSession.getUserId();

        authUserService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    /**
     * API027 비밀번호 변경
     */
    @Auth
    @PostMapping("/profile/password")
    public ResponseEntity<Void> updatePassword (
            @SessionAttribute(value = LOGIN_USER,required = false) SessionUser userSession,
            @RequestBody @Valid PasswordUpdateDTO passwordUpdateDTO
    ) {
        passwordUpdateDTO.userSeq( userSession.getUserSeq() );
        authUserService.updatePassword(passwordUpdateDTO);
        return ResponseEntity.ok().build();
    }
}