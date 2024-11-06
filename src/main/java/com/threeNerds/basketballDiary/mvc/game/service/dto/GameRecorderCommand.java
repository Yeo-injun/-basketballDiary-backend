package com.threeNerds.basketballDiary.mvc.game.service.dto;

import com.threeNerds.basketballDiary.mvc.game.mapper.dto.GameRecorderDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameRecorderCommand {
    private Long gameSeq;
    private GameRecorderDTO gameRecorder;
}
