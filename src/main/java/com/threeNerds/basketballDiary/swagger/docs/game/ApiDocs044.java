package com.threeNerds.basketballDiary.swagger.docs.game;

import com.threeNerds.basketballDiary.exception.error.DomainErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Target( METHOD )
@Retention( RetentionPolicy.RUNTIME )
@Operation(
    summary     = "[ API044 ] 상대팀 목록 조회",
    description =
            "교류전으로 생성한 경기에서 상대팀으로 지정할 수 있는 팀의 목록을 조회한다." +
            "제약사항 : .... ",
    responses   = {
        @ApiResponse(
            responseCode    = "200",
            description     = "팀목록을 조회했습니다.",
            content         = @Content( mediaType = MediaType.APPLICATION_JSON_VALUE )
        ),
    }
)
public @interface ApiDocs044 {
}
