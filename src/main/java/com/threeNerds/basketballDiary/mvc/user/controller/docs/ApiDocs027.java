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
    summary     = "[ API027 ] 비밀번호 변경",
    description = "회원의 비밀번호를 수정한다.<br>",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "비밀번호를 수정했습니다."
        ),
        @ApiResponse(
            responseCode = "400",
            description  = "비밀번호가 일치하지 않습니다."
        ),
        @ApiResponse(
            responseCode = "404",
            description  = "사용자를 찾을 수 없습니다."
        ),
    }
)
public @interface ApiDocs027 { }
