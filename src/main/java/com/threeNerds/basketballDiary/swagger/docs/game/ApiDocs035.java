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
    summary     = "[ API035 ] 경기 참가 선수등록하기",
    description =
            "경기에 참가하는 선수들을 목록으로 받아서 일괄로 등록한다."
    ,
    responses   = {
        @ApiResponse(
            responseCode = "200"
        ),
        @ApiResponse(
            responseCode    = "404",
            description     = "팀원을 찾지 못하였습니다.",
            content         = @Content( schema = @Schema( implementation = DomainErrorResponse.class ) )
        )
    }
)
public @interface ApiDocs035 { }
