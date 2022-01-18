package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.session.SessionDTO;
import com.threeNerds.basketballDiary.mvc.service.LoginService;
import com.threeNerds.basketballDiary.session.SessionConst;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public SessionDTO login(@RequestBody LoginUserRequest loginUserRequest, HttpSession session) {
        SessionDTO sessionDTO = loginService.login(loginUserRequest)
                .map(u -> new SessionDTO(u.getUserSeq(), u.getUserId()))
                .orElse(null);
        session.setAttribute(SessionConst.LOGIN_MEMBER,sessionDTO);
        return sessionDTO;
    }

    @Data
    public static class LoginUserRequest{
        private String userId;
        private String password;
    }
}
