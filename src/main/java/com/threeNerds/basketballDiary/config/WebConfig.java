package com.threeNerds.basketballDiary.config;

import com.threeNerds.basketballDiary.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final String[] allowedOrigins = {
        "http://127.0.0.1:5500",
        "http://localhost:5500",
        "http://192.168.0.3:5500",
        // 개발서버 WS Domain ( http 기본 포트 80 반영 )
        "http://changkeeroom.iptime.org",
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/api/user/registration", "/api/user/login", "/api/user/logout", "/users/new","/login","/logout","/css/**","/*ico","/error");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/api/**")                      // /api 하위 API call 허용
                .allowedMethods("*")
                // CORS은 기본헤더만 접근가능하게 함. 따라서 브라우저에서는 set-Cookie 헤더에 접근이 불가함. 접근을 허용하기 위해 설정을 추가
                // https://bogmong.tistory.com/5
                .allowedHeaders("*")
                .exposedHeaders( "Set-Cookie", "Location" )
                .allowedOrigins(this.allowedOrigins) // TODO allowedCredentials이 true이면  *(와일드 카드)는 사용 못함. Origin을 명시해줘야 함
                .allowCredentials(true);
    }
}
