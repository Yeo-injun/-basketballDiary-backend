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
    summary     = "[ API066 ] 경기 참가 선수 조회 ( 팀별 조회 )",
    description =
            "경기에 참가하는 선수들을 홈팀 혹은 어웨이팀 별로 조회한다.<br>"
            + "<strong>[ 유의사항 ]</strong>"
            + "<li>페이징 처리되며, 페이지당 기본 row Count는 5이다.</li>"
        ,
    responses   = {
        @ApiResponse(
            responseCode    = "200",
            description     = "경기에 참가하는 팀의 선수 목록을 조회했습니다."
       ),
        @ApiResponse(
            responseCode    = "404",
            description     = "참가팀 정보가 존재하지 않습니다."
        ),
    }
)
public @interface ApiDocs066 { }
