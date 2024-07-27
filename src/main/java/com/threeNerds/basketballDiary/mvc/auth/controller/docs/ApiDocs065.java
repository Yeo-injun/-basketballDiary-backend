package com.threeNerds.basketballDiary.mvc.auth.controller.docs;

import com.threeNerds.basketballDiary.exception.error.DomainErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Target( METHOD )
@Retention( RetentionPolicy.RUNTIME )
@Operation(
    summary     = "[ API065 ] 권한정보 조회",
    description = "로그인한 사용자의 권한정보를 조회한다. <br>"
                + "권한정보는 소속팀에 대한 권한과 경기기록에 대한 권한으로 나눠서 관리된다. <br>"
                + "로그. <br>"
                + "<strong>[ 제약사항 ]</strong>"
                + "<li>경기 기록 권한을 가지고 있어야 한다.</li>",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "사용자의 권한정보를 조회했습니다."
        ),
    }
)
public @interface ApiDocs065 { }
