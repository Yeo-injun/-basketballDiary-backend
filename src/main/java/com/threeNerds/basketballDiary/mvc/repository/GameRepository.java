package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.domain.Game;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GameRepository {

    void deleteGame(Long gameSeq);

    /** 게임생성
     * - Game객체에 채번된 gameSeq가 할당됨.
     * @return Long insert된 데이터의 갯수 할당.
     */
    Long saveGame(Game newGame);
}
