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
    summary     = "[ API057 ] 경기기록원 후보 조회",
    description =
            "경기기록 입력 권한을 부여할 수 있는 후보들을 조회한다." +
            "경기기록 권한을 가질 수 있는 사람은 다음 2가지 조건을 충족해야 한다." +
            "[1] 서비스에 가입된 회원이어야 한다." +
            "[2] 경기에 참가한 선수여야 한다. ( 소속팀은 상관없다. )"
    ,
    responses   = {
        @ApiResponse(
            responseCode = "200"
        ),
    }
)
public @interface ApiDocs057 { }
