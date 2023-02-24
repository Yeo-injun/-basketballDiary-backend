package com.threeNerds.basketballDiary.mvc.game.dto;


import com.threeNerds.basketballDiary.constant.code.PlayerTypeCode;
import com.threeNerds.basketballDiary.constant.code.PositionCode;
import com.threeNerds.basketballDiary.constant.code.QuarterCode;
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

    // TODO Integer를 int형으로 모두 바꾸기... 왜냐면 Integer는 연산하기가 불편함.... Optional을 생각보다 많이 않쓴다
    private int totalScore;
    private int tryFreeThrow;
    private int freeThrow;

    private int tryTwoPoint;
    private int twoPoint;

    private int tryThreePoint;
    private int threePoint;

    private Integer rebound;
    private Integer assist;
    private Integer steal;
    private Integer block;
    private Integer turnover;
    private Integer foul;

    /** 참고자료 : https://lotuus.tistory.com/75
     * SQL 조회결과를 Mybatis가 java객체로 매핑시키는 방법
     * 1. 클래스에 setter가 있으면 setter를 호출한다.
     * 2. setter가 없다면 필드 이름으로 맵핑한다.
     * 3. 직접 정의한 생성자(모든 필드가 있는 생성자 포함)는 DB 출력 컬럼순서와 생성자에 정의된 파라미터 순서가 같아야한다.
     * 4. 기본 생성자 또는 순서를 맞춘 모든 필드가 있는 생성자를 반드시 생성해주자
     */
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
