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
    summary     = "[ API041 ] 경기 쿼터 정보 삭제",
    description =
            "특정 경기의 지정한 쿼터의 쿼터 정보를 삭제한다.",
    responses   = {
        @ApiResponse(
            responseCode    = "200",
            description     = "쿼터 정보를 삭제했습니다."
        ),
    }
)
public @interface ApiDocs041 { }
