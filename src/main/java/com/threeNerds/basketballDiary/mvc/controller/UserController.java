package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.dto.user.CmnUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.user.FindAllUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.user.LoginUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.user.UserDTO;
import com.threeNerds.basketballDiary.mvc.service.LoginService;
import com.threeNerds.basketballDiary.mvc.service.UserService;
import com.threeNerds.basketballDiary.mvc.service.UserTeamManagerService;
import com.threeNerds.basketballDiary.session.SessionConst;
import com.threeNerds.basketballDiary.session.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static com.threeNerds.basketballDiary.utils.HttpResponses.*;

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
public class UserController {

    private final LoginService loginService;
    private final UserService userService;
    private final UserTeamManagerService userTeamManagerService;

    /**
     * API034 사용자ID 중복확인
     */
    @PostMapping("/duplicationCheck")
    public ResponseEntity<?> checkDuplicateUserId (
            @RequestBody CmnUserDTO cmnUserDTO
    ) {
        User checkForDuplication = User.builder()
                .userId(cmnUserDTO.getUserId())
                .build();
        userService.checkDuplicationUserId(checkForDuplication);
        return RESPONSE_OK;
    }
    /**
     * API029 회원가입
     */
    @PostMapping("/registration")
    public ResponseEntity<?> createUser(
            @RequestBody @Valid CmnUserDTO userDTO
    ) {

        User user = User.createUserForRegistration(userDTO);
        userService.createMember(user);
        return RESPONSE_OK;
    }

    /**
     * API030 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<SessionUser> login (
            @RequestBody LoginUserDTO loginUserDTO,
            HttpSession session
    ) {
        log.info("로그인 시도");
        SessionUser sessionUser = loginService.login(loginUserDTO);
        session.setAttribute(SessionConst.LOGIN_MEMBER, sessionUser);
        log.info(session.getId());
        // TODO 쿠키생성 로직 - https://reflectoring.io/spring-boot-cookies/
//        ResponseCookie cookie = ResponseCookie.from("teset", "cookie").httpOnly(false).build();
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

    /**
     * API006 사용자 검색
     */
    @GetMapping
    public ResponseEntity<?> findUserInfo(
            @RequestParam(required = false) String userName,    // @RequestParam에 required=false가 없으면  get요청시 쿼리스트링이 반드시 있어야 함.
            @RequestParam(required = false) String email
    ){
        CmnUserDTO findUserCond = new CmnUserDTO()
                                        .userName(userName)
                                        .email(email);
        List<UserDTO> findUserList = userService.findUserByCond(findUserCond);
        return ResponseEntity.ok().body(findUserList);
    }

}
