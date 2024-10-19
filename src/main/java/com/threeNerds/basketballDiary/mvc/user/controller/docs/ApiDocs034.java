package com.threeNerds.basketballDiary.mvc.user.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;


@Target( METHOD )
@Retention( RetentionPolicy.RUNTIME )
@Operation(
    summary     = "[ API034 ] 사용자ID 사용가능여부 확인",
    description = "사용자ID의 중복여부를 검사한다.<br>"
                + "중복된 사용자ID가 존재할 경우 false를 리턴하고, 존재하지 않을 경우 true를 리턴한다.<br>",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "사용자ID의 중복여부를 확인했습니다."
        ),
    }
)
public @interface ApiDocs034 { }
