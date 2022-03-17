package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.interceptor.Auth;
import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.dto.AuthUserRequestDTO;
import com.threeNerds.basketballDiary.mvc.dto.loginUser.CmnLoginUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.CmnUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.UserDTO;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * API029 회원가입
     */
    @PostMapping("/registration")
    public ResponseEntity<?> createUser(@RequestBody @Valid CmnUserDTO userDTO){

        User user = User.builder()
                .userId(userDTO.getUserId())
                .password(userDTO.getPassword())
                .userName(userDTO.getUserName())
                .positionCode(userDTO.getPositionCode())
                .email(userDTO.getEmail())
                .gender(userDTO.getGender())
                .birthYmd(userDTO.getBirthYmd())
                .height(userDTO.getHeight())
                .weight(userDTO.getWeight())
                .regDate(LocalDate.now())
                .updateDate(LocalDate.now())
                .userRegYn("Y")
                .sidoCode(userDTO.getSidoCode())
                .sigunguCode(userDTO.getSigunguCode())
                .build();

        userService.createMember(user);
        return RESPONSE_OK;
    }

    /**
     * API030 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<SessionUser> login(@RequestBody CmnLoginUserDTO cmnLoginUserDTO, HttpSession session) {
        log.info("로그인");
        SessionUser sessionUser = loginService.login(cmnLoginUserDTO)
                .map(loginUser -> { // TODO  loginService.login()에서 로그인 처리 하기 (트랜잭션 경계 이슈 - 현재처럼  login요청을 두번의 Service요청으로 쪼개놓으면 향후 트랜잭션 단위가 분리가 되어 이슈가 발생할 수 있음.)
                                    List<AuthUserRequestDTO> userAuthList = loginService.findAuthList(cmnLoginUserDTO);

                                    /** 팀에 소속되어 있지 않을 경우 - 권한정보없이 SessionUser객체 생성 */
                                    if (userAuthList.size() == 1)
                                    {
                                        return new SessionUser(loginUser.getUserSeq(), loginUser.getUserId());
                                    }

                                    /** 권한정보 생성후 SesstionUser객체 return */
                                    Map<Long, Long> userAuth = userAuthList.stream()
                                                                           .collect(Collectors.toMap(authDTO -> Long.parseLong(authDTO.getTeamSeq()), authDTO -> Long.parseLong(authDTO.getTeamAuthCode())));
                                    return new SessionUser(loginUser.getUserSeq(), loginUser.getUserId(), userAuth);
                })
                .orElse(null);
        session.setAttribute(SessionConst.LOGIN_MEMBER, sessionUser);
        return ResponseEntity.ok(sessionUser);
    }
    /**
     * API031 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session){
        log.info("로그아웃");
        session.invalidate();
        return RESPONSE_OK;
    }

    @Auth(GRADE = 3L)
    @GetMapping("/testAnnotation/{teamId}")
    public void test(){
        log.info("Auth : 1");
    }

    @Auth(GRADE = 2L)
    @GetMapping("/testAnnotation2/{teamId}")
    public void test2(){
        log.info("Auth : 2");
    }
}
