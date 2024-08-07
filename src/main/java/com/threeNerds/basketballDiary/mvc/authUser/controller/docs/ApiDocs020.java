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
    summary     = "[ API020 ] 팀 가입요청 보내기",
    description = "사용자의 로그인을 수행한다. <br>"
                + "로그인 결과로 사용자의 팀별 권한과 경기별 작성권한을 리턴해준다. <br>",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "농구 팀에 가입요청을 보냈습니다."
        ),
        @ApiResponse(
            responseCode = "404",
            description  = "가입요청을 받을 팀 정보가 존재하지 않습니다."
        ),
        @ApiResponse(
            responseCode = "409_1",
            description  = "처리 대기 중인 가입요청이 존재합니다."
        ),
        @ApiResponse(
            responseCode = "409_2",
            description  = "가입 요청한 팀의 팀원입니다."
        ),
    }
)
public @interface ApiDocs020 { }
