package com.threeNerds.basketballDiary.mvc.auth.controller;

import com.threeNerds.basketballDiary.mvc.auth.controller.docs.ApiDocs030;
import com.threeNerds.basketballDiary.mvc.auth.controller.docs.ApiDocs031;
import com.threeNerds.basketballDiary.mvc.auth.controller.docs.ApiDocs065;
import com.threeNerds.basketballDiary.mvc.auth.controller.request.LoginRequest;
import com.threeNerds.basketballDiary.mvc.auth.controller.response.GetAuthInfoResponse;
import com.threeNerds.basketballDiary.mvc.auth.controller.response.LoginResponse;
import com.threeNerds.basketballDiary.mvc.auth.service.AuthService;
import com.threeNerds.basketballDiary.mvc.auth.service.dto.LoginUserQuery;
import com.threeNerds.basketballDiary.mvc.game.service.GameAuthService;
import com.threeNerds.basketballDiary.mvc.game.service.dto.GameAuthQuery;
import com.threeNerds.basketballDiary.mvc.team.service.MyTeamAuthService;
import com.threeNerds.basketballDiary.mvc.team.service.dto.TeamAuthDTO;
import com.threeNerds.basketballDiary.session.SessionUser;
import com.threeNerds.basketballDiary.session.util.SessionUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.threeNerds.basketballDiary.session.util.SessionUtil.LOGIN_USER;

// >> AuthUserController는 권한이 검증된 사용자(즉, 로그인한 사용자)가 본인의 정보를 수정/삭제하는 것에 대한 역할 수행
/**
 * 권한부여, 검증, 만료처리에 대한 역할 수행하는 Controller
 * @author 책임자 작성
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * 2022.03.13 이성주 : LoginController 통합 변경
 * </pre>
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(
    name        = "인증 컨트롤러",
    description = "인증과 관련된 서비스를 제공하는 컨트롤러. 추후 소셜 로그인 등 다양한 로그인 방식에 대한 처리는 해당 컨트롤러를 통해 처리"
)
public class AuthController {

    private final AuthService authService;
    private final MyTeamAuthService myTeamAuthService;
    private final GameAuthService gameAuthService;

    /**
     * API065 권한정보 조회
     */
    @ApiDocs065
    @GetMapping
    public ResponseEntity< GetAuthInfoResponse > getAuthInfo(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser userSession
    ) {
        return ResponseEntity.ok().body( new GetAuthInfoResponse( userSession ) );
    }

    /**
     * API030 로그인
     */
    @ApiDocs030
    @PostMapping("/login")
    public ResponseEntity< LoginResponse > login(
            @RequestBody @Valid LoginRequest request
    ) {
        log.info("======= Try login =======");
        /** 로그인 정보 확인 */
        LoginUserQuery.Result loginUser = authService.login( request.toQuery() );
        Long loginUserSeq               = loginUser.getUserSeq();

        /** 소속팀 권한정보 조회 */
        TeamAuthDTO teamAuthInfo = myTeamAuthService.getAllTeamAuthInfo(TeamAuthDTO.of(loginUserSeq));

        /** 경기 권한정보 조회 */
        GameAuthQuery.Result gameAuthInfo = gameAuthService.getGameAuthInfo(
                                            GameAuthQuery.builder()
                                                .userSeq( loginUserSeq )
                                                .build()
                                       );

        /** 세션 정보 생성 및 저장 */
        SessionUser sessionUser = SessionUser.createWithAuth(
                loginUserSeq,
                loginUser.getUserId(),
                teamAuthInfo.getAuthTeams(),
                gameAuthInfo.getAuthGames()
        );
        SessionUtil.setSessionUser( sessionUser );

        /* 경기기록권한 정보 확인 */
        // TODO 세션ID 로그찍기  log.info(SessionUtil.get.getId());
        // TODO 쿠키생성 로직 - https://reflectoring.io/spring-boot-cookies/
        return ResponseEntity.ok().body( new LoginResponse( sessionUser ) );
    }


    /**
     * API031 로그아웃
     */
    @ApiDocs031
    @PostMapping("/logout")
    public ResponseEntity< Void > logout(HttpSession session) {
        log.info("로그아웃");
        session.invalidate();
        return ResponseEntity.ok().build();
    }

}
