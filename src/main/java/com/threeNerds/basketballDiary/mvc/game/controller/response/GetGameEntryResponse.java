package com.threeNerds.basketballDiary.mvc.game.controller.response;

import com.threeNerds.basketballDiary.mvc.game.dto.PlayerRecordDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class GetGameEntryResponse {

    private List<PlayerRecordDTO> playerList;

    public GetGameEntryResponse playerList(List<PlayerRecordDTO> playerList) {
        this.playerList = playerList;
        return this;
    }
}
