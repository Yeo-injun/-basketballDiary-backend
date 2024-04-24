package com.threeNerds.basketballDiary.mvc.game.dto;

import lombok.Getter;

@Getter
public class SavePlayerRecordDTO {

    /** 쿼터별선수기록 SEQ */
    private Long quarterPlayerRecordsSeq;

    /** 식별자 Group */
    private Long gameSeq;
    private String homeAwayCode;
    private String quarterCode;
    private Long gameJoinPlayerSeq;
    private Long userSeq;

    /** 선수 기본 정보 */
    private String playerTypeCode;
    private String name;
    private String backNumber;
    private String positionCode;

    /** 게임 스탯 */
    private int totalScore;

    private int tryFreeThrow;
    private int tryTwoPoint;
    private int tryThreePoint;

    private int freeThrow;
    private int twoPoint;
    private int threePoint;
    private int assist;
    private int rebound;
    private int steal;
    private int block;
    private int turnover;
    private int foul;
}
