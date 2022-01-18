package com.threeNerds.basketballDiary.interceptor;

import com.threeNerds.basketballDiary.session.SessionConst;
import com.threeNerds.basketballDiary.session.SessionDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class loginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod hm = (HandlerMethod) handler;

        Auth auth = hm.getMethodAnnotation(Auth.class);
        //1. @Auth 가 없는 경우는 인증이 별도로 필요없음(비회원도 접근 가능)
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
        SessionDTO memberDto = (SessionDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
        //User에 권한 컬럼은 없는 것 인가?
        /*MemberStatus status = memberDto.getMemberStatus().get(1L);
        if(auth.role().equals(LEADER)){
            log.info("@Auth : LEADER");
            if(!LEADER.equals(status)){
                log.info("Your don't access here");
                response.sendRedirect("/notAccess");
                return false;
            }
        }
        else if(auth.role().equals(MANAGER)){
            log.info("@Auth : MANAGER");
            if(!LEADER.equals(status) && !MANAGER.equals(status)){
                log.info("Your don't access here");
                response.sendRedirect("/notAccess");
                return false;
            }
        }
        else if(auth.role().equals(TEAM_MEMBER)){
            log.info("@Auth : TEAM_MEMBER");
            if(!LEADER.equals(status) && !MANAGER.equals(status)&&!TEAM_MEMBER.equals(status)){
                log.info("Your don't access here");
                response.sendRedirect("/notAccess");
                return false;
            }
        }*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
