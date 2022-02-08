package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.mvc.service.LoginService;
import com.threeNerds.basketballDiary.session.SessionConst;
import com.threeNerds.basketballDiary.session.SessionDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ... 수행하는 Controller
 * @author 책임자 작성
 *
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * </pre>
 */


@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public SessionDTO login(@RequestBody LoginUserRequest loginUserRequest, HttpSession session) {
        log.info("로그인");
        SessionDTO sessionDTO = loginService.login(loginUserRequest)
                .map(u -> {
                    Map<Long, Long> userAuth = loginService.findAuthList(loginUserRequest).stream()
                            .collect(Collectors.toMap(i -> Long.parseLong(i.getTeamSeq()), i -> Long.parseLong(i.getTeamAuthCode())));
                    return new SessionDTO(u.getUserSeq(), u.getUserId(),userAuth);
                })
                .orElse(null);
        session.setAttribute(SessionConst.LOGIN_MEMBER,sessionDTO);
        return sessionDTO;
    }
    @PostMapping("/logout")
    public String logout(HttpSession session){
        log.info("로그아웃");
        session.invalidate();
        return "logoutOk";
    }

    @Data
    public static class LoginUserRequest{
        private String userId;
        private String password;
    }

}
