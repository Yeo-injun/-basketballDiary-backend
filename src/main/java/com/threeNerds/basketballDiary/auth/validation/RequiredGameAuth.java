package com.threeNerds.basketballDiary.auth.validation;


import com.threeNerds.basketballDiary.auth.validation.type.GameAuth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@RequiredLogin
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiredGameAuth {

    GameAuth type() default GameAuth.NONE;


}
