package com.threeNerds.basketballDiary.mvc.myTeam.dto;

import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import lombok.Getter;

@Getter
public class GameJoinTeamRecordDTO {

    private Long gameJoinTeamSeq;
    private Long teamSeq;

    private String teamName;
    private String homeAwayCode;
    private String homeAwayCodeName;

    // 게임 총점수
    private Integer gameTotalScore;
    private Integer quarterScore1st;
    private Integer quarterScore2nd;
    private Integer quarterScore3rd;
    private Integer quarterScore4th;

    public GameJoinTeamRecordDTO homeAwayCodeName(String homeAwayCode) {
        this.homeAwayCodeName = HomeAwayCode.nameOf(homeAwayCode);
        return this;
    }
    public GameJoinTeamRecordDTO gameTotalScore(Integer gameTotalScore) {
        this.gameTotalScore = gameTotalScore;
        return this;
    }
    public GameJoinTeamRecordDTO quarterScore1st(Integer quarterScore1st) {
        this.quarterScore1st = quarterScore1st;
        return this;
    }
    public GameJoinTeamRecordDTO quarterScore2nd(Integer quarterScore2nd) {
        this.quarterScore2nd = quarterScore2nd;
        return this;
    }
    public GameJoinTeamRecordDTO quarterScore3rd(Integer quarterScore3rd) {
        this.quarterScore3rd = quarterScore3rd;
        return this;
    }
    public GameJoinTeamRecordDTO quarterScore4th(Integer quarterScore4th) {
        this.quarterScore4th = quarterScore4th;
        return this;
    }
}
