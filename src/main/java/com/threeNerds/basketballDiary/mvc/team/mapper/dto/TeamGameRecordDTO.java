package com.threeNerds.basketballDiary.mvc.team.mapper.dto;

import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import com.threeNerds.basketballDiary.mvc.game.domain.GameJoinTeam;
import com.threeNerds.basketballDiary.mvc.game.domain.QuarterTeamRecords;
import lombok.Getter;

import java.util.List;

@Getter
public class TeamGameRecordDTO {

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

    public TeamGameRecordDTO( GameJoinTeam team, List<QuarterTeamRecords> quarterRecords ) {
        this.gameJoinTeamSeq    = team.getGameJoinTeamSeq();
        this.teamSeq            = team.getTeamSeq();
        this.teamName           = team.getTeamName();
        this.homeAwayCode       = team.getHomeAwayCode();
        this.homeAwayCodeName   = HomeAwayCode.nameOf( team.getHomeAwayCode() );
        if ( quarterRecords.isEmpty() ) {
            this.quarterScore1st = 0;
            this.quarterScore2nd = 0;
            this.quarterScore3rd = 0;
            this.quarterScore4th = 0;
            this.gameTotalScore = 0;
            return;
        }

        int gameTotalScore = 0;
        for ( QuarterTeamRecords qtr : quarterRecords ) {
            qtr.calculateQuarterTotalScore();
            int quarterScore = qtr.getScore();
            if ( qtr.is1stQuater() ) {
                this.quarterScore1st = quarterScore;
            }
            else if ( qtr.is2ndQuater() ) {
                this.quarterScore2nd = quarterScore;
            }
            else if ( qtr.is3rdQuater() ) {
                this.quarterScore3rd = quarterScore;
            }
            else if ( qtr.is4thQuater() ) {
                this.quarterScore4th = quarterScore;
            }
            gameTotalScore += quarterScore;
        }
        this.gameTotalScore = gameTotalScore;
    }
}
