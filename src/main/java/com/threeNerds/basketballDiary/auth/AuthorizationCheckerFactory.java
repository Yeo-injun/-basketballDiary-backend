package com.threeNerds.basketballDiary.auth;

import com.threeNerds.basketballDiary.auth.validation.RequiredLogin;
import com.threeNerds.basketballDiary.auth.validation.RequiredGameAuth;
import com.threeNerds.basketballDiary.auth.validation.RequiredTeamAuth;
import com.threeNerds.basketballDiary.session.SessionUser;
import com.threeNerds.basketballDiary.session.util.SessionUtil;
import org.springframework.web.method.HandlerMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorizationCheckerFactory {

    /**
     * HandlerMethod의 권한 어노테이션에 따라 Checker객체 생성
     * @param handler
     * @return Optional< AuthorizationChecker >
     */
    public static Optional< AuthorizationChecker > build( HandlerMethod handler ) {

        // 로그인 여부를 검증해야 하는지 확인 - 로그인 여부를 검증하는 checker 생성
        RequiredLogin requiredLogin = handler.getMethodAnnotation( RequiredLogin.class );
        RequiredTeamAuth teamAuth   = handler.getMethodAnnotation( RequiredTeamAuth.class );
        RequiredGameAuth gameAuth   = handler.getMethodAnnotation( RequiredGameAuth.class );

        SessionUser userSession = SessionUtil.getSessionUser();
        /**
         * 권한 유형에 대한 검증 대상은 사용자가 로그인한 상태인 경우에만 해당
         */
        if ( null != userSession ) {
            if ( null != teamAuth ) {
                return Optional.of( new TeamAuthChecker( teamAuth.type(), userSession.getAuthTeams() ) );
            }

            if ( null != gameAuth ) {
                return Optional.of( new GameAuthChecker( gameAuth.type(), userSession.getAuthGames() ) );
            }
        }

        /**
         * 위의 권한 유형 검증대상이 아니면 로그인 상태가 아니면 로그인이 필요한 서비스인지 확인
         */
        if ( null != requiredLogin ) {
            return Optional.of( new LoginChecker() );
        }
        return Optional.empty();
    }


}
