package com.threeNerds.basketballDiary.auth.validation;

import com.threeNerds.basketballDiary.auth.AuthorizationChecker;
import com.threeNerds.basketballDiary.auth.AuthorizationStatus;
import com.threeNerds.basketballDiary.exception.error.ErrorMessageType;
import com.threeNerds.basketballDiary.exception.error.SystemErrorType;
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
            public ErrorMessageType getErrorMessage() {
                return SystemErrorType.LOGIN_REQUIRED;
            }
        };
    }

}
