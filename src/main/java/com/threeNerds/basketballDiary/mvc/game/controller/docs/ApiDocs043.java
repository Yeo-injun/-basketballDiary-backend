package com.threeNerds.basketballDiary.mvc.game.controller.docs;

import com.threeNerds.basketballDiary.exception.error.DomainErrorResponse;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Target( METHOD )
@Retention( RetentionPolicy.RUNTIME )
@Operation(
    summary     = "[ API043 ] 쿼터별 경기참가선수들의 경기기록 조회",
    description = "경기 쿼터별로 경기에 참가한 모든 선수들의 경기기록을 조회한다."
                + "[ 제약사항 ] 1. 페이징 처리가 없다. 2. Home/Away팀 모두 한번에 조회한다.",
    responses   = {
        @ApiResponse(
            responseCode    = "200",
            description     = "쿼터별 경기참가선수들의 경기기록을 조회했습니다.",
            content         = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE )
        ),
        @ApiResponse(
            responseCode    = "404",
            description     = "경기 정보가 존재하지 않습니다.",
            content         = @Content( schema = @Schema( implementation = DomainErrorResponse.class ) )
        ),
    }
)
public @interface ApiDocs043 { }
