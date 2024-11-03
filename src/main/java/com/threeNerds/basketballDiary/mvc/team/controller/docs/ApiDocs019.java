package com.threeNerds.basketballDiary.mvc.team.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Target( METHOD )
@Retention( RetentionPolicy.RUNTIME )
@Operation(
        summary     = "[ API019 ] 팀 검색",
        description = "검색 조건에 맞는 팀을 검색한다.<br>",
        responses   = {
                @ApiResponse(
                    responseCode = "200",
                    description  = "팀을 조회했습니다."
                ),
        }
)
public @interface ApiDocs019 {
}
