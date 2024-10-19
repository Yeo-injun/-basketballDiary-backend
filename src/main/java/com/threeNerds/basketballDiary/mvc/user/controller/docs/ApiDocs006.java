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
    summary     = "[ API006 ] 사용자 검색 ( 팀원 제외 )",
    description = "농구일기에 가입한 사용자를 조회한다.<br>"
                + "사용자 조회시 같은 팀에 소속된 사용자는 조회 결과에서 제외된다.<br>",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "사용자를 조회했습니다."
        ),
    }
)
public @interface ApiDocs006 { }
