package com.threeNerds.basketballDiary.mvc.game.mapper.dto;

import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import com.threeNerds.basketballDiary.constant.code.type.PlayerTypeCode;
import com.threeNerds.basketballDiary.constant.code.type.PositionCode;
import lombok.Getter;

/**
 * 경기업무 조회용 DTO
 * 경기관련 SEQ 및 코드를 관리할 수 있다.
 * @author 강창기
 */
@Getter
public class GameRecorderCandidateDTO {
    /** 경기 SEQ */
    private Long gameSeq;
    /** 홈어웨이코드 */
    private String homeAwayCode;
    private String homeAwayCodeName;

    private Long gameJoinPlayerSeq;
    private Long teamSeq;
    private Long userSeq;
    private String teamName;
    private String userName;

    private String playerTypeCode;
    private String playerTypeCodeName;
    private String positionCode;
    private String positionCodeName;

    private String email;
    private String backNumber;

    private void setPlayerTypeCode(String playerTypeCode) {
        this.playerTypeCode = playerTypeCode;
        this.playerTypeCodeName = PlayerTypeCode.nameOf(playerTypeCode);
    }

    private void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
        this.positionCodeName = PositionCode.nameOf(positionCode);
    }

    private void setHomeAwayCode(String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        this.homeAwayCodeName = HomeAwayCode.nameOf(homeAwayCode);
    }

    public GameRecorderCandidateDTO gameSeq(Long gameSeq) {
        this.gameSeq = gameSeq;
        return this;
    }
    public GameRecorderCandidateDTO homeAwayCode(String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        return this;
    }
}
