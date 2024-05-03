package com.threeNerds.basketballDiary.mvc.game.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameJoinPlayerRecordQuery {

    private Long gameSeq;
    private String quarterCode;
    private String homeAwayCode;

}
