package com.threeNerds.basketballDiary.config;

import com.threeNerds.basketballDiary.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/api/**")                      // /api 하위 API call 허용
                .allowedOrigins("*")
                .allowedMethods("*");
                //.allowedOrigins("http://127.0.0.1:5500")    // 로컬 프론트에서만 허용
                //.allowedMethods("GET");                     // GET Method에 대해서만 허용
    }
}
