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
    summary     = "[ API046 ] 경기 기초 정보 조회",
    description = "경기Seq로 경기의 기본 정보를 조회한다.",
    responses   = {
        @ApiResponse(
            responseCode    = "200",
            description     = "경기 기본 정보를 조회했습니다.",
            content         = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE )
        ),
    }
)
public @interface ApiDocs046 { }
