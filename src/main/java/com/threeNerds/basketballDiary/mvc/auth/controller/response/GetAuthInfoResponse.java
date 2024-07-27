package com.threeNerds.basketballDiary.mvc.auth.controller.response;

import com.threeNerds.basketballDiary.session.SessionUser;
import lombok.Getter;

import java.io.Serializable;
import java.util.Map;

@Getter
public class GetAuthInfoResponse {
    private boolean loginState;
    private Long userSeq;
    private String userId;

    private Map< Long, Integer > authTeams;    // 팀을 기준으로 권한수준 관리
    private Map< Long, Integer > authGames;    // 경기를 기준으로 권한수준 관리

    public GetAuthInfoResponse( SessionUser userSession ) {
        if ( null == userSession ) {
            this.loginState = false;
        } else {
            this.loginState = true;
            this.userSeq    = userSession.getUserSeq();
            this.userId     = userSession.getUserId();
            this.authTeams  = userSession.getAuthTeams();
            this.authGames  = userSession.getAuthGames();
        }
    }
}
