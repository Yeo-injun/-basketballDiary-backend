package com.threeNerds.basketballDiary.interceptor;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.Error;
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
import java.util.Optional;

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
        log.info("인증 체크 인터셉터 실행 {}",requestURI);

        HttpSession session = request.getSession();
        SessionUser memberDto = Optional.ofNullable(session)
                                        .map(httpSession -> (SessionUser) httpSession.getAttribute(SessionConst.LOGIN_MEMBER))
                                        .orElseThrow(() -> new CustomException(Error.LOGIN_REQUIRED));

        // TODO response.sendRedirect()는 웹브라우저가 최초 요청에 대해 응답을 받은 후 재요청을 위한 URL(화면을 받기 위함)를 넘겨주는 API임.
        // TODO 하지만 현재의 백엔드 서버는 화면을 넘겨주는 것이 아닌 데이터만 넘겨주기 있음.
        // TODO 이때문에 sendRedirect로 새로운 URL를 넘겨준다고 해도 화면전환이 안되고 백엔드 서버에 /login요청이 다시 날라가서 로그인 에러가 발생할 것임.
        // TODO 그렇기 때문에 sendRedirect()사용은 지양하고 서버에서 예외를 던져줘서 HTTP상태코드로 오류를 알려줘야 함.
        // TODO sendRedirect()를 정말 쓰고 싶다면 로그인 화면을 html 혹은 jsp와 같은 화면을 하나 만들어서 해당 URL를 던져줘야 오류가 나지 않을 것임.
        // 참고자료 : sendRedirect() - https://www.javatpoint.com/sendRedirect()-method
        // OptionalCode 작성 - https://madplay.github.io/post/how-to-handle-optional-in-java, https://www.daleseo.com/java8-optional-effective/

        /** 수정하기 전 원본코드 */
//        if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
//            log.info("미인증 사용자 요청");
//            // 오류 TEST URL : response.sendRedirect("/test/sendRedirect?redirectURL=" + requestURI);
//             response.sendRedirect("/api/user/login?redirectURL=" + requestURI);
//             return false;
//        }

        //3. 각 권한 분기처리(팀장,임원,팀원)
        //팀 id , 권한
        //Long teamId = Long.parseLong(request.getParameter("teamId"));   //=>pathVariable 로 바꾸어야됨
        final Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long teamId = Long.parseLong(pathVariables.get("teamSeq"));
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
