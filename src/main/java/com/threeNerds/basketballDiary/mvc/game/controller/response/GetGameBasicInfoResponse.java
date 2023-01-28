package com.threeNerds.basketballDiary.mvc.game.controller.response;

import com.threeNerds.basketballDiary.mvc.game.dto.GameInfoDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.QuarterPlayerRecordDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class GetGameBasicInfoResponse {

    private GameInfoDTO gameInfo;

    public GetGameBasicInfoResponse gameInfo(GameInfoDTO gameInfo) {
        this.gameInfo = gameInfo;
        return this;
    }
}
