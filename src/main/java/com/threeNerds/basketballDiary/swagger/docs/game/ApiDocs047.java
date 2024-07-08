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
    summary     = "[ API047 ] 경기 참가팀 조회",
    description = "경기Seq로 경기에 참가한 Home/Away팀의 정보를 조회한다.",
    responses   = {
        @ApiResponse(
            responseCode    = "200",
            description     = "경기에 참가한 팀의 정보를 조회했습니다.",
            content         = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE )
        ),
    }
)
public @interface ApiDocs047 { }
