package com.threeNerds.basketballDiary.mvc.game.repository;

import com.threeNerds.basketballDiary.mvc.game.domain.GameJoinTeam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GameJoinTeamRepository {

    /** 게임에 참가한 팀 조회 - 홈/어웨이팀 단건 */
    GameJoinTeam findGameJoinTeam(GameJoinTeam paramGameJoinTeam);

     /** 게임참가팀저장 */
    int saveGameJoinTeam(GameJoinTeam homeTeamInSelfGame);


}
