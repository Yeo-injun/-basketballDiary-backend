package com.threeNerds.basketballDiary.auth.annotation;


import com.threeNerds.basketballDiary.auth.type.TeamAuth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@RequiredLogin
public @interface AllowedFor {

    TeamAuth type() default TeamAuth.NONE;


}
