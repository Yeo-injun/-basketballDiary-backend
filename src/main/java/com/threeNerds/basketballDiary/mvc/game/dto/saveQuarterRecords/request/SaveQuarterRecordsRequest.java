package com.threeNerds.basketballDiary.mvc.game.dto.saveQuarterRecords.request;

import com.threeNerds.basketballDiary.mvc.game.dto.HomeAwayTeamRecordDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.PlayerRecordDTO;
import lombok.Getter;

import java.util.List;


@Getter
public class SaveQuarterRecordsRequest {
    // 경기기록 DTO (단건)
    HomeAwayTeamRecordDTO homeAwayTeamRecordDTO;
    // 선수기록 DTO (목록)
    List<PlayerRecordDTO> playerRecordDTOList;
}
