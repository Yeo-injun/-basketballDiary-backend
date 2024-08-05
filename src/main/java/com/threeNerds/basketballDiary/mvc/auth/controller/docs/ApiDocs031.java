package com.threeNerds.basketballDiary.mvc.auth.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;


@Target( METHOD )
@Retention( RetentionPolicy.RUNTIME )
@Operation(
    summary     = "[ API031 ] 로그아웃",
    description = "로그인한 사용자를 로그아웃한다. <br>"
                + "로그아웃 결과 사용자의 세션이 만료된다. <br>",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "로그아웃에 성공했습니다."
        ),
    }
)
public @interface ApiDocs031 { }
