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
    summary     = "[ API052 ] 팀의 경기 목록 검색",
    description = "팀이 참가한 경기 목록을 검색한다.<br>"
                + "[ 참고사항 ]<br>"
                + "1. 페이징 처리된 목록을 조회한다. ( 기본 조회 : 5건 )<br>",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "팀의 경기 목록을 조회합니다."
        ),
    }
)
public @interface ApiDocs052 { }
