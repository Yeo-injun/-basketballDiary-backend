package com.threeNerds.basketballDiary.swagger.docs.game;

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
    summary     = "[ API067 ] 경기참가선수 추가 ( 단건 )",
    description =
            "경기에 참가하는 선수를 한명씩 추가한다.",
    responses   = {
        @ApiResponse(
            responseCode = "200"
        ),
        @ApiResponse(
            responseCode    = "403#1",
            description     = "경기참가선수를 변경할 수 없습니다. 참가선수는 경기의 참가팀이 확정된 상태에서만 변경할 수 있습니다.",
            content         = @Content( schema = @Schema( implementation = DomainErrorResponse.class ) )
        ),
        @ApiResponse(
            responseCode    = "403#2",
            description     = "이미 쿼터기록이 입력되고 있어 선수 추가가 불가합니다.",
            content         = @Content( schema = @Schema( implementation = DomainErrorResponse.class ) )
        ),
        @ApiResponse(
            responseCode    = "404",
            description     = "경기참가팀 정보가 존재하지 않습니다.",
            content         = @Content( schema = @Schema( implementation = DomainErrorResponse.class ) )
        )
    }
)
public @interface ApiDocs067 { }
