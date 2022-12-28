package com.threeNerds.basketballDiary.mvc.auth.controller;

import com.threeNerds.basketballDiary.mvc.auth.controller.request.CheckDuplicateUserIdRequest;
import com.threeNerds.basketballDiary.mvc.auth.controller.request.CreateUserRequest;
import com.threeNerds.basketballDiary.mvc.auth.controller.request.LoginRequest;
import com.threeNerds.basketballDiary.mvc.auth.dto.CheckDuplicateUserIdDTO;
import com.threeNerds.basketballDiary.mvc.auth.dto.CreateUserDTO;
import com.threeNerds.basketballDiary.mvc.auth.service.AuthService;
import com.threeNerds.basketballDiary.mvc.auth.dto.LoginUserDTO;
import com.threeNerds.basketballDiary.session.SessionUser;
import com.threeNerds.basketballDiary.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.threeNerds.basketballDiary.constant.HttpResponseConst.RESPONSE_OK;

/**
 * ... 수행하는 Controller
 * @author 책임자 작성
 *
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * 2022.03.13 이성주 : LoginController 통합
 * </pre>
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class AuthController {

    private final AuthService authService;

    /**
     * API034 사용자ID 중복확인
     */
    @PostMapping("/duplicationCheck")
    public ResponseEntity<?> checkDuplicateUserId (
            @RequestBody CheckDuplicateUserIdRequest reqBody
    ) {
        CheckDuplicateUserIdDTO checkForDuplication = new CheckDuplicateUserIdDTO()
                .userId(reqBody.getUserId());

        authService.checkDuplicationUserId(checkForDuplication);
        return RESPONSE_OK;
    }
    /**
     * API029 회원가입
     */
    @PostMapping("/registration")
    public ResponseEntity<?> createUser (
            @RequestBody @Valid CreateUserRequest reqBody
    ) {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        BeanUtils.copyProperties(reqBody, createUserDTO);

        authService.createMember(createUserDTO);
        return RESPONSE_OK;
    }

    /**
     * API030 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<SessionUser> login (
            @RequestBody LoginRequest reqBody
    ) {
        log.info("======= Try login =======");
        LoginUserDTO loginUserDTO = new LoginUserDTO()
                .userId(reqBody.getUserId())
                .password(reqBody.getPassword());

        SessionUser sessionUser = authService.login(loginUserDTO);
        SessionUtil.setSessionUser(sessionUser);

        // TODO 세션ID 로그찍기  log.info(SessionUtil.get.getId());
        // TODO 쿠키생성 로직 - https://reflectoring.io/spring-boot-cookies/
        return ResponseEntity.ok().body(sessionUser);
    }

    /**
     * API031 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout (HttpSession session) {
        log.info("로그아웃");
        session.invalidate();
        return RESPONSE_OK;
    }
}