package com.threeNerds.basketballDiary.auth;


import javax.servlet.http.HttpServletRequest;

public interface AuthorizationChecker {
    /**
     * Request에 대한 권한 체크 수행
     * @param request
     * @return AuthorizationStatus 권한 허가여부와 오류 메세지를 return
     */
    AuthorizationStatus checkAuthStatus( HttpServletRequest request );

}
