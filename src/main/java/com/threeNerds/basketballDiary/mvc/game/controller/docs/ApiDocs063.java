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
    summary     = "[ API063 ] 경기의 전체 쿼터 기록 조회",
    description =
        "한 경기의 전체 쿼터 기록을 조회한다.<br>"
        + "쿼터별 기록은 팀별 스코어와 파울 수가 반영된다.<br>"
        ,
    responses   = {
        @ApiResponse(
            responseCode    = "200",
            description     = "경기의 전체 쿼터 기록을 조회했습니다."
       ),
        @ApiResponse(
            responseCode    = "404",
            description     = "경기 정보가 존재하지 않습니다."
        ),
    }
)
public @interface ApiDocs063 { }
