package com.threeNerds.basketballDiary.interceptor;

import com.threeNerds.basketballDiary.session.SessionConst;
import com.threeNerds.basketballDiary.session.SessionUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod hm = (HandlerMethod) handler;

        Auth auth = hm.getMethodAnnotation(Auth.class);
        //1. @Auth 가 없는 경우는 인증이 별도로 필요없음(팀에 속하지 않는 회원에 해당)
        if(auth==null){
            return true;
        }
        //2. @Auth 가 있는 경우에는 세션이 있는지 확인
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();

        log.info("인증 체크 인터셉터 실행 {}",requestURI);

        if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
            log.info("미인증 사용자 요청");
            response.sendRedirect("/login?redirectURL=" + requestURI);
            return false;
        }
        //3. 각 권한 분기처리(팀장,임원,팀원)
        SessionUser memberDto = (SessionUser) session.getAttribute(SessionConst.LOGIN_MEMBER);

        //팀 id , 권한
        //Long teamId = Long.parseLong(request.getParameter("teamId"));   //=>pathVariable 로 바꾸어야됨
        final Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long teamId = Long.parseLong(pathVariables.get("teamId"));
        Map<Long, Long> userAuth = memberDto.getUserAuth();

        Long grade = auth.GRADE();
        //현재 나의 권한보다 접근할 수 있는 권한이 더 높으면 접근 불가
        if(userAuth.get(teamId) < grade){
            log.info("접근 불가");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
