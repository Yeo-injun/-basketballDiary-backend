package com.threeNerds.basketballDiary.mvc.game.repository;

import com.threeNerds.basketballDiary.mvc.game.domain.GameJoinTeam;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface GameJoinTeamRepository {

    /**********
     * SELECT
     **********/
    /** 게임에 참가한 팀 조회 - 홈/어웨이팀 단건 */
    GameJoinTeam findGameJoinTeam( GameJoinTeam gameJoinTeam );

    /** 게임에 모든 참가한 팀 조회 */
    List<GameJoinTeam> findAllGameJoinTeam( Long gameSeq );

    /**********
     * INSERT
     **********/
    /** 게임참가팀저장 */
    int saveGameJoinTeam( GameJoinTeam gameJoinTeam );

    /**********
     * UPDATE
     **********/
    /** 게임참가팀저장 */
    int updateTeamName( GameJoinTeam gameJoinTeam );

    /**********
     * DELETE
     **********/
    int deleteByGame( Long gameSeq );

}
