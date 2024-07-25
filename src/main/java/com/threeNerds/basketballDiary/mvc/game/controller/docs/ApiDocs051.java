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
    summary     = "[ API051 ] 경기 삭제하기",
    description =
        "경기에 대한 정보를 모두 삭제한다."
        + "팀별 쿼터 기록, 참가 선수별 기록 모두 삭제된다."
        + "경기 삭제는 경기 생성자만 할 수 있다.",
    responses   = {
        @ApiResponse(
            responseCode    = "200",
            description     = "경기 정보를 모두 삭제했습니다."
        ),
        @ApiResponse(
            responseCode    = "404",
            description     = "삭제할 경기정보가 존재하지 않습니다."
        ),
    }
)
public @interface ApiDocs051 { }
