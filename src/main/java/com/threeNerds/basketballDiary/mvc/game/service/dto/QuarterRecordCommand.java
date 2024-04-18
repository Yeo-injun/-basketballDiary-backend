package com.threeNerds.basketballDiary.mvc.game.service.dto;

import com.threeNerds.basketballDiary.mvc.game.dto.SavePlayerRecordDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuarterRecordCommand {
    private Long gameSeq;
    private String quarterCode;
    private String quarterTime;
    private List<SavePlayerRecordDTO> homeTeamPlayerRecords;
    private List<SavePlayerRecordDTO> awayTeamPlayerRecords;
}
