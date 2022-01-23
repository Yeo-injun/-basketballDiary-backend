package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.mvc.dto.AuthUserRequestDTO;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public SessionDTO login(@RequestBody LoginUserRequest loginUserRequest, HttpSession session) {
        SessionDTO sessionDTO = loginService.login(loginUserRequest)
                .map(u -> {
                    Map<Long, Long> userAuth = loginService.findAuthList(loginUserRequest).stream()
                            .collect(Collectors.toMap(i -> Long.parseLong(i.getTeamSeq()), i -> Long.parseLong(i.getTeamAuthCode())));
                    return new SessionDTO(u.getUserSeq(), u.getUserId(),userAuth);
                })
                .orElse(null);
        /**
         * 화면에서 teamSeq를 가져온다고 하자
         * 그러면 화면이 teamSeq를 가지고 있어야 하는데, 에초에 내가 접근하려고 하는 팀이 어떤 seq를 가지고 있는지 어떻게 알 수 있지????
         * => 팀목록 화면에 들어가면 Get, 이때 나의 userId 를 TEAM_MEMBER 테이블에서 조회해서 데이터들을 다 뽑아오자
         * 그러면 이 정보안에 teamSeq가 담겨져 있다. 얘를 화면에 뿌려준다.
         * 그 후에 팀을 선택했을때 Httpbody 에 teamSeq도 함께 있다.
         */
        session.setAttribute(SessionConst.LOGIN_MEMBER,sessionDTO);
        return sessionDTO;
    }

    @Data
    public static class LoginUserRequest{
        private String userId;
        private String password;
    }

}
