package com.threeNerds.basketballDiary.auth.validation.team;


import com.threeNerds.basketballDiary.auth.validation.RequiredLogin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@RequiredLogin
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiredTeamAuth {

    TeamAuth type() default TeamAuth.NONE;


}
