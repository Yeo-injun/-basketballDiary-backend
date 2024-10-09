package com.threeNerds.basketballDiary.mvc.game.domain.repository;

import com.threeNerds.basketballDiary.mvc.game.domain.Game;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GameRepository {
    /**********
     * SELECT
     **********/
    Game findGame(Long gameSeq);

    /**********
     * INSERT
     **********/
    /**--------------------------------
     * 게임생성
     * - Game객체에 채번된 gameSeq가 할당됨.
     **--------------------------------*/
    int saveGame(Game newGame);

    /**********
     * UPDATE
     **********/
    /**--------------------------------
     * 게임기록상태 Update
     * @parma Game (gameSeq, gameRecordStateCode)
     **--------------------------------*/
    int updateGameRecordState(Game game);
    /**--------------------------------
     * 게임의 쿼터시간 Update
     * @parma Game (gameSeq, quarterTime1,2,3,4 중 하나)
     **--------------------------------*/
    int updateQuarterTime(Game game);

    /**********
     * DELETE
     **********/
    int deleteGame(Long gameSeq);
}
