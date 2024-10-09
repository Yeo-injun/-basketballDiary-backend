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
    summary     = "[ API032 ] 농구팀에게 받은 초대 목록 조회",
    description = "농구 팀이 보낸 초대 목록을 조회한다.<br>"
                + "[ 참고사항 ]<br>"
                + "1. 페이징 처리 없이 전제 목록을 조회한다.<br>"
                + "2. '초대'상태의 요청만 조회된다.<br>",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "농구팀이 보낸 초대 목록 조회했습니다."
        ),
    }
)
public @interface ApiDocs032 { }
