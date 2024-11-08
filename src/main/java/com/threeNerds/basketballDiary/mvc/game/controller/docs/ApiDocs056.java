package com.threeNerds.basketballDiary.mvc.game.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Target( METHOD )
@Retention( RetentionPolicy.RUNTIME )
@Operation(
    summary     = "[ API056 ] 경기기록원 저장",
    description =
        "경기참가선수를 경기기록원으로 저장한다." +
        "경기기록원은 경기를 기록할 수 있는 권한을 가진 사용자를 의미한다." +
        "<p><strong>제약사항</strong></p>" +
        "<li>경기기록원은 경기에 참가한 선수여야 한다.</li>" +
        "<li></li>",
    responses   = {
        @ApiResponse(
            responseCode    = "200",
            description     = "경기 기록원 목록을 저장했습니다."
        ),
    }
)
public @interface ApiDocs056 { }
