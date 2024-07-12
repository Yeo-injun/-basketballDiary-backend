package com.threeNerds.basketballDiary.swagger.docs.game;

import com.threeNerds.basketballDiary.exception.error.DomainErrorResponse;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
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
    summary     = "[ API068 ] 경기참가선수 삭제 ( 단건 )",
    description =
            "경기에 참가하는 선수를 한명씩 삭제한다.",
    responses   = {
        @ApiResponse(
            responseCode    = "200",
            description     = "경기에 참가한 선수를 삭제했습니다."
        ),
        @ApiResponse(
            responseCode    = "403_1",
            description     = "경기참가선수를 변경할 수 없습니다. 참가선수는 경기의 참가팀이 확정된 상태에서만 변경할 수 있습니다.",
            content         = @Content( schema = @Schema( implementation = DomainErrorResponse.class ) )
        ),
        @ApiResponse(
            responseCode    = "403_2",
            description     = "이미 쿼터기록이 입력되고 있어 선수 추가가 불가합니다.",
            content         = @Content( schema = @Schema( implementation = DomainErrorResponse.class ) )
        ),
        @ApiResponse(
            responseCode    = "403_3",
            description     = "경기기록 권한을 가지고 있는 선수는 삭제할 수 없습니다.",
            content         = @Content( schema = @Schema( implementation = DomainErrorResponse.class ) )
        )
    }
)
public @interface ApiDocs068 { }
