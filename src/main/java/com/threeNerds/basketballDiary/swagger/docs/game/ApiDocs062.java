package com.threeNerds.basketballDiary.swagger.docs.game;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Target( METHOD )
@Retention( RetentionPolicy.RUNTIME )
@Operation(
    summary     = "[ API062 ] 경기참가팀 확정",
    description =
            "경기에 참여하는 어웨이 팀을 확정한다.<br>"
            + "cf. 홈팀은 경기 정보가 생성된 시점에 경기를 생성한 사용자의 소속팀으로 지정된다.<br>"
            + "<br>"
            + "<strong>[ 유의사항 ]</strong>"
            + "<li>경기 참가팀을 확정하면 어웨이팀을 수정할 수 없다.</li>"
        ,
    responses   = {
        @ApiResponse(
            responseCode    = "200",
            description     = "경기 참가팀을 확정했습니다."
       ),
        @ApiResponse(
            responseCode    = "400",
            description     = "유효하지 않은 경기유형입니다."
        ),
        @ApiResponse(
            responseCode    = "404",
            description     = "어웨이 팀 정보가 존재하지 않습니다."
        ),
        @ApiResponse(
            responseCode    = "409",
            description     = "경기에 참가한 어웨이 팀이 이미 존재합니다."
        ),
    }
)
public @interface ApiDocs062 { }
