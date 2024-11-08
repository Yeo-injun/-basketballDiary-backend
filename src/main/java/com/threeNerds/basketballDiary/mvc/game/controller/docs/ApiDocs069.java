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
    summary     = "[ API069 ] 경기기록 권한 삭제 ( 단건 )",
    description =
            "경기기록 권한을 가진 선수의 권한을 삭제한다. </br>" +
            "[ 제약사항 ]</br>" +
            "1. 권한은 경기기록권한만 삭제된다. ( 경기생성자 권한은 삭제할 수 없다. )",
    responses   = {
        @ApiResponse(
            responseCode    = "200",
            description     = "경기기록권한을 삭제했습니다."
        ),
    }
)
public @interface ApiDocs069 { }
