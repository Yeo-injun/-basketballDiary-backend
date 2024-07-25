package com.threeNerds.basketballDiary.mvc.game.controller.docs;

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
    summary     = "[ API055 ] 경기기록원 조회",
    description =
        "경기를 기록할 수 있는 권한을 가진 사용자를 조회한다." +
        "경기기록원은 경기를 생성한 경기생성자와 경기 기록권한을 부여받은 경기기록자 모두를 포함한다.",
    responses   = {
        @ApiResponse(
            responseCode = "200"
        ),
    }
)
public @interface ApiDocs055 { }
