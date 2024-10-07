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
        summary     = "[ API007 ] 회원에게 초대 요청 보내기",
        description = "소속된 팀에서 관리자 이상의 권한을 가진 사용자가 회원에게 팀 초대 요청을 보낸다.<br>"
                + "[ 참고사항 ]<br>"
                + "1. 초대요청을 보내기 위해서는 관리자 이상의 권한을 가지고 있어야 한다.<br>",
        responses   = {
            @ApiResponse(
                responseCode = "200",
                description  = "초대 요청을 성공적으로 보냈습니다."
            ),
            @ApiResponse(
                responseCode = "409_1",
                description  = "아직 처리 대기중인 가입요청이 존재합니다."
            ),
            @ApiResponse(
                responseCode = "409_2",
                description  = "이미 팀원으로 존재합니다."
            ),
        }
)
public @interface ApiDocs007 {
}
