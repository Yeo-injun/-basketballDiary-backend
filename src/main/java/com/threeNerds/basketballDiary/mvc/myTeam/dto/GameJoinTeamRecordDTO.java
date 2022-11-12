package com.threeNerds.basketballDiary.mvc.myTeam.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class GameJoinTeamRecordDTO {

    private Long gameJoinTeamSeq;
    private Long teamSeq;

    private String teamName;
    private String homeAwayCode;
    private String homeAwayCodeName;

    // 쿼터 점수
    private List<QuarterRecordDTO> quarters;

//    private Integer quarter1Socre;
//    private Integer quarter2Socre;
//    private Integer quarter3Socre;
//    private Integer quarter4Socre;


    public GameJoinTeamRecordDTO quarters(List<QuarterRecordDTO> quarters) {
        this.quarters = quarters;
        return this;
    }
}
