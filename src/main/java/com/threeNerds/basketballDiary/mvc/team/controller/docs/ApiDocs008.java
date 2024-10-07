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
        summary     = "[ API008 ] 소속팀이 받은 가입요청목록 조회",
        description = "사용자에게 받은 팀가입요청 목록을 조회한다.<br>"
                + "[ 참고사항 ]<br>"
                + "1. 팀의 관리자 이상의 권한을 가진 사용자만 조회가능한다. <br>"
                + "2. 팀가입요청의 상태가 '대기중', '거절' 상태인 것만 조회가능하다. <br>",
        responses   = {
            @ApiResponse(
                responseCode = "200",
                description  = "팀가입요청 목록을 조회했습니다."
            ),
            @ApiResponse(
                responseCode = "403",
                description  = "소속된 팀원만 조회할 수 있습니다."
            ),
        }
)

public @interface ApiDocs008 {
}
