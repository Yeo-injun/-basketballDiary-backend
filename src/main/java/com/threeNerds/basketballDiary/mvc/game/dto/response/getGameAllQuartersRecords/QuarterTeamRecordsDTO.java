package com.threeNerds.basketballDiary.mvc.game.dto.response.getGameAllQuartersRecords;

import lombok.Getter;

@Getter
public class QuarterTeamRecordsDTO {

    private String homeAwayCode;
    private String homeAwayCodeName;
    private String teamName;
    private Integer foul;
    private Integer score;
}
