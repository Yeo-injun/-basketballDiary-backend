package com.threeNerds.basketballDiary.config;

import com.threeNerds.basketballDiary.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * https://spring.io/blog/2015/06/08/cors-support-in-spring-framework
 * Spring에서 제공해주는 Web관련 설정을 위한 interface에 대하 내용이 정리되어 있음
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/","/users/new","/login","/logout","/css/**","/*ico","/error");
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // TODO WebMvcConfigurer.super.addCorsMappings()를 사용한 이유가?? 주석처리해도 정상 동작하니까 삭제하는 것은 어떤지?
//        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/api/**")                      // /api 하위 API call 허용
                .allowedOrigins("http://127.0.0.1:5500", "http://localhost:5500")    // 로컬 프론트에서만 허용
                .allowedMethods("*");     // 모든 Method에 대해서 접근 가능
    }
}
