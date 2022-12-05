package com.threeNerds.basketballDiary.mvc.game.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class QuarterPlayerRecordDTO {

    private Long quarterPlayerRecordsSeq;
    private Long gameSeq;
    private Long gameJoinTeamSeq;
    private String quarterCode;
    private String quarterCodeName;

    private Long gameJoinPlayerSeq;

    private String inGameYn;

    private String playerTypeCode;
    private String playerTypeCodeName;
    private String name;
    private String backNumber;
    private String positionCode;
    private String positionCodeName;

    private Integer tryFreeThrow;
    private Integer freeThrow;

    private Integer tryTwoPoint;
    private Integer twoPoint;

    private Integer tryThreePoint;
    private Integer threePoint;

    private Integer rebound;
    private Integer steal;
    private Integer block;
    private Integer turnover;
    private Integer foul;

}
