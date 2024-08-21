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
    summary     = "[ API015 ] 소속팀 관리자에서 제명하기",
    description = "팀 권한수준을 관리자에서 팀원으로 변경한다.<br>"
                + "[ 참고사항 ]<br>"
                + "1. 권한수준이 관리자인 경우에만 제명될 수 있다.<br>",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "관리자에서 제명됐습니다."
        ),
        @ApiResponse(
            responseCode = "403",
            description  = "관리자가 아닙니다. 팀장이나 팀원은 관리자에서 해임할 수 없습니다."
        ),
    }
)
public @interface ApiDocs015 { }
