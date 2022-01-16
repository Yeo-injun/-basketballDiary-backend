package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.dto.SessionDTO;
import com.threeNerds.basketballDiary.mvc.service.LoginService;
import com.threeNerds.basketballDiary.mvc.service.UserService;
import com.threeNerds.basketballDiary.session.SessionConst;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public SessionDTO login(LoginUserRequest loginUserRequest,HttpSession session){
        SessionDTO sessionDTO = loginService.login(loginUserRequest.getUserId(), loginUserRequest.getPassword())
                .map(u -> new SessionDTO(u.getUserSeq(), u.getUserId()))
                .orElse(null);
        session.setAttribute(SessionConst.LOGIN_MEMBER,sessionDTO);
        return sessionDTO;
    }

    @Data
    static class LoginUserRequest{
        private String userId;
        private String password;
    }
}
