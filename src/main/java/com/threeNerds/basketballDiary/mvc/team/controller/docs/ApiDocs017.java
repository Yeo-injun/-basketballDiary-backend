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
    summary     = "[ API017 ] 소속팀 정보 수정",
    description = "소속팀의 정보를 수정한다.<br>"
            + "[ 참고사항 ]<br>"
            + "1. 소속팀의 관리자 이상만 소속팀 정보를 수정할 수 있다.<br>",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "소속팀의 정보를 수정했습니다."
        ),
        @ApiResponse(
            responseCode = "404",
            description  = "소속팀이 존재하지 않습니다. /  TODO 예외 응답메세지 case 세분화"
        ),
    }
)
public @interface ApiDocs017 {
}
