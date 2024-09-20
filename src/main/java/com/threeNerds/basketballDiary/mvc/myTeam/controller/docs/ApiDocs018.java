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
    summary     = "[ API018 ] 소속팀 삭제",
    description = "소속팀을 삭제한다.<br>"
            + "[ 참고사항 ]<br>"
            + "1. 팀의 팀장만 팀을 삭제할 수 있다.<br>"
            + "2. 팀이 삭제 되어도 소속팀의 경기기록은 유지된다.",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "팀을 삭제했습니다."
        ),
        @ApiResponse(
            responseCode = "403",
            description  = "팀장만 팀을 삭제할 수 있습니다."
        ),
        @ApiResponse(
            responseCode = "404",
            description  = "팀 정보가 존재하지 않습니다."
        ),
    }
)
public @interface ApiDocs018 {
}
