package com.threeNerds.basketballDiary.mvc.game.dto.getGameJoinPlayerRecordsByQuarter.response;

import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import com.threeNerds.basketballDiary.constant.code.type.PlayerTypeCode;
import com.threeNerds.basketballDiary.constant.code.type.PositionCode;
import com.threeNerds.basketballDiary.constant.code.type.QuarterCode;
import lombok.Getter;

@Getter
public class PlayerQuarterRecordDTO {

    /** 선수기록 식별자 */
    private Long quarterPlayerRecordsSeq;
    private Long gameSeq;
    private String homeAwayCode;
    private String homeAwayCodeName;
    private Long userSeq;
    private String quarterCode;
    private String quarterCodeName;

    /** 선수정보 */
    private String playerTypeCode;
    private String playerTypeCodeName;
    private String name;
    private String backNumber;
    private String positionCode;
    private String positionCodeName;

    /** 기록정보 */
    private int freeThrow;
    private int tryFreeThrow;
    private int twoPoint;
    private int tryTwoPoint;
    private int threePoint;
    private int tryThreePoint;
    private int totalScore;
    private int assist;
    private int rebound;
    private int steal;
    private int block;
    private int turnover;
    private int foul;

    /** 코드이름 세팅 */
    public void setHomeAwayCode( String homeAwayCode ) {
        this.homeAwayCode = homeAwayCode;
        this.homeAwayCodeName = HomeAwayCode.nameOf( homeAwayCode );
    }

    public void setQuarterCode( String quarterCode ) {
        this.quarterCode = quarterCode;
        this.quarterCodeName = QuarterCode.nameOf( quarterCode );
    }

    public void setPlayerTypeCode( String playerTypeCode ) {
        this.playerTypeCode = playerTypeCode;
        this.playerTypeCodeName = PlayerTypeCode.nameOf( playerTypeCode );
    }

    public void setPositionCode( String positionCode ) {
        this.positionCode = positionCode;
        this.positionCodeName = PositionCode.nameOf( positionCode );
    }


}
