package com.threeNerds.basketballDiary.swagger.docs.game;

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
    summary     = "[ API040 ] 경기 엔트리 조회하기",
    description = "경기의 특정 쿼터를 지정하여 기록을 저장, 수정한다. / "
                + "[제약사항] : 1. 경기 기록 권한을 가지고 있어야 한다.",
    responses   = {
        @ApiResponse(
            responseCode = "200",
            description  = "경기 엔트리가 조회됐습니다."
        ),
        @ApiResponse(
            responseCode = "404",
            description  = "유효하지 않은 홈/어웨이 코드입니다.",
            content      = @Content( schema = @Schema( implementation = DomainErrorResponse.class ) )
        )
    }
)
public @interface ApiDocs040 {
}
