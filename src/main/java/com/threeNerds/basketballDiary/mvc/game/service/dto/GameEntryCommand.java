package com.threeNerds.basketballDiary.mvc.game.service.dto;

import com.threeNerds.basketballDiary.mvc.game.mapper.dto.PlayerInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameEntryCommand {

    private Long gameSeq;
    private Long gameJoinTeamSeq;
    private String quarterCode;
    private String homeAwayCode;
    private List<PlayerInfoDTO> entry;

}
