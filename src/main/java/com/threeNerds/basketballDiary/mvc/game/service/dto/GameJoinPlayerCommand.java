package com.threeNerds.basketballDiary.mvc.game.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameJoinPlayerCommand {

    /**
     * Insert 처리시 참조 속성
     */
    private Long gameSeq;
    private String homeAwayCode;
    private String playerTypeCode;      // 선수유형코드
    private Long userSeq;               // 사용자Seq
    private String userName;            // 사용자이름
    private String backNumber;          // 등번호
    private String positionCode;        // 포지션코드
    private String email;               // 이메일

    /**
     * Update/Delete 처리시 참조 속성
     */
    private Long gameJoinPlayerSeq;
}
