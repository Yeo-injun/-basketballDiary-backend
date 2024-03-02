package com.threeNerds.basketballDiary.session.util;

import com.threeNerds.basketballDiary.session.SessionStore;
import com.threeNerds.basketballDiary.session.SessionUser;

import java.util.HashMap;
import java.util.Map;

public class SessionUtil {

    public static final String LOGIN_USER = "loginUser";

    private SessionUtil() {
        throw new AssertionError("Can not create instance of SessionUtil");
    }

    public static void set(String key, String value) {
        SessionStore.set(key, value);
    }

    public static void setSessionUser(SessionUser value) {
        SessionStore.set(LOGIN_USER, value);
    }

    public static SessionUser getSessionUser() {
        SessionUser userInfo = null;
        try {
            userInfo = (SessionUser) SessionStore.get( LOGIN_USER );
        } catch (Exception e) {
            // 로그 구현하기
        }
        return userInfo;
    }

    public static Long getUserSeq() {
        if ( !isLogin() ) {
            return null;
        }
        return getSessionUser().getUserSeq();
    }

    public static boolean isLogin() {
        if ( getSessionUser() == null ) {
            return false;
        }
        return true;
    }

    public static void invalidate() {
        SessionStore.invalidate();
    }
}
