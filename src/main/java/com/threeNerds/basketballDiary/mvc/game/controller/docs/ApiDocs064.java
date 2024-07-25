package com.threeNerds.basketballDiary.mvc.game.controller.docs;

import com.threeNerds.basketballDiary.exception.error.DomainErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Target( METHOD )
@Retention( RetentionPolicy.RUNTIME )
@Operation(
    summary     = "[ API064 ] 경기 쿼터 기초정보 생성",
    description =
            "경기의 쿼터 정보를 생성한다. 쿼터 정보를 생성하기 위해서는 경기가 수정가능한 상태( 경기 참가팀이 확정된 상태 )이고 상대팀이 지정된 상태여야 한다."
            + "또한, 각 팀별 경기참가선수가 5명으로 확정된 경우에만 쿼터 정보 생성이 가능하다.",
    responses   = {
        @ApiResponse(
            responseCode    = "200",
            description     = "쿼터 정보를 생성했습니다."
        ),
        @ApiResponse(
            responseCode    = "403_1",
            description     = "경기기록이 확정된 상태입니다. 쿼터기록을 입력할 수 없습니다.",
            content         = @Content( schema = @Schema( implementation = DomainErrorResponse.class ) )
        ),
        @ApiResponse(
            responseCode    = "403_2",
            description     = "경기에 참가하는 팀의 수가 부족합니다.",
            content         = @Content( schema = @Schema( implementation = DomainErrorResponse.class ) )
        ),
        @ApiResponse(
            responseCode    = "400",
            description     = "경기에 참가하는 선수가 부족합니다.",
            content         = @Content( schema = @Schema( implementation = DomainErrorResponse.class ) )
        )
    }
)
public @interface ApiDocs064 { }
