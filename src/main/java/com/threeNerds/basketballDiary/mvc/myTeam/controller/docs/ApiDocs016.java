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
        summary     = "[ API016 ] 소속팀 정보 조회",
        description = "소속팀의 정보를 조회한다.<br>"
                + "[ 참고사항 ]<br>"
                + "1. 사용자의 소속팀이 아니면 정보를 조회할 수 없다.<br>",
        responses   = {
            @ApiResponse(
                responseCode = "200",
                description  = "소속팀의 정보를 조회했습니다."
            ),
            @ApiResponse(
                responseCode = "404",
                description  = "소속팀이 존재하지 않습니다. /  TODO 예외 응답메세지 case 세분화"
            ),
        }
)
public @interface ApiDocs016 {
}
