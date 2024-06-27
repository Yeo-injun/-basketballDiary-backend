package com.threeNerds.basketballDiary.mvc.game.service.dto;

import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.mvc.game.dto.PlayerInfoDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.QuarterTeamEntryDTO;
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
