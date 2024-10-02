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
        summary     = "[ API021 ] 팀 생성",
        description = "팀을 새롭게 생성한다.<br>"
                + "[ 참고사항 ]<br>"
                + "1. 팀을 생성한 사용자는 팀장 권한을 갖는다.<br>",
        responses   = {
                @ApiResponse(
                        responseCode = "200",
                        description  = "팀 생성을 완료했습니다."
                ),
        }
)
public @interface ApiDocs021 {
}
