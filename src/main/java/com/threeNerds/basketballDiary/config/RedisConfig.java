package com.threeNerds.basketballDiary.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800)  // 세션 유효 시간 설정 (30분)
public class RedisConfig {

    // 참고자료 : https://ksh-coding.tistory.com/128#2.%20%EC%9D%98%EC%A1%B4%EC%84%B1%20%EC%84%A4%EC%A0%95-1
    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(   redisHost );
        redisStandaloneConfiguration.setPort(       redisPort );
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

}
