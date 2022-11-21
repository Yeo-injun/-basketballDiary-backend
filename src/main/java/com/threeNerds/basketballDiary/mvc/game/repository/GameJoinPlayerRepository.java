package com.threeNerds.basketballDiary.mvc.game.repository;

import com.threeNerds.basketballDiary.mvc.game.domain.GameJoinPlayer;
import com.threeNerds.basketballDiary.mvc.game.domain.GameJoinTeam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameJoinPlayerRepository {

     /** 게임참가선수 저장 */
    int save(GameJoinPlayer gameJoinPlayer);

    /** 게임참가선수 목록조회 */
    List<GameJoinPlayer> findPlayers(Long gameJoinTeamSeq);

    /** 게임참가선수 삭제(다건) - 게임참가팀Seq로 다건 삭제  */
    int deletePlayers(Long gameJoinTeamSeq);
}
