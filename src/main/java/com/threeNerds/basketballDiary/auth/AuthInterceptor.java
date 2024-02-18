package com.threeNerds.basketballDiary.auth;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.SystemErrorType;
import com.threeNerds.basketballDiary.utils.SessionUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /** Request 로깅 */
        HandlerMethod hm = (HandlerMethod) handler;
        loggingRequestInfo( request, hm );

        /** 1. @Auth 가 없는 경우 - 인증이 별도로 필요없음 */
        Auth requiredAuth = hm.getMethodAnnotation( Auth.class );
        if ( null == requiredAuth ) {
            return true;
        }

        /** 2. @Auth가 있는 경우 - 세션이 있는지 확인 */
        if ( !SessionUtil.isLogin() ) {
            throw new CustomException( SystemErrorType.LOGIN_REQUIRED );
        }

        /** 소속팀 권한 체크 */
        AuthChecker checker = AuthChecker.ofSession( requiredAuth );
        if ( checker.checkAuth( request ) ) {
            return true;
        }
        throw new CustomException( SystemErrorType.UNAUTHORIZED_ACCESS );
    }

    // 요청정보 로깅
    private void loggingRequestInfo( HttpServletRequest request, HandlerMethod hm ) {
        Operation apiSpec = hm.getMethodAnnotation( Operation.class );
        if ( null != apiSpec ) {
            log.info( "=> {} : {} {} ", apiSpec.summary(), request.getMethod(), request.getRequestURI() );
        } else {
            log.info( "=> {} {} ", request.getMethod(), request.getRequestURI() );
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
