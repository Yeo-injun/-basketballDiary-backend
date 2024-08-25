package com.threeNerds.basketballDiary.mvc.myTeam.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;


@Target( METHOD )
@Retention( RetentionPolicy.RUNTIME )
@Operation(
    summary     = "[ API005 ] 초대한 사용자 목록 조회",
    description = "소속된 팀에서 초대한 사용자 목록을 조회한다.<br>"
                + "[ 참고사항 ]<br>"
                + "1. 소속팀의 팀원 데이터의 활동 여부가 'N'으로 관리된다.<br>",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "초대한 사용자 목록을 조회했습니다."
        ),
    }
)
public @interface ApiDocs005 { }
