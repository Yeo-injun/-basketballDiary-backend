package com.threeNerds.basketballDiary.mvc.game.controller.request;

import com.threeNerds.basketballDiary.mvc.game.service.dto.GameJoinPlayerCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class AddGameJoinPlayerRequest {

    private Long gameSeq;
    private String homeAwayCode;
    @NotNull
    private String playerTypeCode;      // 선수유형코드
    private Long userSeq;               // 사용자Seq
    private String userName;            // 사용자이름
    @NotNull
    private String backNumber;          // 등번호
    private String positionCode;        // 포지션코드
    @NotNull
    private String email;               // 이메일

    public GameJoinPlayerCommand toCommand( Long gameSeq, String homeAwayCode ) {
        return GameJoinPlayerCommand.builder()
                .gameSeq(           gameSeq )
                .homeAwayCode(      homeAwayCode )
                .playerTypeCode(    playerTypeCode )
                .userSeq(           userSeq )
                .userName(          userName )
                .backNumber(        backNumber )
                .positionCode(      positionCode )
                .email(             email )
                .build();
    }
}
