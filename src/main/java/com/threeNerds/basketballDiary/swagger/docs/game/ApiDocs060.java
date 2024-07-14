package com.threeNerds.basketballDiary.swagger.docs.game;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Target( METHOD )
@Retention( RetentionPolicy.RUNTIME )
@Operation(
    summary     = "[ API060 ] 쿼터 엔트리 정보 저장",
    description =
            "쿼터별로 경기에 직접 뛰고 있는 선수들의 목록을 저장한다."
        ,
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description =
            "엔트리 목록 저장에 성공했습니다."
       ),
    }
)
public @interface ApiDocs060 { }
