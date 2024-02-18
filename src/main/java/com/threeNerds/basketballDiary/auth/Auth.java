package com.threeNerds.basketballDiary.auth;

import com.threeNerds.basketballDiary.auth.constant.AuthLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Auth {
    AuthLevel level() default AuthLevel.MEMBER;

    /**
     * 권한을 체크하는 유형구분
     * - team : 팀권한을 체크기하는 것
     * - game : 경기권한을 체크하는 것 ( 경기기록생성자, 경기기록자 )
     */
    String type() default "team";
}
