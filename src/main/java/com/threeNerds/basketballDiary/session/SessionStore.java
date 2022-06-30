package com.threeNerds.basketballDiary.session;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

public class SessionStore {

    private static HttpSession getSession() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // TODO 차세대에서는 false이었는데, false면 세션이 없을때 새로 생성하지 않음.
        // 그러면 로그아웃상태였다가, 로그인하면 세션을 어떻게 생성해준건지?
        // 참고자료 - https://july1012.tistory.com/entry/getSession-getSessiontrue-getSessionfalse-%EC%B0%A8%EC%9D%B4%EC%A0%90
        return requestAttributes.getRequest().getSession(true);
    }

    public static void set(String attribute, Object value) {
        getSession().setAttribute(attribute, value);
    }

    public static Object get(String attribute) {
        try {
            return getSession().getAttribute(attribute);
        } catch (Exception e) {
            return null;
        }
    }

    public static void remove(String attribute) {
        try {
            getSession().removeAttribute(attribute);
        } catch (Exception e) {}
    }

    public static void invalidate() {
        if (getSession() != null) {
            getSession().invalidate();
        }
    }
}
