package com.threeNerds.basketballDiary.mvc.game.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameJoinCommand {
    private Long gameSeq;               /* 경기Seq */
    private Long userSeq;               /* 사용자Seq - 세션값 할당 */
    private Long teamSeq;               /* 팀Seq - 소속팀이 맞는지 확인용 */
}