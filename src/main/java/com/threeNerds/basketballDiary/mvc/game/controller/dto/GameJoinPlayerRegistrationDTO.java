package com.threeNerds.basketballDiary.mvc.game.controller.dto;

import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinPlayerDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.GameJoinTeamRecordDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class GameJoinPlayerRegistrationDTO {

    private Long gameSeq;
    private Long gameJoinTeamSeq;
    private List<GameJoinPlayerDTO> gameJoinPlayerDTOList;

    public GameJoinPlayerRegistrationDTO gameSeq(Long gameSeq) {
        this.gameSeq = gameSeq;
        return this;
    }
    public GameJoinPlayerRegistrationDTO gameJoinTeamSeq(Long gameJoinTeamSeq) {
        this.gameJoinTeamSeq = gameJoinTeamSeq;
        return this;
    }
    public GameJoinPlayerRegistrationDTO gameJoinPlayerDTOList(List<GameJoinPlayerDTO> gameJoinPlayerDTOList) {
        this.gameJoinPlayerDTOList = gameJoinPlayerDTOList;
        return this;
    }

}
