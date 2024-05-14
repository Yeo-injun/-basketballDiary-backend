package com.threeNerds.basketballDiary.mvc.game.service.dto;

import com.threeNerds.basketballDiary.mvc.game.dto.GameRecorderDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameRecorderCommand {
    private Long gameSeq;
    private List<GameRecorderDTO> gameRecorders;
}
