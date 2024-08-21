package com.threeNerds.basketballDiary.mvc.myTeam.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;


@Target( METHOD )
@Retention( RetentionPolicy.RUNTIME )
@Operation(
    summary     = "[ API003 ] 소속팀 관리자로 임명하기",
    description = "팀의 권한수준을 팀원에서 관리자로 변경한다.<br>"
                + "[ 참고사항 ]<br>"
                + "1. 권한수준이 팀원인 경우에만 관리자로 임명될 수 있다.<br>",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "팀원을 관리자로 임명했습니다."
        ),
        @ApiResponse(
            responseCode = "403",
            description  = "팀원이 아닙니다. 팀원만 관리자로 임명할 수 있습니다."
        ),
    }
)
public @interface ApiDocs003 { }
