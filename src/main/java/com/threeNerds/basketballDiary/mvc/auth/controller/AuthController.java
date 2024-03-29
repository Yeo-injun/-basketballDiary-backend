package com.threeNerds.basketballDiary.mvc.auth.controller;

import com.threeNerds.basketballDiary.mvc.auth.controller.request.CheckDuplicateUserIdRequest;
import com.threeNerds.basketballDiary.mvc.auth.controller.request.CreateUserRequest;
import com.threeNerds.basketballDiary.mvc.auth.controller.request.LoginRequest;
import com.threeNerds.basketballDiary.mvc.auth.service.dto.LoginUserDTO;
import com.threeNerds.basketballDiary.mvc.auth.service.AuthService;
import com.threeNerds.basketballDiary.mvc.authUser.controller.response.CheckDuplicationUserIdResponse;
import com.threeNerds.basketballDiary.mvc.game.service.GameAuthService;
import com.threeNerds.basketballDiary.mvc.game.service.dto.GameAuthDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.service.MyTeamAuthService;
import com.threeNerds.basketballDiary.mvc.myTeam.service.dto.TeamAuthDTO;
import com.threeNerds.basketballDiary.session.SessionUser;
import com.threeNerds.basketballDiary.session.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.threeNerds.basketballDiary.session.util.SessionUtil.LOGIN_USER;

/**
 * ... 수행하는 Controller
 * @author 책임자 작성
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * 2022.03.13 이성주 : LoginController 통합
 * </pre>
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final MyTeamAuthService myTeamAuthService;
    private final GameAuthService gameAuthService;

    /**
     * API065 권한정보 조회
     */
    @GetMapping
    public ResponseEntity<SessionUser> getAuthInfo (
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser userSession
    ) {
        return ResponseEntity.ok().body( userSession );
    }

    /**
     * API034 사용자ID 중복확인
     * - 23.10.14 인준
     *      : API마다 Request/Response 클래스를 만들어서 관리하는 패턴 유지.
     *      : Service에서 Request/Response 클래스에 의존하지 않도록 변경. ( 파라미터 및 return 타입은 DTO 혹은 원시타입 )
     *      : ResponseEntity 제네릭 안의 와일드카드를 없애고, Return되는 Response 타입을 명확하게 하기 위해 BooleanResponse타입의 인터페이스를 만들어서 적용
     *      >> 기존 BooleanResult 클래스를 사용하지 않는 이유는 true or false 값을 return한다고 해도 API마다 속성명이 미묘하게 달라져야 의미가 더 명확하게 전달되기 때문
     *          - success 속성은 중복여부 체크의 성공여부를 알려주는 뉘앙스로, 중복여부를 직접적으로 나타내는 의미를 전달하지 못함.
     *          - 이에 따라 중복여부를 명확하게 드러내는 속성명을 적용하고, true/false를 return하는 API마다 맥락에 맞는 속성명을 정의해서 사용.
     * - 23.11.02 목 / 회의 결과 Marker Interface 방식 적용은 보류
     */
    @PostMapping("/duplicationCheck")
    public ResponseEntity<CheckDuplicationUserIdResponse> checkDuplicateUserId (
            @RequestBody @Valid CheckDuplicateUserIdRequest reqBody
    ) {
        return ResponseEntity.ok()
                .body( new CheckDuplicationUserIdResponse( authService.checkDuplicationUserId( reqBody.getUserId() ) ) );
    }

    /**
     * API029 회원가입
     */
    @PostMapping("/registration")
    public ResponseEntity<Void> createUser (
            @RequestBody @Valid CreateUserRequest request
    ) {
        authService.createMember(request);
        return ResponseEntity.ok().build();
    }

    /**
     * API030 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<SessionUser> login (
            @RequestBody @Valid LoginRequest reqBody
    ) {
        log.info("======= Try login =======");
        LoginUserDTO loginRequest = new LoginUserDTO()
                .userId( reqBody.getUserId() )
                .password( reqBody.getPassword() );

        /** 로그인 정보 확인 */
        LoginUserDTO loginUser = authService.login( loginRequest );
        Long loginUserSeq = loginUser.getUserSeq();

        /** 소속팀 권한정보 조회 */
        TeamAuthDTO userTeamAuthInfo = myTeamAuthService.getAllTeamAuthInfo( TeamAuthDTO.of( loginUserSeq ) );

        /** 경기 권한정보 조회 */
        GameAuthDTO userAuthGameInfo = gameAuthService.getGameAuthInfo( loginUserSeq );

        /** 세션 정보 생성 및 저장 */
        SessionUser sessionUser = SessionUser.createWithAuth(
            loginUserSeq, loginUser.getUserId(),
            userTeamAuthInfo.getAuthTeams(),
            userAuthGameInfo.getAuthGames()
        );
        SessionUtil.setSessionUser( sessionUser );

        /* 경기기록권한 정보 확인 */
        // TODO 세션ID 로그찍기  log.info(SessionUtil.get.getId());
        // TODO 쿠키생성 로직 - https://reflectoring.io/spring-boot-cookies/
        return ResponseEntity.ok().body( sessionUser );
    }

    /**
     * API031 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout (HttpSession session) {
        log.info("로그아웃");
        session.invalidate();
        return ResponseEntity.ok().build();
    }

}
