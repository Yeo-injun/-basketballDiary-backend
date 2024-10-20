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
    summary     = "[ API028 ] 회원탈퇴",
    description = "회원 탈퇴를 한다.<br>"
                   + "비밀번호를 확인한 후 탈퇴 처리한다.",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "탈퇴 완료했습니다."
        ),
        @ApiResponse(
            responseCode = "400",
            description  = "비밀번호가 일치하지 않습니다."
        ),
        @ApiResponse(
            responseCode = "404",
            description  = "탈퇴처리할 사용자정보가 존재하지 않습니다."
        ),
    }
)
public @interface ApiDocs028 { }
