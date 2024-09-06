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
    summary     = "[ API013 ] 소속팀 탈퇴하기",
    description = "소속팀을 탈퇴한다.<br>"
                + "[ 참고사항 ]<br>"
                + "1. 소속팀을 탈퇴해도 소속팀으로 뛰었던 경기기록은 유지된다.<br>",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "소속팀을 탈퇴했습니다."
        ),
    }
)
public @interface ApiDocs013 {}
