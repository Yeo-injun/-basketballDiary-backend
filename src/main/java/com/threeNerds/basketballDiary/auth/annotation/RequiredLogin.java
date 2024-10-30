package com.threeNerds.basketballDiary.auth.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({
    ElementType.ANNOTATION_TYPE, ElementType.METHOD
})
public @interface RequiredLogin {

    // 로그인이 필요한 API에 어노테이션을 달아서 접근제어
    boolean value() default true;


}
