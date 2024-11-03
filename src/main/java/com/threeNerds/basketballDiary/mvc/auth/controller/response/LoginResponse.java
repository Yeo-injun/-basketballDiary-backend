package com.threeNerds.basketballDiary.mvc.auth.controller.response;

import com.threeNerds.basketballDiary.session.SessionUser;
import lombok.Getter;
import java.util.Map;

@Getter
public class LoginResponse {

    private Long userSeq;
    private String userId;

    private Map< Long, Integer > authTeams;
    private Map< String, String > authGames;

    public LoginResponse( SessionUser userSession ) {
        this.userSeq    = userSession.getUserSeq();
        this.userId     = userSession.getUserId();
        this.authTeams  = userSession.getAuthTeams();
        this.authGames  = userSession.getAuthGames();
    }
}
