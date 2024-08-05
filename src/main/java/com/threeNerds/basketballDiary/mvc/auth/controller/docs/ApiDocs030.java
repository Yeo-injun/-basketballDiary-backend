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
    summary     = "[ API030 ] 로그인",
    description = "사용자의 로그인을 수행한다. <br>"
                + "로그인 결과로 사용자의 팀별 권한과 경기별 작성권한을 리턴해준다. <br>",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "로그인을 성공했습니다."
        ),
        @ApiResponse(
            responseCode = "400",
            description  = "입력한 ID 혹은 비밀번호가 일치하지 않습니다."
        ),
    }
)
public @interface ApiDocs030 { }
