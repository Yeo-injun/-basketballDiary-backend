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
    summary     = "[ API024 ] 농구팀 초대 승인",
    description = "사용자가 농구 팀이 보낸 초대요청을 승인한다..<br>"
                + "초대요청의 상태를 '승인' 상태로 변경한다. <br>",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "농구팀 초대요청을 승인했습니다."
        ),
        @ApiResponse(
            responseCode = "404",
            description  = "승인할 초대요청이 존재하지 않습니다."
        ),
    }
)
public @interface ApiDocs024 { }
