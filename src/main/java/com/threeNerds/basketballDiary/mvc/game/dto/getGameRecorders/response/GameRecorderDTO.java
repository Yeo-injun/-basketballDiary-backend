package com.threeNerds.basketballDiary.mvc.game.dto.getGameRecorders.response;

import com.threeNerds.basketballDiary.constant.code.GameRecordAuthCode;
import com.threeNerds.basketballDiary.constant.code.PlayerTypeCode;
import com.threeNerds.basketballDiary.constant.code.PositionCode;
import lombok.Getter;

@Getter
public class GameRecorderDTO {

    private Long gameRecordAuthSeq;
    private Long teamSeq;
    private Long userSeq;
    private String playerTypeCode;
    private String playerTypeCodeName;
    private String teamName;
    private String userName;
    private String positionCode;
    private String positionCodeName;
    private String backNumber;
    private String email;
    private String gameRecordAuthCode;
    private String gameRecordAuthCodeName;

    public void setPlayerTypeCode( String playerTypeCode ) {
        this.playerTypeCode     = playerTypeCode;
        this.playerTypeCodeName = PlayerTypeCode.nameOf( playerTypeCode );
    }
    public void setPositionCode( String positionCode ) {
        this.positionCode = positionCode;
        this.positionCodeName = PositionCode.nameOf( positionCode );
    }
    public void setGameRecordAuthCode( String gameRecordAuthCode ) {
        this.gameRecordAuthCode =  gameRecordAuthCode;
        this.gameRecordAuthCodeName  = GameRecordAuthCode.nameOf( gameRecordAuthCode );
    }
}
