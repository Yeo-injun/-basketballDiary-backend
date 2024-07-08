package com.threeNerds.basketballDiary.swagger.docs.game;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Target( METHOD )
@Retention( RetentionPolicy.RUNTIME )
@Operation(
    summary     = "[ API048 ] 경기 기록 조회 ( 쿼터별 )",
    description = "특정 경기의 특정 쿼터의 기록을 조회한다."
        + "경기에 참가한 Home / Away 팀의 쿼터 기록을 조회한다.",
    responses   = {
        @ApiResponse(
            responseCode    = "200",
            description     = "경기의 쿼터 기록을 조회했습니다.",
            content         = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE )
        ),
        @ApiResponse(
            responseCode    = "404",
            description     = "경기 정보가 존재하지 않습니다.",
            content         = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE )
        ),
    }
)
public @interface ApiDocs048 { }
