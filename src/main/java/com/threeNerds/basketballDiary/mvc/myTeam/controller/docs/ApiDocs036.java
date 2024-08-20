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
    summary     = "[ API036 ] 소속팀 팀원 전체 목록 조회 ( 운영진 포함 )",
    description = "소속된 팀의 팀원 전체 목록을 조회한다.<br>"
                + "[ 참고사항 ]<br>"
                + "1. 페이징 처리된 목록을 조회한다. ( 기본 조회 : 5건 )<br>"
                + "2. 운영진을 포함한 전체 팀원을 조회한다.<br>",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "소속팀의 팀원 전체 목록을 조회했습니다."
        ),
    }
)
public @interface ApiDocs036 { }
