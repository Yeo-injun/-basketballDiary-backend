package com.threeNerds.basketballDiary.mvc.game.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameQuarterCommand {
    private Long gameSeq;               /* 경기Seq */
    private String quarterCode;         /* 쿼터 코드 */
}