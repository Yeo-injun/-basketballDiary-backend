package com.threeNerds.basketballDiary.mvc.user.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;


@Target( METHOD )
@Retention( RetentionPolicy.RUNTIME )
@Operation(
    summary     = "[ API029 ] 회원가입",
    description = "회원가입을 한다.",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "회원가입을 완료했습니다."
        ),
        @ApiResponse(
            responseCode = "409",
            description  = "동일한 회원ID가 존재합니다."
        ),
    }
)
public @interface ApiDocs029 { }
