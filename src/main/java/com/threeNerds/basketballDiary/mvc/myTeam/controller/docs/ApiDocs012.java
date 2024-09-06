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
    summary     = "[ API012 ] 소속팀 개인프로필 수정",
    description = "소속팀의 프로필 정보를 수정한다.<br>"
            + "[ 참고사항 ]<br>"
            + "1. 소속팀별 관리 가능한 프로필 정보는 등번호와 프로필 사진이다.<br>"
            + "   - 포지션과 신장, 체중은 모든 소속팀에 동일하게 적용되므로, 개인 프로필 수정API를 통해 수정해야 한다.",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "프로필 수정을 완료했습니다."
        ),
    }
)
public @interface ApiDocs012 {}
