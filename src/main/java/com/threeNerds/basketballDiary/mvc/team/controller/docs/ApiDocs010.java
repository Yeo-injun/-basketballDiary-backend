package com.threeNerds.basketballDiary.mvc.team.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Target( METHOD )
@Retention( RetentionPolicy.RUNTIME )
@Operation(
    summary     = "[ API010 ] 받은 팀가입요청 거절",
    description = "사용자에게 받은 팀가입요청을 거절한다.<br>"
            + "[ 참고사항 ]<br>"
            + "1. 팀 가입요청은 관리자 이상의 권한을 가지고 있는 팀원이 거절할 수 있다.<br>",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "사용자의 팀가입요청을 거절했습니다."
        ),
        @ApiResponse(
            responseCode = "403",
            description  = "팀이 처리 할 수 있는 가입요청이 아닙니다."
        ),
    }
)
public @interface ApiDocs010 {
}
