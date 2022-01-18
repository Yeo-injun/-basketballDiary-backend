package com.threeNerds.basketballDiary.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Auth {
    enum Role{
        USER,TEAM_MEMBER, MANAGER, LEADER
    }
    Role role() default Role.USER;
}
