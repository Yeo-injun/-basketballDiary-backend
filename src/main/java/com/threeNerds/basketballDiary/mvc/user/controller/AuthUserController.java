package com.threeNerds.basketballDiary.mvc.user.controller;

import com.threeNerds.basketballDiary.auth.validation.RequiredLogin;

import com.threeNerds.basketballDiary.mvc.team.service.TeamAuthService;
import com.threeNerds.basketballDiary.mvc.team.service.dto.TeamAuthQuery;
import com.threeNerds.basketballDiary.mvc.user.controller.docs.*;
import com.threeNerds.basketballDiary.mvc.user.mapper.dto.JoinRequestDTO;
import com.threeNerds.basketballDiary.mvc.user.service.UserTeamJoinService;
import com.threeNerds.basketballDiary.mvc.user.service.dto.JoinRequestCommand;
import com.threeNerds.basketballDiary.mvc.user.service.dto.JoinRequestQuery;
import com.threeNerds.basketballDiary.mvc.user.service.dto.TeamInvitationCommand;
import com.threeNerds.basketballDiary.mvc.user.service.dto.TeamInvitationQuery;
import com.threeNerds.basketballDiary.session.SessionUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.threeNerds.basketballDiary.session.util.SessionUtil.LOGIN_USER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/loginUser")
@Tag(
    name        = "로그인(인증)한 사용자 컨트롤러",
    description = "로그인(인증)한 사용자와 관련된 서비스를 제공하는 컨트롤러."
)
public class AuthUserController {

    private final TeamAuthService teamAuthService;

    private final UserTeamJoinService userTeamJoinService;

    /**
     *  API022 : 농구팀 가입요청 목록 조회
     *  22.03.13 인준 : API022 세분화 - 가입요청 및 초대 목록을 하나의 API콜로 가져오는 것에서 API 2개를 콜해서 가져오는 구조로 변경
     *  22.03.26 인준 : SessionUser null체크 로직 제거 - 인터셉터에서 체크하기 때문
     *  22.03.29 인준 : 권한어노테이션 추가
     **/
    @ApiDocs022
    @RequiredLogin
    @GetMapping("/joinRequestsTo")
    public ResponseEntity<?> getJoinRequests(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser userSession
    ) {
        List<JoinRequestDTO> result = userTeamJoinService.getJoinRequests( JoinRequestQuery.of( userSession.getUserSeq() ) );
        return ResponseEntity.ok().body(result);
    }

    /**
     *  API020 : 농구팀 가입요청 보내기
     *  22.03.25 인준 : SessionUser null체크후 예외처리 적용. Service Layer에서의 예외처리 적용
     *  22.03.26 인준 : SessionUser null체크 로직 제거 - 인터셉터에서 하기 때문.
     *  22.03.29 인준 : 권한어노테이션 추가
     **/
    @ApiDocs020
    @RequiredLogin
    @PostMapping("/joinRequestTo/{teamSeq}")
    public ResponseEntity<Void> sendRequest(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser userSession,
            @PathVariable Long teamSeq
    ) {
        userTeamJoinService.sendRequest( JoinRequestCommand.ofCreation( teamSeq, userSession.getUserSeq() ) );
        return ResponseEntity.ok().build();
    }

    /**
     *  API023 : 농구팀 가입요청 취소
     *  22.03.29 인준 : 권한어노테이션 추가
     **/
    @ApiDocs023
    @RequiredLogin
    @DeleteMapping("/joinRequestsTo/{teamJoinRequestSeq}")
    public ResponseEntity<?> cancelRequest(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser userSession,
            @PathVariable Long teamJoinRequestSeq
    ) {
        userTeamJoinService.cancelRequest( JoinRequestCommand.ofCancel( teamJoinRequestSeq, userSession.getUserSeq() ) );
        return ResponseEntity.ok().build();
    }

    /**
     *  API024 : 농구팀 초대 승인
     *  22.03.29 인준 : 권한어노테이션 추가
     **/
    @ApiDocs024
    @RequiredLogin
    @PutMapping("/joinRequestsFrom/{teamJoinRequestSeq}/approval")
    public ResponseEntity<?> approveTeamInvitation(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser userSession,
            @PathVariable Long teamJoinRequestSeq
    ) {
        userTeamJoinService.approveInvitation( TeamInvitationCommand.ofApproval( teamJoinRequestSeq, userSession.getUserSeq() ) );

        /** 세션 팀 권한 정보 update
         * TODO Redis로 이전한 이후 세팅값이 정상적으로 반영되는지 확인 필요
         *  >> Redis 서버에 정보 업데이트 되도록 처리 요망
         *  >> 별도 처리없이도 반영되는 것으로 확인... 원리는? 어떻게 가능하지?
         **/
        TeamAuthQuery.Result teamAuthInfo = teamAuthService.getAllTeamAuthInfo(
                                                TeamAuthQuery.builder()
                                                        .userSeq( userSession.getUserSeq() )
                                                        .build()
        );
        userSession.setAuthTeams( teamAuthInfo.getAuthTeams() );
        return ResponseEntity.ok().build();
    }


    /**
     *  API033 : 농구팀 초대 거절
     *  22.03.29 인준 : 권한어노테이션 추가
     **/
    @ApiDocs033
    @RequiredLogin
    @PutMapping("/joinRequestsFrom/{teamJoinRequestSeq}/rejection")
    public ResponseEntity<?> rejectTeamInvitation(
            @SessionAttribute(value=LOGIN_USER, required = false) SessionUser userSession,
            @PathVariable Long teamJoinRequestSeq
    ) {
        userTeamJoinService.rejectInvitation( TeamInvitationCommand.ofRejection( teamJoinRequestSeq, userSession.getUserSeq() ) );
        return ResponseEntity.ok().build();
    }


    /**
     *  API032 : 농구팀에게 받은 초대 목록 조회
     *  22.03.13 인준 : API022 세분화 - 가입요청 및 초대 목록을 하나의 API콜로 가져오는 것에서 API 2개를 콜해서 가져오는 구조로 변경
     *  22.03.29 인준 : 권한어노테이션 추가
     **/
    @ApiDocs032
    @RequiredLogin
    @GetMapping("/joinRequestsFrom")
    public ResponseEntity<?> getTeamInvitations(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser userSession
    ) {
        List<JoinRequestDTO> result = userTeamJoinService.getTeamInvitations( TeamInvitationQuery.ofUser( userSession.getUserSeq() ) );
        return ResponseEntity.ok().body( result );
    }

}