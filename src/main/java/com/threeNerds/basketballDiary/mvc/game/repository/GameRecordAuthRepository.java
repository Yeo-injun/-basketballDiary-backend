package com.threeNerds.basketballDiary.mvc.game.repository;

import com.threeNerds.basketballDiary.mvc.game.domain.GameRecordAuth;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface GameRecordAuthRepository {

    /**********
     * SELECT
     **********/

    /**********
     * INSERT
     **********/
    /**
     * 게임기록권한생성
     */
    int saveGameRecordAuth(GameRecordAuth gameRecordAuth);


    /**********
     * UPDATE
     **********/


    /**********
     * DELETE
     **********/

}

