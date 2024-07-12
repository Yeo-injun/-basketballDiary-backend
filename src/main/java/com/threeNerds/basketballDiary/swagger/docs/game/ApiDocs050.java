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
    summary     = "[ API050 ] 경기 기록 확정하기",
    description =
        "입력한 경기 기록을 확정한다."
        + "경기 기록이 확정되면 경기 기록은 수정할 수 없다."
        + "경기 기록은 경기의 참가팀이 확정된 상태에서만 기록을 확정지을 수 있다.",
    responses   = {
        @ApiResponse(
            responseCode    = "200",
            description     = "경기 기록이 확정되었습니다."
        ),
        @ApiResponse(
            responseCode    = "403_1",
            description     = "경기 기록이 이미 확정된 상태입니다."
        ),
        @ApiResponse(
            responseCode    = "403_2",
            description     = "경기 기록을 확정지을 수 없는 상태입니다. 경기 진행상태를 확인해주시기 바랍니다."
        ),
    }
)
public @interface ApiDocs050 { }
