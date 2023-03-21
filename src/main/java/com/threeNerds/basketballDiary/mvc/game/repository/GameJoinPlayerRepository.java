package com.threeNerds.basketballDiary.mvc.game.repository;

import com.threeNerds.basketballDiary.mvc.game.domain.GameJoinPlayer;
import com.threeNerds.basketballDiary.mvc.game.domain.GameJoinTeam;
import com.threeNerds.basketballDiary.mvc.game.dto.PlayerInfoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameJoinPlayerRepository {

    /**********
     * SELECT
     **********/
    /** 게임참가선수의 참가팀Seq 조회 */
    GameJoinPlayer findPlayer(GameJoinPlayer gameJoinPlayer);

    /** 게임참가선수 목록조회 */
    List<GameJoinPlayer> findPlayers(Long gameJoinTeamSeq);

    /**********
     * INSERT
     **********/
    /** 게임참가선수 저장 */
    int save(GameJoinPlayer gameJoinPlayer);

    /**********
     * UPDATE
     **********/

    /**********
     * DELETE
     **********/
    /** 게임참가선수 삭제(다건) - 게임참가팀Seq로 다건 삭제  */
    int deletePlayers(Long gameJoinTeamSeq);
}
