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
    summary     = "[ API001 ] 소속팀 운영진 조회",
    description = "소속된 팀의 운영진 목록을 조회한다.<br>"
                + "[ 참고사항 ]<br>"
                + "1. 페이징 처리 없이 전제 목록을 조회한다.<br>",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "소속팀의 운영진 목록을 조회했습니다."
        ),
    }
)
public @interface ApiDocs001 { }
