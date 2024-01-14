package com.threeNerds.basketballDiary.interceptor;

import com.threeNerds.basketballDiary.constant.UserAuthConst;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.exception.error.SystemErrorType;
import com.threeNerds.basketballDiary.utils.SessionUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /**
         * URL 로그 처리
         */
        if(!(handler instanceof HandlerMethod)) {
            log.info("=> {} {} ", request.getMethod(), request.getRequestURI());
            return true;
        }
        HandlerMethod hm = (HandlerMethod) handler;
        Operation apiSpec = hm.getMethodAnnotation( Operation.class );
        if ( null != apiSpec ) {
            log.info( "=> {} : {} {} ", apiSpec.summary(), request.getMethod(), request.getRequestURI() );
        } else {
            log.info( "=> {} {} ", request.getMethod(), request.getRequestURI() );
        }

        Auth apiAuth = hm.getMethodAnnotation(Auth.class);
        /** 1. @Auth 가 없는 경우 - 인증이 별도로 필요없음
         *  (팀에 속하지 않는 회원에 해당) */
        if (apiAuth == null) {
            return true;
        }

        /** 2. @Auth가 있는 경우 - 세션이 있는지 확인 */
        if (!SessionUtil.isLogin()) {
            throw new CustomException( SystemErrorType.LOGIN_REQUIRED );
        }

        /** 소속팀의 권한 체크 - pathVariables에 teamSeq가 없는 경우는 권한체크를 할 필요없음. */
        final Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        boolean hasTeamSeq = pathVariables.containsKey("teamSeq");
        if (!hasTeamSeq) {
            return true;
        }

        Long apiAuthGrade = apiAuth.GRADE();
        boolean isUserGrade = apiAuthGrade == UserAuthConst.USER;
        if (isUserGrade) {
            return true;
        }

        // 현재 나의 권한이 api의 권한보다 크거나 같을때 접근가능
        Map<Long, Long> userAuth = SessionUtil.getAuth();
        Long teamSeq = Long.parseLong(pathVariables.get("teamSeq"));
        Long userAuthGrade = userAuth.get(teamSeq);
        boolean isAuthorized = userAuthGrade >= apiAuthGrade;
        if (isAuthorized) {
            return true;
        }
        log.info("==================== 권한이 없습니다. ===================");
        log.info("= 팀Seq : {} ", teamSeq);
        log.info("= API권한정보 : {} / 사용자권한정보 : {} ", userAuthGrade, apiAuthGrade);
        log.info("======================================================");
        throw new CustomException( SystemErrorType.UNAUTHORIZED_ACCESS );
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
