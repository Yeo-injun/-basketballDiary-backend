package com.threeNerds.basketballDiary.mvc.user.controller;

import com.threeNerds.basketballDiary.auth.Auth;
import com.threeNerds.basketballDiary.mvc.user.controller.docs.ApiDocs006;
import com.threeNerds.basketballDiary.mvc.user.controller.docs.ApiDocs034;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    @ApiDocs006
    @GetMapping("/excludeTeam/{teamSeq}")
    public ResponseEntity<?> searchUsersExcludingTeamMembers  (
            @PathVariable
            Long teamSeq,
            @RequestParam( required = false )
            String userName,
            @RequestParam( required = false )
            String email,
            @RequestParam( defaultValue = "0" )
            Integer pageNo
    ) {
        UserQuery.Result result = userService.searchUsersExcludingTeamMembers(
                                        UserQuery.builder()
                                                .pageNo(    pageNo )
                                                .teamSeq(   teamSeq )
                                                .userName(  userName )
                                                .email(     email )
                                                .build()
                                        );
        return ResponseEntity.ok().body( new GetUsersExcludingTeamMembersResponse( result ) );
    }


    /**
     * --------------------------------
     * 이하 메소드는 url service root 분리 검토
     * > /api/membership/... 회원가입에 필요한 API들을 만들어두기
     *      GET     /available
     *      POST    /signUp
     *      DELETE  /
     *      GET     /profile
     *      POST    /profile
     *      POST    /password
     * --------------------------------
     */
    /**
     * API034 사용자ID 사용가능여부 확인
     */
    @ApiDocs034
    @GetMapping("/available")
    public ResponseEntity<?> checkUserIdAvailable (
            @RequestParam @NotEmpty( message="userId는 필수값입니다.")
            String userId
    ) {
        return ResponseEntity.ok()
                .body( new CheckUserIdAvailableResponse( userService.checkUserIdAvailable( userId ) ) );
    }

    /**
     * API029 회원가입
     */
    @PostMapping("/signUp")
    public ResponseEntity<Void> signUp (
            @RequestBody @Valid
            SignUpRequest request
    ) {
        userService.createMembership( request.toCommand() );
        return ResponseEntity.ok().build();
    }

    /**
     * API028 회원탈퇴
     */
    @Auth
    @DeleteMapping  // cf. 일반적인 HTTP spec에서는 DELETE 메소드는 RequestBody를 지원하지 않음. 이에 따라 Spring @DeleteMapping에서는 @RequestBody를 지원하지 않음.
    public ResponseEntity<Void> withdrawalMembership(
            @SessionAttribute( value = LOGIN_USER, required = false )
            SessionUser userSession,
            @RequestParam @NotEmpty( message="password는 필수값입니다." )
            String password
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
     * API025 회원 프로필 조회
     */
    @Auth
    @GetMapping("/profile")
    public ResponseEntity<GetMyProfileResponse> getMyProfile(
            @SessionAttribute(value = LOGIN_USER, required = false)
            SessionUser userSession
    ) {
        return ResponseEntity.ok().body(
            new GetMyProfileResponse( userService.getMyProfile( userSession.getUserSeq() ) )
        );
    }

    /**
     * API026 회원 프로필 수정
     */
    @Auth
    @PostMapping("/profile")
    public ResponseEntity<?> updateMyProfile(
            @SessionAttribute(value = LOGIN_USER,required = false)
            SessionUser userSession,
            @RequestBody @Valid
            UpdateMyProfileRequest request
    ) {
        userService.updateMyProfile( request.toCommand( userSession.getUserSeq() ) );
        return ResponseEntity.ok().build();
    }

    /**
     * API027 비밀번호 변경
     */
    @Auth
    @PostMapping("/password")
    public ResponseEntity<Void> updatePassword(
            @SessionAttribute(value = LOGIN_USER,required = false)
            SessionUser userSession,
            @RequestBody @Valid
            UpdatePasswordRequest request
    ) {
        userService.updatePassword( request.toCommand( userSession ) );
        return ResponseEntity.ok().build();
    }
}
