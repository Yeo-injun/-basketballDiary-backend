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
    summary     = "[ API004 ] 소속팀 팀원에서 제명하기",
    description = "팀원의 퇴장 상태를 'Y'로 변경한다.<br>"
                + "[ 참고사항 ]<br>"
                + "1. 소속팀의 팀원 데이터의 활동 여부가 'N'으로 관리된다.<br>",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "팀원에서 제명됐습니다.."
        ),
    }
)
public @interface ApiDocs004 { }
