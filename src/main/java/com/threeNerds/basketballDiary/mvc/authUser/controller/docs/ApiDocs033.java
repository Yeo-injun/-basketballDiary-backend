package com.threeNerds.basketballDiary.mvc.authUser.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;


@Target( METHOD )
@Retention( RetentionPolicy.RUNTIME )
@Operation(
    summary     = "[ API033 ] 농구팀의 초대 거절",
    description = "사용자가 농구 팀이 보낸 팀 초대 요청을 거절한다.<br>"
                + "초대 요청의 상태를 '거절' 상태로 변경한다. <br>",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "농구팀의 초대 요청을 거절했습니다."
        ),
        @ApiResponse(
            responseCode = "404",
            description  = "거절할 농구 팀의 초대정보가 존재하지 않습니다."
        ),
    }
)
public @interface ApiDocs033 { }
