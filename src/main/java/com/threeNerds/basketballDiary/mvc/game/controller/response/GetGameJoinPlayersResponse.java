package com.threeNerds.basketballDiary.mvc.game.controller.response;

import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinTeamDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinTeamInfoDTO;
import lombok.Getter;

import java.util.List;


@Getter
public class GetGameJoinPlayersResponse {

    /**
     * gameSeq : 1,
     * teams : [
     *      {
     *          gameJoinTeamSeq : 1,
     *          homeAwayCode : "01",
     *          homeAwayCodeName : "홈팀",
     *          players : [
     *              {
     *                  gameJoinPlayerSeq : 1,
     *                  playerTypeCode : "01",
     *                  playerTypeCodeName : "팀원",
     *                  userSeq : 1,
     *                  name : "여인준",
     *                  backNumber : "93",
     *                  positionCode : "10",
     *                  positionCodeName : "가드",
     *                  email : "test01@nongguilgi.com"
     *              },
     *              {
     *
     *              },
     *              ...
     *          ],
     *      },
     * ]
     *
     */
    private Long gameSeq;
    private GameJoinTeamDTO homeTeam;
    private GameJoinTeamDTO awayTeam;

    public GetGameJoinPlayersResponse ( Long gameSeq, GameJoinTeamDTO homeTeam, GameJoinTeamDTO awayTeam ) {
        this.gameSeq = gameSeq;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }
}
