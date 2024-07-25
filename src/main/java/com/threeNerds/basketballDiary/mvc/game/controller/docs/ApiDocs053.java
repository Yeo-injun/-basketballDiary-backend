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
    summary     = "[ API053 ] 경기 생성하기",
    description =
        "경기 정보를 생성한다.<br>" +
        "경기에 참가하는 Home팀이 등록되고, Away팀은 이후 이후 별도 API를 통해 등록할 수 있다.<br>" +
        "경기를 생성한 사용자에게는 경기 생성자 권한이 부여된다.<br>",
    responses   = {
        @ApiResponse(
            responseCode    = "200",
            description     = "경기를 생성했습니다."
        ),
        @ApiResponse(
            responseCode    = "403",
            description     = "소속된 팀원만 경기를 생성할 수 있습니다."
        ),
        @ApiResponse(
            responseCode    = "404",
            description     = "팀 정보가 존재하지 않습니다."
        ),
    }
)
public @interface ApiDocs053 { }
