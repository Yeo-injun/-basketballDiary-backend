package com.threeNerds.basketballDiary.mvc.authUser.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;


@Target( METHOD )
@Retention( RetentionPolicy.RUNTIME )
@Operation(
    summary     = "[ API024 ] 팀 가입요청 취소",
    description = "사용자가 팀에 보낸 가입요청을 취소한다.<br>"
                + "가입요청의 상태를 '취소' 상태로 변경한다. <br>",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "팀 가입요청을 취소했습니다."
        ),
        @ApiResponse(
            responseCode = "404",
            description  = "취소할 가입요청이 존재하지 않습니다."
        ),
    }
)
public @interface ApiDocs024 { }
