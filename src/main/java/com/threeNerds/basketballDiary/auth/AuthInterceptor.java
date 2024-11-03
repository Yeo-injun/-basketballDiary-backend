package com.threeNerds.basketballDiary.auth;

import com.threeNerds.basketballDiary.auth.exception.AuthorizationException;
import com.threeNerds.basketballDiary.auth.exception.NotAllowedAuthException;
import com.threeNerds.basketballDiary.auth.exception.RequiredLoginException;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.ErrorMessageType;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if ( !( handler instanceof HandlerMethod ) ) {
            return true;
        }
        /** Request 로깅 */
        HandlerMethod hm = (HandlerMethod) handler;
        loggingRequestInfo( request, hm );

        Optional< AuthorizationChecker > nullableChecker = AuthorizationCheckerFactory.build( hm );
        if ( nullableChecker.isEmpty() ) {
            // 권한 검증할 checker가 없으면 접근가능하도록 처리
            return true;
        }
        // 생성된 checker로 요청에 대한 권한 체크 수행
        AuthorizationChecker checker = nullableChecker.get();
        AuthorizationStatus status      = checker.checkAuthStatus( request );
        if ( status.isPermission() ) {
            return true;
        }
        AuthorizationException exception = status.getException();
        throw new CustomException( new ErrorMessageType() {
            @Override
            public Integer getStatus() {
                if ( exception instanceof RequiredLoginException) {
                    return UNAUTHORIZED.value();
                }
                return ( exception instanceof NotAllowedAuthException )
                        ? METHOD_NOT_ALLOWED.value()    // 해당 자원에 대한 접근 권한이 지원되지 않는 것을 의미.
                        : INTERNAL_SERVER_ERROR.value();
            }

            @Override
            public String getCode() {
                return exception.getName();
            }

            @Override
            public String getMessage() {
                return exception.getMessage();
            }
        });
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
