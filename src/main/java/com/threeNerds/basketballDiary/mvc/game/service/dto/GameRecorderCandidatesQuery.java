package com.threeNerds.basketballDiary.mvc.game.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GameRecorderCandidatesQuery {
    private Long gameSeq;
    private String homeAwayCode;
}
