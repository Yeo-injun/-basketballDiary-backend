package com.threeNerds.basketballDiary.auth;

import com.threeNerds.basketballDiary.auth.validation.RequiredLogin;
import com.threeNerds.basketballDiary.auth.validation.LoginChecker;
import com.threeNerds.basketballDiary.auth.validation.game.GameAuthChecker;
import com.threeNerds.basketballDiary.auth.validation.game.RequiredGameAuth;
import com.threeNerds.basketballDiary.auth.validation.team.RequiredTeamAuth;
import com.threeNerds.basketballDiary.auth.validation.team.TeamAuthChecker;
import com.threeNerds.basketballDiary.session.SessionUser;
import com.threeNerds.basketballDiary.session.util.SessionUtil;
import org.springframework.web.method.HandlerMethod;

import java.util.Optional;

public class AuthorizationCheckerFactory {

    /**
     * HandlerMethod의 권한 어노테이션에 따라 Checker객체 생성
     * @param handler
     * @return Optional< AuthorizationChecker >
     */
    public static Optional< AuthorizationChecker > build( HandlerMethod handler ) {

        SessionUser userSession = SessionUtil.getSessionUser();
        RequiredTeamAuth teamAuth = handler.getMethodAnnotation( RequiredTeamAuth.class );
        if ( null != teamAuth ) {
            return Optional.of( new TeamAuthChecker( teamAuth.type(), userSession.getAuthTeams() ) );
        }

        RequiredGameAuth gameAuth = handler.getMethodAnnotation( RequiredGameAuth.class );
        if ( null != gameAuth ) {
            return Optional.of( new GameAuthChecker( gameAuth.type(), userSession.getAuthGames() ) );
        }
        // 로그인 여부를 검증해야 하는지 확인 - 로그인 여부를 검증하는 checker 생성
        RequiredLogin requiredLogin = handler.getMethodAnnotation( RequiredLogin.class );
        if ( null != requiredLogin ) {
            return Optional.of( new LoginChecker() );
        }
        return Optional.empty();
    }


}
