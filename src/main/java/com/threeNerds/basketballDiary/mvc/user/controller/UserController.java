package com.threeNerds.basketballDiary.mvc.user.controller;

import com.threeNerds.basketballDiary.auth.Auth;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorResponse;
import com.threeNerds.basketballDiary.exception.error.SystemErrorType;
import com.threeNerds.basketballDiary.mvc.user.controller.request.UpdateMyProfileRequest;
import com.threeNerds.basketballDiary.mvc.user.controller.response.GetUsersExcludingTeamMembersResponse;
import com.threeNerds.basketballDiary.mvc.user.service.dto.MembershipCommand;
import com.threeNerds.basketballDiary.mvc.user.controller.request.SignUpRequest;
import com.threeNerds.basketballDiary.mvc.user.controller.request.UpdatePasswordRequest;
import com.threeNerds.basketballDiary.mvc.user.controller.response.CheckUserIdAvailableResponse;

import com.threeNerds.basketballDiary.mvc.user.controller.response.GetMyProfileResponse;
import com.threeNerds.basketballDiary.mvc.user.service.UserService;
import com.threeNerds.basketballDiary.mvc.user.service.dto.UserQuery;
import com.threeNerds.basketballDiary.session.SessionUser;
import com.threeNerds.basketballDiary.session.util.SessionUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import static com.threeNerds.basketballDiary.session.util.SessionUtil.LOGIN_USER;

/**
 * 사용자 정보의 생성, 확인 및 조회, 변경, 삭제에 대한 서비스를 제공하는 Controller
 *
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * </pre>
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "UserController")
public class UserController {

    private final UserService userService;

    /**
     * TODO URL 및 파라미터 타입 재설계
     * API006 사용자 검색 ( 팀원 제외 )
     * 23.05.07 여인준 : 팀원 제외하고 조회되게끔 변경
     */
    @Auth
    @Operation(
        summary = "사용자 검색 API",description = "사용자 검색",
        responses = {
                @ApiResponse(responseCode = "200"),
                @ApiResponse(responseCode = "404", description = "팀원을 찾지 못하였습니다.",content = @Content(schema = @Schema(implementation = DomainErrorResponse.class)))
        }
    )
    @GetMapping("/excludeTeam/{teamSeq}")
    public ResponseEntity<?> getUsersExcludingTeamMembers  (
            @PathVariable Long teamSeq,
            @RequestParam( required = false ) String userName,
            @RequestParam( required = false ) String email,
            @RequestParam( defaultValue = "0" ) Integer pageNo
    ) {
        UserQuery.Result result = userService.getUsersExcludingTeamMembers(
                                        UserQuery.builder()
                                                .pageNo(    pageNo )
                                                .teamSeq(   teamSeq )
                                                .userName(  userName )
                                                .email(     email )
                                                .build()
                                        );
        return ResponseEntity.ok().body( new GetUsersExcludingTeamMembersResponse( result ) );
    }

    // >> AuthController는 권한부여, 검증, 만료처리에 대한 역할 수행
    // >> AuthUserController는 권한이 검증된 사용자(즉, 로그인한 사용자)가 본인의 정보를 수정/삭제하는 것에 대한 역할 수행
    /**
     * TODO API설계서 최신화 반영 요망 24.03.29 일자 수정
     * API034 사용자ID 사용가능여부 확인
     */
    @GetMapping("/available")
    public ResponseEntity<?> checkUserIdAvailable (
            @RequestParam @NotEmpty String userId
    ) {
        if ( !StringUtils.hasText( userId ) ) {
            throw new CustomException( SystemErrorType.NOT_NULLABLE_VALUE );
        }
        return ResponseEntity.ok()
                .body( new CheckUserIdAvailableResponse( userService.checkUserIdAvailable( userId ) ) );
    }

    /**
     * API029 회원가입
     */
    @PostMapping("/signUp")
    public ResponseEntity<Void> signUp (
            @RequestBody @Valid SignUpRequest request
    ) {
        userService.createMembership( request.toCommand() );
        return ResponseEntity.ok().build();
    }

    /**
     * TODO API설계서 반영요망 24.03.30
     * API028 회원탈퇴
     */
    @Auth
    @DeleteMapping  // cf. 일반적인 HTTP spec에서는 DELETE 메소드는 RequestBody를 지원하지 않음. 이에 따라 Spring @DeleteMapping에서는 @RequestBody를 지원하지 않음.
    public ResponseEntity<Void> withdrawalMembership(
            @SessionAttribute( value = LOGIN_USER, required = false ) SessionUser userSession,
            @RequestParam String password
    ) {
        userService.withdrawalMembership( MembershipCommand.builder()
                .userSeq(       userSession.getUserSeq() )
                .plainPassword( password )
                .build() );

        // 로그아웃 처리
        SessionUtil.invalidate();
        return ResponseEntity.ok().build();
    }


    /**
     * TODO API설계서 반영요망 24.04.01
     * API025 회원 프로필 조회
     */
    @Auth
    @GetMapping("/profile")
    public ResponseEntity<GetMyProfileResponse> getMyProfile(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser userSession
    ) {
        return ResponseEntity.ok().body(
            new GetMyProfileResponse( userService.getMyProfile( userSession.getUserSeq() ) )
        );
    }

    /**
     * TODO API설계서 반영요망 24.04.01
     * API026 회원 프로필 수정
     */
    @Auth
    @PostMapping("/profile")
    public ResponseEntity<?> updateMyProfile(
            @SessionAttribute(value = LOGIN_USER,required = false) SessionUser userSession,
            @RequestBody @Valid UpdateMyProfileRequest request
    ) {
        userService.updateMyProfile( request.toCommand( userSession.getUserSeq() ) );
        return ResponseEntity.ok().build();
    }

    /**
     * TODO API설계서 반영요망 24.04.01
     * API027 비밀번호 변경
     */
    @Auth
    @PostMapping("/password")
    public ResponseEntity<Void> updatePassword(
            @SessionAttribute(value = LOGIN_USER,required = false) SessionUser userSession,
            @RequestBody @Valid UpdatePasswordRequest request
    ) {
        userService.updatePassword( request.toCommand( userSession ) );
        return ResponseEntity.ok().build();
    }
}
