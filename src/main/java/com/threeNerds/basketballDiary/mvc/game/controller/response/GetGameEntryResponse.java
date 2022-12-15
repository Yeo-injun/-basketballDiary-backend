package com.threeNerds.basketballDiary.mvc.game.controller.response;

import com.threeNerds.basketballDiary.mvc.game.dto.PlayerRecordDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.QuarterPlayerRecordDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class GetGameEntryResponse {

    private List<QuarterPlayerRecordDTO> playerList;

    public GetGameEntryResponse playerList(List<QuarterPlayerRecordDTO> playerList) {
        this.playerList = playerList;
        return this;
    }
}
