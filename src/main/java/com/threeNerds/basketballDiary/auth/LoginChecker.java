package com.threeNerds.basketballDiary.auth;

import com.threeNerds.basketballDiary.auth.exception.AuthorizationException;
import com.threeNerds.basketballDiary.auth.exception.NotAllowedAuthException;
import com.threeNerds.basketballDiary.session.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 현재 세션의 권한정보를 관리하는 객체
 * -
 */
public class LoginChecker implements AuthorizationChecker {
    public AuthorizationStatus checkAuthStatus(HttpServletRequest request ) {
        return new AuthorizationStatus() {
            @Override
            public boolean isPermission() {
                return SessionUtil.isLogin();
            }

            @Override
            public AuthorizationException getException() {
                return new NotAllowedAuthException( "REQUIRED_LOGIN", "로그인이 필요합니다." );
            }
        };
    }

}
