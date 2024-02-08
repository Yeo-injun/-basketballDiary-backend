package com.threeNerds.basketballDiary.mvc.game.dto;


import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import com.threeNerds.basketballDiary.constant.code.type.PlayerTypeCode;
import com.threeNerds.basketballDiary.constant.code.type.PositionCode;
import com.threeNerds.basketballDiary.constant.code.type.QuarterCode;

import lombok.Getter;

@Getter
public class QuarterPlayerRecordDTO {

    private Long quarterPlayerRecordsSeq;
    private Long gameSeq;
    private String homeAwayCode;
    private String homeAwayCodeName;
    private Long gameJoinTeamSeq;
    private String quarterCode;
    private String quarterCodeName;
    private Long gameJoinPlayerSeq;

    private String inGameYn;

    private String playerTypeCode;
    private String playerTypeCodeName;
    private String name;
    private String userName; // TODO 임시 속성 name 속성과 통합 필요
    private String email;   // 엔트리의 key역할
    private String backNumber;
    private String positionCode;
    private String positionCodeName;

    private int totalScore;
    private int freeThrow;
    private int twoPoint;
    private int threePoint;

    private int tryFreeThrow;
    private int tryTwoPoint;
    private int tryThreePoint;

    private int rebound;
    private int assist;
    private int steal;
    private int block;
    private int turnover;
    private int foul;

    /** 참고자료 : https://lotuus.tistory.com/75
     * SQL 조회결과를 Mybatis가 java객체로 매핑시키는 방법
     * 1. 클래스에 setter가 있으면 setter를 호출한다.
     * 2. setter가 없다면 필드 이름으로 맵핑한다.
     * 3. 직접 정의한 생성자(모든 필드가 있는 생성자 포함)는 DB 출력 컬럼순서와 생성자에 정의된 파라미터 순서가 같아야한다.
     * 4. 기본 생성자 또는 순서를 맞춘 모든 필드가 있는 생성자를 반드시 생성해주자
     */
    private void setHomeAwayCode(String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        this.homeAwayCodeName = HomeAwayCode.nameOf(homeAwayCode);
    }

    private void setQuarterCode(String quarterCode) {
        this.quarterCode = quarterCode;
        this.quarterCodeName = QuarterCode.nameOf(quarterCode);
    }

    private void setPlayerTypeCode(String playerTypeCode) {
        this.playerTypeCode = playerTypeCode;
        this.playerTypeCodeName = PlayerTypeCode.nameOf(playerTypeCode);
    }

    private void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
        this.positionCodeName = PositionCode.nameOf(positionCode);
    }

    private void setFreeThrow(int freeThrow) {
        this.freeThrow = freeThrow;
        this.totalScore += freeThrow;
    }
    private void setTwoPoint(int twoPoint) {
        this.twoPoint = twoPoint;
        this.totalScore += ( twoPoint * 2 );
    }
    private void setThreePoint(int threePoint) {
        this.threePoint = threePoint;
        this.totalScore += ( threePoint * 3 );
    }
}
