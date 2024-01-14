package com.threeNerds.basketballDiary.interceptor;

import com.threeNerds.basketballDiary.constant.UserAuthConst;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Auth {
    /** annotation member**/
    /**
     * 1. primitive
     * 2. String
     * 3. an Enum
     * 4. another Annotation
     * 5. Class
     * 6. an array of any of the above
     * => don't use 'wrapper class'
     */
    long GRADE() default UserAuthConst.USER;
}
