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
    summary     = "[ API014 ] 소속팀 목록 조회",
    description = "사용자의 소속팀을 조회한다.<br>"
                + "[ 참고사항 ]<br>"
                + "1. 페이징 단위는 3건 이다.<br>",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "소속팀을 조회했습니다."
        ),
    }
)
public @interface ApiDocs014 {
}
